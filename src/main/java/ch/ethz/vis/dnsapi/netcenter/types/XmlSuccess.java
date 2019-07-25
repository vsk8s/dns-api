package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "success")
public class XmlSuccess {
    @XmlValue
    private String message;

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }
}
