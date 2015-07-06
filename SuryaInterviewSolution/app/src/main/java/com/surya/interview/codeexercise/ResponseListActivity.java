package com.surya.interview.codeexercise;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.surya.interview.remotemanagement.AndroidTaskExecutor;
import com.surya.interview.remotemanagement.TaskListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManishaKedia on 04/07/2015.
 */
public class ResponseListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.list);

        String response = intent.getStringExtra("Response Data");

        try {
            List<ResponseData> resp = parseResp(response);
            if (resp != null) {
                ArrayAdapter responseArrayAdapter = new ResponseAdapter(this, resp);
                setListAdapter(responseArrayAdapter);
            }
        }
        catch (Exception e) {
        }
    }

    private List<ResponseData> parseResp(String response) {
        List<ResponseData> responseList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("items"));
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                ResponseData respData = new ResponseData();
                respData.setEmailId(jsonObject.getString("emailId"));
                respData.setFirstName(jsonObject.getString("firstName"));
                respData.setLastName(jsonObject.getString("lastName"));
                respData.setImage(fetchImage(jsonObject.getString("imageUrl")));
                responseList.add(respData);
            }

            return responseList;
        } catch (Exception e) {
            Log.e("list", e.getMessage());
        }
        return null;
    }

    private Drawable fetchImage(final String imageUrl) throws Exception{

        Object data =  new AndroidTaskExecutor().getResult(new TaskListener() {
            @Override
            public Object execute() {
                try {
                    URL url = new URL(imageUrl);

                    // Read the inputstream
                    BufferedInputStream buf = new BufferedInputStream(url.openStream());

                    // Convert the BufferedInputStream to a Bitmap
                    Bitmap bMap = BitmapFactory.decodeStream(buf);
                    buf.close();

                    return new BitmapDrawable(null, bMap);
                } catch (Exception e) {
                    Log.e("Error reading file", e.toString());
                }

                return null;
            }
        });

        return (Drawable)data;
    }
}



