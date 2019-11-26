package com.recvani.requests;

import org.json.simple.JSONArray;

public class ExpRequest implements  BaseRequest {
    protected String eid;
    protected long exptime;

    public ExpRequest(String eid, long exptime){
        this.eid = eid;
        this.exptime = exptime;
    }
    @Override
    public String getMethod() {
        return "set_exp";
    }

    @Override
    public JSONArray getParams() {
        JSONArray jarray = new JSONArray();
        jarray.add(eid);
        jarray.add(exptime);
        return jarray;
    }
}
