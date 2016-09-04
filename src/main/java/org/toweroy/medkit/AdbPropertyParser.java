package org.toweroy.medkit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AdbPropertyParser {

    private static final Pattern IFACE_PATTERN = Pattern.compile(".*iface=(.*?)\\s.*");
    private static final Pattern TYPE_PATTERN = Pattern.compile(".*type=(.*?)\\s.*");
    private static final Pattern NETWORK_ID_PATTERN = Pattern.compile(".*networkId=(.*?)\\s.*");

    public static List<Netstats> getNetstats(String netstats) {
        String[] netstatLines = netstats.split("\n");
        List<String> interfaceLines = new ArrayList<>();
        boolean activeSection = false;

        for (int i = 0; i < netstatLines.length; i++) {
            if (netstatLines[i].contains("Active interfaces")) {
                activeSection = true;
                continue;
            }
            if (netstatLines[i].trim().startsWith("iface=") && activeSection) {
                interfaceLines.add(netstatLines[i].trim());
            } else if (activeSection) {
                break;
            }
        }

        return interfaceLines.stream().map(iface -> parse(iface)).collect(Collectors.toList());
    }

    /**
     * iface=wlan0 ident=[{type=WIFI, subType=COMBINED, networkId="trojan64", metered=false}]
     */
    private static Netstats parse(String input) {
        String iface = "";
        String type = "";
        String networkId = "";

        Matcher ifaceMatcher = IFACE_PATTERN.matcher(input);
        if (ifaceMatcher.find()) {
            iface = ifaceMatcher.group(1);
        }

        Matcher typeMatcher = TYPE_PATTERN.matcher(input);
        if (typeMatcher.find()) {
            type = typeMatcher.group(1);
        }

        Matcher networkIdMatcher = NETWORK_ID_PATTERN.matcher(input);
        if (networkIdMatcher.find()) {
            networkId = networkIdMatcher.group(1);
        }

        type = type.substring(0, type.lastIndexOf(","));
        networkId = networkId.substring(networkId.indexOf("\"") + 1, networkId.lastIndexOf("\""));
        return new Netstats.Builder(iface, type).networkId(networkId).build();
    }
}
