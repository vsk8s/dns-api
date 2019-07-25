package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class CreateARecordRequest {
    @XmlElement(name = "ip")
    private String ip;

    // This field is unfortunately called "name" in the netcenter.
    // ipName + subdomain gives the FQDN of the record to be created.
    @XmlElement(name = "name")
    private String ipName;

    @XmlElement(name = "subdomainName")
    private String subdomain;

    @XmlElement(name = "ttl")
    private int ttl;

    @XmlElement(name = "forward")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean forward;

    @XmlElement(name = "reverse")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean reverse;

    @XmlElement(name = "dhcp")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean dhcp;

    @XmlElement(name = "ddns")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean ddns;

    @XmlElement(name = "macAddress")
    private String macAddress;

    @XmlElement(name = "isgGroup")
    private String isgGroup;

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

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
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

    public Boolean getDhcp() {
        return dhcp;
    }

    public void setDhcp(Boolean dhcp) {
        this.dhcp = dhcp;
    }

    public Boolean getDdns() {
        return ddns;
    }

    public void setDdns(Boolean ddns) {
        this.ddns = ddns;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIsgGroup() {
        return isgGroup;
    }

    public void setIsgGroup(String isgGroup) {
        this.isgGroup = isgGroup;
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

    public static final class Builder {
        private String ip;
        private String ipName;
        private String subdomain;
        private int ttl;
        private Boolean forward;
        private Boolean reverse;
        private Boolean dhcp;
        private Boolean ddns;
        private String macAddress;
        private String isgGroup;
        private List<String> views;
        private String remark;

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder withIpName(String ipName) {
            this.ipName = ipName;
            return this;
        }

        public Builder withSubdomain(String subdomain) {
            this.subdomain = subdomain;
            return this;
        }

        public Builder withTtl(int ttl) {
            this.ttl = ttl;
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

        public Builder withDhcp(Boolean dhcp) {
            this.dhcp = dhcp;
            return this;
        }

        public Builder withDdns(Boolean ddns) {
            this.ddns = ddns;
            return this;
        }

        public Builder withMacAddress(String macAddress) {
            this.macAddress = macAddress;
            return this;
        }

        public Builder withIsgGroup(String isgGroup) {
            this.isgGroup = isgGroup;
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

        public CreateARecordRequest build() {
            if (ip == null || ipName == null || subdomain == null || isgGroup == null) {
                throw new IllegalArgumentException("ip, ipName, subdomain and isGroup required!");
            }

            CreateARecordRequest createARecordRequest = new CreateARecordRequest();
            createARecordRequest.setIp(ip);
            createARecordRequest.setIpName(ipName);
            createARecordRequest.setSubdomain(subdomain);
            createARecordRequest.setTtl(ttl);
            createARecordRequest.setForward(forward);
            createARecordRequest.setReverse(reverse);
            createARecordRequest.setDhcp(dhcp);
            createARecordRequest.setDdns(ddns);
            createARecordRequest.setMacAddress(macAddress);
            createARecordRequest.setIsgGroup(isgGroup);
            createARecordRequest.setViews(views);
            createARecordRequest.setRemark(remark);
            return createARecordRequest;
        }
    }
}
