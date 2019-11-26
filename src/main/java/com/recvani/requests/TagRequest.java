package com.recvani.requests;

import org.json.simple.JSONArray;

import java.util.List;

public class TagRequest implements  BaseRequest {
    protected String eid;
    protected List<String> tags;

    public TagRequest(String eid, List<String> tags){
        this.eid = eid;
        this.tags = tags;
    }
    @Override
    public String getMethod() {
        return "set_tag";
    }

    @Override
    public JSONArray getParams() {
        JSONArray jarray = new JSONArray();
        jarray.add(eid);
        JSONArray jsonArray = new JSONArray();
        for (String tag:tags){
            jsonArray.add(tag);
        }
        jarray.add(jsonArray);
        return jarray;
    }
}
