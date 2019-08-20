package ch.ethz.vis.dnsapi.netcenter.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "insert")
public class XmlCreateCNameRecordRequestWrapper {
    @XmlElement(name = "alias")
    private CreateCNameRecordRequest request;

    public XmlCreateCNameRecordRequestWrapper() { }

    public XmlCreateCNameRecordRequestWrapper(CreateCNameRecordRequest request) {
        this.request = request;
    }

    public CreateCNameRecordRequest getRequest() {
        return request;
    }

    public void setRequest(CreateCNameRecordRequest request) {
        this.request = request;
    }
}
