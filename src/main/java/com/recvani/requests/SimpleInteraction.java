package com.recvani.requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SimpleInteraction implements  BaseRequest {
    protected String uid;
    protected String eid;
    protected double score;
    protected long time;

    public SimpleInteraction(String uid, String eid, double score, long time){
        this.uid = uid;
        this.eid = eid;
        this.score = score;
        this.time=time;
    }
    @Override
    public String getMethod() {
        return "save";
    }

    @Override
    public JSONArray getParams() {
        JSONArray jarray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", uid);
        jsonObject.put("eid", eid);
        jsonObject.put("score", score);
        jsonObject.put("time", time);
        jarray.add(jarray);
        return jarray;
    }
}
