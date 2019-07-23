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

    // This field is unfortunately called "name" in the netcenter.
    // ipname + subdomain = fqname, however ipname and subdomain have to be given in order for edits to occur.
    @XmlElement(name = "name")
    private String ipname;

    @XmlElement(name = "subdomainName")
    private String subdomain;

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

    @XmlElement(name = "lastChange")
    private String lastChange;

    @XmlElementWrapper(name = "views")
    @XmlElement(name = "view")
    private List<String> views;

    @XmlElement(name = "remark")
    private String remark;

    @XmlElement(name = "lastChangeBy")
    private String lastChangeBy;

    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlElement(name = "alias")
    private Boolean alias;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFqName() {
        return fqName;
    }

    private void setFqName(String fqName) {
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

    public List<String> getViews() {
        return views;
    }

    public void setViews(List<String> views) {
        this.views = views;
    }

    public String getIpname() {
        return ipname;
    }

    public void setIpname(String ipname) {
        this.ipname = ipname;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastChangeBy() {
        return lastChangeBy;
    }

    private void setLastChangeBy(String lastChangeBy) {
        this.lastChangeBy = lastChangeBy;
    }

    public String getLastChange() {
        return lastChange;
    }

    private void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    public Boolean getAlias() {
        return alias;
    }

    private void setAlias(Boolean alias) {
        this.alias = alias;
    }

    public String getIpVersion() {
        return ipVersion;
    }

    private void setIpVersion(String ipVersion) {
        this.ipVersion = ipVersion;
    }
}
