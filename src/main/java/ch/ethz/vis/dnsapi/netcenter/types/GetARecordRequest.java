package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.desktop.AppReopenedEvent;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "usedIps")
public class GetARecordRequest {
    private ARecord aRecord;

    public ARecord getaRecord() {
        return aRecord;
    }

    public void setaRecord(ARecord aRecord) {
        this.aRecord = aRecord;
    }
}
