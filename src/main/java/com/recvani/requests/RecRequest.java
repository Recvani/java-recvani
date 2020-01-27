package com.recvani.requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class RecRequest implements BaseRequest {
    
    public static long NO_HISTORY_FILTER = -1;
    public static long FULL_HISTORY_FILTER = -100;
    String uid;
    Integer count;
    List<List<String>> tags;
    long historyFilterTime;

    public RecRequest(String uid, int count, List<List<String>> tags) {
        this(uid, count, tags, FULL_HISTORY_FILTER);
    }

    public RecRequest(String uid, int count, List<List<String>> tags, long historyFilterTime) {
        this.uid = uid;
        this.count = count;
        this.tags = tags;
        this.historyFilterTime = historyFilterTime;
    }

    public String getMethod() {
        return "get_rec";
    }

    public JSONArray getParams() {
        JSONArray jarray = new JSONArray();
        JSONObject jobject = new JSONObject();
        jobject.put("uid", uid);
        jobject.put("count", count);
        JSONArray jsonArray = new JSONArray();
        for (List<String> tlist : tags) {
            JSONArray jlist = new JSONArray();
            for (String tag : tlist) {
                jlist.add(tag);
            }
        }
        jobject.put("tags", jsonArray);
        jobject.put("history", historyFilterTime);
        jarray.add(jobject);
        return jarray;
    }
}
