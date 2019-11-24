package com.recvani.requests;

import org.json.simple.JSONArray;

public interface BaseRequest {
    public String getMethod();
    public JSONArray getParams();
}
