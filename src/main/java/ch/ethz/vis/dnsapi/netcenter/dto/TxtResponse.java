package ch.ethz.vis.dnsapi.netcenter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class TxtResponse {
    @JsonProperty(value = "errors", required = false)
    private List<JsonError> errors;

    @JsonUnwrapped
    private TxtRecord txtRecord;

    public List<JsonError> getErrors() {
        return errors;
    }

    private void setErrors(List<JsonError> errors) {
        this.errors = errors;
    }

    public TxtRecord getTxtRecord() {
        return txtRecord;
    }

    private void setTxtRecord(TxtRecord txtRecord) {
        this.txtRecord = txtRecord;
    }
}
