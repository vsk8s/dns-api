package ch.ethz.vis.dnsapi.netcenter.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonResponse {
    @JsonProperty(value = "errors", required = false)
    private List<JsonError> error;

    @JsonProperty(value = "status", required = false)
    private String status;

    public List<JsonError> getError() {
        return error;
    }

    private void setError(List<JsonError> error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }
}
