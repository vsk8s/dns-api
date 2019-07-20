package ch.ethz.vis.dnsapi.netcenter.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class TxtResponse {
    @JsonProperty(value = "errors", required = false)
    private List<JsonError> error;

    @JsonUnwrapped
    private TxtRecord txtRecord;

    public List<JsonError> getError() {
        return error;
    }

    private void setError(List<JsonError> error) {
        this.error = error;
    }

    public TxtRecord getTxtRecord() {
        return txtRecord;
    }

    private void setTxtRecord(TxtRecord txtRecord) {
        this.txtRecord = txtRecord;
    }
}
