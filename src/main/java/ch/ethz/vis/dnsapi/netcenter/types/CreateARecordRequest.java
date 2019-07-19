package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "insert")
public class CreateARecordRequest {
    @XmlElement(name = "nameToIP")
    private ARecord aRecord;

    public CreateARecordRequest() {
        this.aRecord = null;
    }

    public CreateARecordRequest(ARecord aRecord) {
        this.aRecord = aRecord;
    }

    public ARecord getaRecord() {
        return aRecord;
    }

    public void setaRecord(ARecord aRecord) {
        this.aRecord = aRecord;
    }
}
