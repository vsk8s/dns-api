package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "error")
public class XmlError {
    @XmlElement(name = "msg", required = true)
    private String message;

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }
}
