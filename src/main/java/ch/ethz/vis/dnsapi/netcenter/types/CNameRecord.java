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

    public static final class Builder {
        private String fqName;
        private String hostname;
        private int ttl;
        private String isgGroup;
        private String lastChange;
        private String lastChangeBy;
        private List<String> views;
        private String remark;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withFqName(String fqName) {
            this.fqName = fqName;
            return this;
        }

        public Builder withHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder withTtl(int ttl) {
            this.ttl = ttl;
            return this;
        }

        public Builder withIsgGroup(String isgGroup) {
            this.isgGroup = isgGroup;
            return this;
        }

        public Builder withLastChange(String lastChange) {
            this.lastChange = lastChange;
            return this;
        }

        public Builder withLastChangeBy(String lastChangeBy) {
            this.lastChangeBy = lastChangeBy;
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

        public CNameRecord build() {
            CNameRecord cNameRecord = new CNameRecord();
            cNameRecord.setHostname(hostname);
            cNameRecord.setTtl(ttl);
            cNameRecord.setIsgGroup(isgGroup);
            cNameRecord.setViews(views);
            cNameRecord.setRemark(remark);
            cNameRecord.fqName = this.fqName;
            cNameRecord.lastChangeBy = this.lastChangeBy;
            cNameRecord.lastChange = this.lastChange;
            return cNameRecord;
        }
    }
}
