package com.recvani.requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class ItemParam {
    String eid;
    Long exptime;
    List<String> tags;

    public ItemParam(String eid, List<String> tags){
        this(eid, tags, null);
    }

    public ItemParam(String eid, long exptime){
        this(eid, null, exptime);
    }

    public ItemParam(String eid, List<String> tags, Long exptime){
        this.eid = eid;
        this.exptime = exptime;
        this.tags = tags;
    }
    JSONObject getParams(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("eid", eid);
        if(this.exptime != null){
            jsonObject.put("exp_time", exptime);
        }
        if(this.tags != null){
            JSONArray jsonArray = new JSONArray();
            for (String tag: tags){
                jsonArray.add(tags);
            }
            jsonObject.put("tags", jsonArray);
        }
        return jsonObject;
    }

}
