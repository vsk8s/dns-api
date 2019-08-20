package ch.ethz.vis.dnsapi.netcenter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchTxtRecordRequest {
    @JsonProperty("id")
    private String id;

    @JsonProperty("txtValue")
    private String value;

    @JsonProperty("fqName")
    private String fqName;

    @JsonProperty("netsupName")
    private String isgGroup;

    @JsonProperty("ttl")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int ttl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFqName() {
        return fqName;
    }

    public void setFqName(String fqName) {
        this.fqName = fqName;
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


    public static final class Builder {
        private String id;
        private String value;
        private String fqName;
        private String isgGroup;
        private int ttl;

        private Builder() {
        }

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

        public Builder withFqName(String fqName) {
            this.fqName = fqName;
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

        public SearchTxtRecordRequest build() {
            SearchTxtRecordRequest txtRecord = new SearchTxtRecordRequest();
            txtRecord.setId(id);
            txtRecord.setValue(value);
            txtRecord.setFqName(fqName);
            txtRecord.setIsgGroup(isgGroup);
            txtRecord.setTtl(ttl);
            return txtRecord;
        }
    }
}
