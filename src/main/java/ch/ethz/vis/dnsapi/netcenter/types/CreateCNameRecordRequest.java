package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class CreateCNameRecordRequest {
    // The destination (old) hostname which already exists.
    @XmlElement(name = "hostName")
    private String hostname;

    // This field is unfortunately called "name" in the netcenter.
    // aliasName + subdomain is the FQDN for the newly created records.
    @XmlElement(name = "name")
    private String aliasName;

    @XmlElement(name = "subdomainName")
    private String subdomain;

    @XmlElement(name = "ttl")
    private int ttl;

    @XmlElement(name = "isgGroup")
    private String isgGroup;

    @XmlElementWrapper(name = "views")
    @XmlElement(name = "view")
    private List<String> views;

    @XmlElement(name = "remark")
    private String remark;

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

    public static final class Builder {
        // The destination (old) hostname which already exists.
        private String hostname;
        // This field is unfortunately called "name" in the netcenter.
        // aliasName + subdomain is the FQDN for the newly created records.
        private String aliasName;
        private String subdomain;
        private int ttl;
        private String isgGroup;
        private List<String> views;
        private String remark;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder withAliasName(String aliasName) {
            this.aliasName = aliasName;
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

        public CreateCNameRecordRequest build() {
            if (hostname == null || aliasName == null || subdomain == null || isgGroup == null) {
                throw new IllegalArgumentException("hostname, aliasName, subdomain and isgGroup required!");
            }

            CreateCNameRecordRequest createCNameRecordRequest = new CreateCNameRecordRequest();
            createCNameRecordRequest.setHostname(hostname);
            createCNameRecordRequest.setAliasName(aliasName);
            createCNameRecordRequest.setSubdomain(subdomain);
            createCNameRecordRequest.setTtl(ttl);
            createCNameRecordRequest.setIsgGroup(isgGroup);
            createCNameRecordRequest.setViews(views);
            createCNameRecordRequest.setRemark(remark);
            return createCNameRecordRequest;
        }
    }
}
