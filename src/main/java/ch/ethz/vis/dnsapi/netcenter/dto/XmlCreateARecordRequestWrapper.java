package ch.ethz.vis.dnsapi.netcenter.dto;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "insert")
public class XmlCreateARecordRequestWrapper {
    @XmlElement(name = "nameToIP")
    private CreateARecordRequest request;

    public XmlCreateARecordRequestWrapper() { }

    public XmlCreateARecordRequestWrapper(CreateARecordRequest request) {
        this.setRequest(request);
    }

    public CreateARecordRequest getRequest() {
        return request;
    }

    public void setRequest(CreateARecordRequest request) {
        this.request = request;
    }
}
