package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class CNameRecord {
    // The FQDN (new) alias you are trying to create.
    @XmlElement(name = "fqName")
    private String src;

    // The destiantion (old) hostname which already exists.
    @XmlElement(name = "hostName")
    private String dest;

    // This field is unfortunately called "name" in the netcenter.
    // aliasName + subdomain = hostName, however in order to create new records, we have to set aliasName + subdomain
    @XmlElement(name = "name")
    private String aliasName;

    @XmlElement(name = "subdomainName")
    private String subdomain;

    @XmlElement(name = "ttl")
    private int ttl;

    @XmlElement(name = "isgGroup")
    private String isgGroup;

    @XmlElement(name = "lastChange")
    private String lastChange;

    @XmlElement(name = "lastChangeBy")
    private String lastChangeBy;

    @XmlElementWrapper(name = "views")
    @XmlElement(name = "view")
    private List<String> views;

    @XmlElement(name = "remark")
    private String remark;

    public String getSrc() {
        return src;
    }

    private void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
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

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
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
}
