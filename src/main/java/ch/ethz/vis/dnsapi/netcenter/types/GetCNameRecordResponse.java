package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "usedAliases")
public class GetCNameRecordResponse {
    @XmlElement(name = "alias")
    private List<CNameRecord> records;

    public List<CNameRecord> getRecords() {
        return records;
    }

    private void setRecords(List<CNameRecord> records) {
        this.records = records;
    }
}
