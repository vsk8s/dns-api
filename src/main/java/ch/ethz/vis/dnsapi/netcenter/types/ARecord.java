package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class ARecord {
    @XmlElement(name = "ip")
    private String ip;

    @XmlElement(name = "ipVersion")
    private String ipVersion;

    @XmlElement(name = "fqname")
    private String fqName;

    @XmlElement(name = "ipSubnet")
    private String ipSubnet;

    @XmlElement(name = "forward")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean forward;

    @XmlElement(name = "reverse")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean reverse;

    @XmlElement(name = "ttl")
    private int ttl;

    @XmlElement(name = "dhcp")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean dhcp;

    @XmlElement(name = "dhcpMac")
    private String dhcpMac;

    @XmlElement(name = "ddns")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean ddns;

    @XmlElement(name = "isgGroup")
    private String isgGroup;

    @XmlElement(name = "lastDetection")
    private String lastDetection;

    @XmlElementWrapper(name = "views")
    @XmlElement(name = "view")
    private List<String> views;

    @XmlElement(name = "remark")
    private String remark;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFqName() {
        return fqName;
    }

    public void setFqName(String fqName) {
        this.fqName = fqName;
    }

    public String getIpSubnet() {
        return ipSubnet;
    }

    public void setIpSubnet(String ipSubnet) {
        this.ipSubnet = ipSubnet;
    }

    public Boolean getForward() {
        return forward;
    }

    public void setForward(Boolean forward) {
        this.forward = forward;
    }

    public Boolean getReverse() {
        return reverse;
    }

    public void setReverse(Boolean reverse) {
        this.reverse = reverse;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public Boolean getDhcp() {
        return dhcp;
    }

    public void setDhcp(Boolean dhcp) {
        this.dhcp = dhcp;
    }

    public String getDhcpMac() {
        return dhcpMac;
    }

    public void setDhcpMac(String dhcpMac) {
        this.dhcpMac = dhcpMac;
    }

    public Boolean getDdns() {
        return ddns;
    }

    public void setDdns(Boolean ddns) {
        this.ddns = ddns;
    }

    public String getIsgGroup() {
        return isgGroup;
    }

    public void setIsgGroup(String isgGroup) {
        this.isgGroup = isgGroup;
    }

    public String getLastDetection() {
        return lastDetection;
    }

    public void setLastDetection(String lastDetection) {
        this.lastDetection = lastDetection;
    }

    public List<String> getViews() {
        return views;
    }

    public void setViews(List<String> views) {
        this.views = views;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(String ipVersion) {
        this.ipVersion = ipVersion;
    }

    public static final class Builder {
        private String ip;
        private String ipVersion;
        private String fqName;
        private String ipSubnet;
        private Boolean forward;
        private Boolean reverse;
        private int ttl;
        private Boolean dhcp;
        private String dhcpMac;
        private Boolean ddns;
        private String isgGroup;
        private String lastDetection;
        private List<String> views;
        private String remark;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder withIpVersion(String ipVersion) {
            this.ipVersion = ipVersion;
            return this;
        }

        public Builder withFqName(String fqName) {
            this.fqName = fqName;
            return this;
        }

        public Builder withIpSubnet(String ipSubnet) {
            this.ipSubnet = ipSubnet;
            return this;
        }

        public Builder withForward(Boolean forward) {
            this.forward = forward;
            return this;
        }

        public Builder withReverse(Boolean reverse) {
            this.reverse = reverse;
            return this;
        }

        public Builder withTtl(int ttl) {
            this.ttl = ttl;
            return this;
        }

        public Builder withDhcp(Boolean dhcp) {
            this.dhcp = dhcp;
            return this;
        }

        public Builder withDhcpMac(String dhcpMac) {
            this.dhcpMac = dhcpMac;
            return this;
        }

        public Builder withDdns(Boolean ddns) {
            this.ddns = ddns;
            return this;
        }

        public Builder withIsgGroup(String isgGroup) {
            this.isgGroup = isgGroup;
            return this;
        }

        public Builder withLastDetection(String lastDetection) {
            this.lastDetection = lastDetection;
            return this;
        }

        public Builder withViews(List<String> views) {
            this.views = views;
            return this;
        }

        public Builder withRemark(String remark) {
            this.remark = remark;
            return this;
        }

        public ARecord build() {
            ARecord aRecord = new ARecord();
            aRecord.setIp(ip);
            aRecord.setIpVersion(ipVersion);
            aRecord.setFqName(fqName);
            aRecord.setIpSubnet(ipSubnet);
            aRecord.setForward(forward);
            aRecord.setReverse(reverse);
            aRecord.setTtl(ttl);
            aRecord.setDhcp(dhcp);
            aRecord.setDhcpMac(dhcpMac);
            aRecord.setDdns(ddns);
            aRecord.setIsgGroup(isgGroup);
            aRecord.setLastDetection(lastDetection);
            aRecord.setViews(views);
            aRecord.setRemark(remark);
            return aRecord;
        }
    }
}
