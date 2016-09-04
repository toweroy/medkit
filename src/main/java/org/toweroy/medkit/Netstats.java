package org.toweroy.medkit;

public class Netstats {

    private String iface;
    private String type;
    private String networkId;

    Netstats(Builder builder) {
        iface = builder.iface;
        type = builder.type;
        networkId = builder.networkId;
    }

    public String getIface() {
        return iface;
    }

    public String getType() {
        return type;
    }

    public String getNetworkId() {
        return networkId;
    }

    static class Builder {
        private String iface;
        private String type;
        private String networkId;

        public Builder(String iface, String type) {
            this.iface = iface;
            this.type = type;
        }

        public Builder networkId(String networkId) {
            this.networkId = networkId;
            return this;
        }

        public Netstats build() {
            return new Netstats(this);
        }
    }
}
