package com.recvani.requests;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class BatchInteraction implements BaseRequest {

    protected List<SimpleInteraction> interactions;

    public  BatchInteraction(){
        this(new ArrayList<>());
    }

    public BatchInteraction(List<SimpleInteraction> interactions){
        this.interactions = interactions;
    }

    public void add(SimpleInteraction simpleInteraction){
        interactions.add(simpleInteraction);
    }

    public void clear(){
        interactions.clear();
    }
    @Override
    public String getMethod() {
        return "save_batch";
    }

    @Override
    public JSONArray getParams() {
        JSONArray jsonArray = new JSONArray();
        JSONArray jarray = new JSONArray();
        for (SimpleInteraction simpleInteraction: interactions){
            jarray.add(simpleInteraction.getParams().get(0));
        }
        jsonArray.add(jarray);
        return jsonArray;
    }
}
