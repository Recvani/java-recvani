package com.recvani.client;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import com.recvani.requests.BaseRequest;

public class RvClient {

    String apiToken;
    String model;
    String modelKey;
    String uri;
    Integer id;
    CloseableHttpClient httpClient;
    public RvClient(String apiToken, String model, String modelKey){
        this(apiToken, model, modelKey, "https://api.recvani.com/rpc");
    }

    public RvClient(String apiToken, String model, String modelKey, String uri) {
        this.apiToken = apiToken;
        this.model = model;
        this.modelKey = modelKey;
        this.uri = uri;
        httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5 * 1000)
                .setConnectionRequestTimeout(5 * 1000)
                .setSocketTimeout(5 * 1000).build();
        httpClient = HttpClients.custom().create().setDefaultRequestConfig(config).build();

        id = 0;
    }


    public Object send(BaseRequest request) throws IOException {

        id = id +1;

        HttpPost post = new HttpPost(uri);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("apikey", apiToken);

        JSONObject jobject = new JSONObject();
        jobject.put("method", request.getMethod());
        JSONArray jsonArray = request.getParams();
        jsonArray.add(0, model);
        jobject.put("params", jsonArray);
        jobject.put("id", id);
        String jsonString= jobject.toJSONString();
        String sign = getSign(jsonString);
        post.addHeader("Authorization", sign);
        post.setEntity(new StringEntity(jsonString));
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == 200 ) {
            JSONParser jsonParser = new JSONParser();
            HttpEntity entity = response.getEntity();
            String  resultString  = EntityUtils.toString(entity);
            try {
                JSONObject jsonObject = (JSONObject)jsonParser.parse(resultString);

                String error = (String)jsonObject.get("error");
                if (error != null) {
                    throw  new RuntimeException("Got error while processing");
                }
                return jsonObject.get("result");


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            HttpEntity entity = response.getEntity();
            String  resultString  = EntityUtils.toString(entity);
            throw new RuntimeException ("Got Non 200 Respose "+ resultString);
        }
        return null;
    }

    private String getSign(String jobj){

        String hash  = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(modelKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            hash = Base64.encodeBase64String(sha256_HMAC.doFinal(jobj.getBytes()));
        }
        catch (Exception e){
        }
        return "RV " + hash;
    }


}
