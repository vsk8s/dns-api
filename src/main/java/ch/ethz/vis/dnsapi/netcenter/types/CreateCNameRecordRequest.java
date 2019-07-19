package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "insert")
public class CreateCNameRecordRequest {
    @XmlElement(name = "alias")
    private CNameRecord cNameRecord;

    public CreateCNameRecordRequest() {
        this.cNameRecord = null;
    }

    public CreateCNameRecordRequest(CNameRecord cNameRecord) {
        this.cNameRecord = cNameRecord;
    }

    public CNameRecord getcNameRecord() {
        return cNameRecord;
    }

    public void setcNameRecord(CNameRecord cNameRecord) {
        this.cNameRecord = cNameRecord;
    }
}
