package com.surya.interview.remotemanagement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by ManishaKedia on 04/07/2015.
 */
public class AndroidHttpReq {

    private final HttpPost httpPost;
    private JSONObject params;

    public AndroidHttpReq(String url){
        httpPost = new HttpPost(url);
    }

    public void put(String key, String value) {
        if(params == null)
            params = new JSONObject();

        try {
            params.put(key, value);
        }
        catch (Exception e) {
            //Ignore Exception
        }
    }

    public String sendHttpRequest() throws Exception{
        HttpParams httpParams = new BasicHttpParams();
        //Set Connection TimeOut as 10 sec
        HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
        //Set Socket TimeOut as 20 sec
        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
        HttpConnectionParams.setSocketBufferSize(httpParams, 1024*8);

        DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);

        StringEntity strEntity;
        try {
            strEntity = new StringEntity(params.toString());
            strEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        }
        catch (UnsupportedEncodingException e){
            throw new Exception("Error: " + e.getMessage());
        }

        httpPost.setEntity(strEntity);

        HttpResponse httpResponse;
        String response = null;

        try{
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            if(httpEntity != null)
                response = EntityUtils.toString(httpEntity);
        }
        catch (Exception e){
            throw new Exception("Error: " + e.getMessage());
        }

        return response;
    }


}
