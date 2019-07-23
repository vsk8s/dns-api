package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "usedIps")
public class GetARecordResponse {
    @XmlElement(name = "usedIp")
    private List<ARecord> records = new ArrayList<>();

    public List<ARecord> getRecords() {
        return records;
    }

    private void setRecords(List<ARecord> records) {
        this.records = records;
    }
}
