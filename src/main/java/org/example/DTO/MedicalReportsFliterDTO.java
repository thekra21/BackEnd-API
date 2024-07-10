package org.example.DTO;

import jakarta.ws.rs.QueryParam;

public class MedicalReportsFliterDTO {

    private @QueryParam("limit") Integer limit;
    private @QueryParam("offset") int offset;


    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
