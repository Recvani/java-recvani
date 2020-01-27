package com.recvani.manager;

import com.recvani.requests.BaseRequest;
import org.json.simple.JSONArray;
import com.recvani.client.RvClient;
import com.recvani.requests.RecRequest;

import java.util.List;
import java.util.ArrayList;


public class RecVaniManager {
    private static final String API_KEY = "BCOfp5pXW/sAdpfv5MgPSEEB";
    private static final String MODEL = "rps_mr";
    private static final String MODEL_KEY = "mOZ0rldtzlMKjt7kxmfSsORZqoc0z0X+hUaA/EaQ3Es=";

    private RvClient rvClient;
    
    public RecVaniManager(String api_key, String model, String model_key ) {
        rvClient = new RvClient(api_key, model, model_key);
    }

    public List<String> getStories(String uid, Integer count) {
        return getStories(uid, count, new ArrayList<>(), RecRequest.FULL_HISTORY_FILTER);
    }

    public List<String> getStories(String uid, Integer count, List<List<String>> tags) {
        return getStories(uid, count, tags, RecRequest.FULL_HISTORY_FILTER);
    }

    public List<String> getStories(String uid, Integer count, List<List<String>> tags, long historyFilterTime ) {

        List<String>result = new ArrayList<>();
        RecRequest recRequest = new RecRequest(uid, count, tags, historyFilterTime);
        try {
            Object obj  = rvClient.send(recRequest);
            if (obj instanceof JSONArray){
                for(Object eid: ((JSONArray)obj)){
                    result.add((String)eid);
                }
            }
        } catch (Exception ex){
            System.err.println("Got Exception in RecVani Client " +uid + " " + ex.getMessage());
        }
        return  result;
    }

    public boolean sendRequest(BaseRequest request){
        try {
            rvClient.send(request);
        }
        catch (Exception ex) {
            System.err.println("Got Exception in RecVani Client " +  ex.getMessage());
            return  false;
        }
        return  true;
    }
}
