package ch.ethz.vis.dnsapi.netcenter.types;

import com.fasterxml.jackson.annotation.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TxtRecord {
    @JsonProperty("id")
    private String id;

    // When using GET requests, the API gives you "value", but when
    // creating new records via POST you have to use "txtValue".
    @JsonProperty("txtValue")
    @JsonAlias({"value"})
    private String value;

    @JsonProperty("fqName")
    private String fqName;

    @JsonProperty("netsupName")
    private String isgGroup;

    @JsonProperty("ttl")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int ttl;

    @JsonProperty("view")
    private List<String> views;

    @JsonProperty("remark")
    private String remark;

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

    public List<String> getViews() {
        return views;
    }

    public void setViews(List<String> views) {
        this.views = views;
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
        private String fqName;
        private String isgGroup;
        private int ttl;
        private List<String> views;
        private String remark;

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

        public Builder withViews(List<String> views) {
            this.views = views;
            return this;
        }

        public Builder withRemark(String remark) {
            this.remark = remark;
            return this;
        }

        public TxtRecord build() {
            TxtRecord txtRecord = new TxtRecord();
            txtRecord.setId(id);
            txtRecord.setValue(value);
            txtRecord.setFqName(fqName);
            txtRecord.setIsgGroup(isgGroup);
            txtRecord.setTtl(ttl);
            txtRecord.setViews(views);
            txtRecord.setRemark(remark);
            return txtRecord;
        }
    }
}
