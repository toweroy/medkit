package org.toweroy.medkit;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AdbPropertyParserTest {
    private static final String NETSTATS = "Active interfaces:\n" +
            "  iface=wlan0 ident=[{type=WIFI, subType=COMBINED, networkId=\"SuperSlowInternet\", metered=false}]\n" +
            "Active UID interfaces:\n" +
            "  iface=wlan0 ident=[{type=WIFI, subType=COMBINED, networkId=\"SuperSlowInternet\", metered=false}]\n" +
            "Dev stats:\n" +
            "  Pending bytes: 568408";

    @Test
    public void parseNetstats() {
        List<Netstats> netstats = AdbPropertyParser.getNetstats(NETSTATS);
        assertEquals("wlan0", netstats.get(0).getIface());
        assertEquals("WIFI", netstats.get(0).getType());
        assertEquals("SuperSlowInternet", netstats.get(0).getNetworkId());
    }
}
