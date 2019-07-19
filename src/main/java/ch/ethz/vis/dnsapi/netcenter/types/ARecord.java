package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
public class ARecord {
    @XmlElement(name = "ip")
    private String ip;

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

    @XmlElement(name = "macAddress")
    private String macAddress;

    @XmlElement(name = "ddns")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean ddns;

    @XmlElement(name = "isgGroup")
    private String isgGroup;

    @XmlElement(name = "lastDetection")
    private String lastDetection;

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

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
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
}
