package com.surya.interview.codeexercise;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;

import com.surya.interview.remotemanagement.AndroidTaskExecutor;
import com.surya.interview.remotemanagement.TaskListener;
import com.surya.interview.remotemanagement.UserListener;

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

    List<ResponseData> responseList = new ArrayList<>();
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.list);

        parseResp(intent.getStringExtra("Response Data"));
    }

    private void parseResp(final String response) {
        try {
                new AndroidTaskExecutor().execute(new UserListener() {
                    ProgressDialog progressBar;

                    @Override
                    public void onPreExecute() {
                        // prepare for a progress bar dialog
                        progressBar = new ProgressDialog(ResponseListActivity.this);
                        progressBar.setCancelable(false);
                        progressBar.setMessage("Please wait. Files getting downloaded...");
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.show();
                        try {
                            jsonObject = new JSONObject(response);
                            jsonArray = new JSONArray(jsonObject.getString("items"));
                        } catch (Exception e) {}
                    }

                    @Override
                    public void onPostExecute(Object result) {
                        if(progressBar != null)
                            progressBar.dismiss();
                        if(responseList != null) {
                            setListAdapter(new ResponseAdapter(ResponseListActivity.this, responseList));
                        }
                    }
                }, new TaskListener() {
                    @Override
                    public Object execute() {
                        try {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);

                                ResponseData respData = new ResponseData();

                                respData.setEmailId(jsonObject.getString("emailId"));
                                respData.setFirstName(jsonObject.getString("firstName"));
                                respData.setLastName(jsonObject.getString("lastName"));

                                URL url = new URL(jsonObject.getString("imageUrl"));

                                // Read the inputstream
                                BufferedInputStream buf = new BufferedInputStream(url.openStream());

                                // Convert the BufferedInputStream to a Bitmap
                                Bitmap bMap = BitmapFactory.decodeStream(buf);
                                buf.close();

                                respData.setImage(new BitmapDrawable(null, bMap));
                                responseList.add(respData);
                            }

                            return null;
                        } catch (Exception e) {
                            Log.e("Error: ", e.toString());
                        }
                        return null;
                    }
                });
        } catch (Exception e) {
            Log.e("List Actiivity", e.getMessage());
        }
    }
}



