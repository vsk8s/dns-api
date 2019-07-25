package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class CNameRecord {
    @XmlElement(name = "fqName")
    private String fqName;

    @XmlElement(name = "hostName")
    private String hostname;

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

    public String getFqName() {
        return fqName;
    }

    private void setFqName(String fqName) {
        this.fqName = fqName;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
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
