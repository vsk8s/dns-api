package ch.ethz.vis.dnsapi.netcenter.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateTxtRecordRequest {
    // When using GET requests, the API gives you "value", but when
    // creating new records via POST you have to use "txtValue".
    @JsonProperty("txtValue")
    @JsonAlias({"value"})
    private String value;

    @JsonProperty("name")
    private String txtName;

    @JsonProperty("subdomainName")
    private String subdomain;

    @JsonProperty("netsupName")
    private String isgGroup;

    @JsonProperty("ttl")
    private int ttl;

    @JsonProperty("view")
    private List<String> views;

    @JsonProperty("remark")
    private String remark;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public List<String> getViews() {
        return views;
    }

    public void setViews(List<String> views) {
        this.views = views;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getIsgGroup() {
        return isgGroup;
    }

    public void setIsgGroup(String isgGroup) {
        this.isgGroup = isgGroup;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final class Builder {
        private String id;
        // When using GET requests, the API gives you "value", but when
        // creating new records via POST you have to use "txtValue".
        private String value;
        private String txtName;
        private String subdomain;
        private String isgGroup;
        private int ttl;
        private List<String> views;
        private String remark;

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withTxtName(String txtName) {
            this.txtName = txtName;
            return this;
        }

        public Builder withSubdomain(String subdomain) {
            this.subdomain = subdomain;
            return this;
        }

        public Builder withIsgGroup(String isgGroup) {
            this.isgGroup = isgGroup;
            return this;
        }

        public Builder withTtl(int ttl) {
            this.ttl = ttl;
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

        public CreateTxtRecordRequest build() {
            CreateTxtRecordRequest txtRecord = new CreateTxtRecordRequest();
            txtRecord.setValue(value);
            txtRecord.setTxtName(txtName);
            txtRecord.setSubdomain(subdomain);
            txtRecord.setIsgGroup(isgGroup);
            txtRecord.setTtl(ttl);
            txtRecord.setViews(views);
            txtRecord.setRemark(remark);
            return txtRecord;
        }
    }
}
