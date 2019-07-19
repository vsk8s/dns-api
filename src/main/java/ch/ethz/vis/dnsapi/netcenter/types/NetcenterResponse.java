package ch.ethz.vis.dnsapi.netcenter.types;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class NetcenterResponse {
    @XmlElement(name = "success")
    private String successMsg;

    @XmlElementWrapper(name = "errors")
    @XmlElement(name = "error")
    private List<String> errors;

    public String getSuccessMsg() {
        return successMsg;
    }

    private void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public List<String> getErrors() {
        return errors;
    }

    private void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
