package ch.ethz.vis.dnsapi.netcenter.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonResponse {
    @JsonProperty(value = "errors", required = false)
    private List<JsonError> errors;

    @JsonProperty(value = "status", required = false)
    private String status;

    public List<JsonError> getErrors() {
        return errors;
    }

    private void setErrors(List<JsonError> errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }
}
