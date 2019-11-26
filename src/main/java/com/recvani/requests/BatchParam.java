package com.recvani.requests;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class BatchParam implements BaseRequest {

    protected List<ItemParam> itemParams;

    public  BatchParam(){
        this(new ArrayList<>());
    }

    public BatchParam(List<ItemParam> itemParams){
        this.itemParams = itemParams;
    }

    public void add(ItemParam itemParam){
        itemParams.add(itemParam);
    }

    public void clear(){
        itemParams.clear();
    }
    @Override
    public String getMethod() {
        return "set_batch_param";
    }

    @Override
    public JSONArray getParams() {
        JSONArray jsonArray = new JSONArray();
        JSONArray jarray = new JSONArray();
        for (ItemParam itemParam: itemParams){
            jarray.add(itemParam.getParams());
        }
        jsonArray.add(jarray);
        return jsonArray;
    }
}

