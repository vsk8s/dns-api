package ch.ethz.vis.dnsapi.netcenter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonError {
    @JsonProperty("rowId")
    private String rowId;

    @JsonProperty("field")
    private String field;

    @JsonProperty("errMsg")
    private String errorMsg;

    public String getRowId() {
        return rowId;
    }

    private void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getField() {
        return field;
    }

    private void setField(String field) {
        this.field = field;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    private void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
