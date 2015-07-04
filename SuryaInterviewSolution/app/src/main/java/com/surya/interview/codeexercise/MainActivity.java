package com.surya.interview.codeexercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.surya.interview.remotemanagement.AndroidHttpReq;
import com.surya.interview.remotemanagement.AndroidTaskExecutor;
import com.surya.interview.remotemanagement.TaskListener;
import com.surya.interview.remotemanagement.UserListener;
import com.surya.interview.utility.PropertyManager;


public class MainActivity extends ActionBarActivity {

    private String url;
    private String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = new PropertyManager(getApplicationContext(), "config").getValue("url");

        Button btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailId = ((EditText) findViewById(R.id.fld_emaiId)).getText().toString().trim();
                if(emailId.equalsIgnoreCase("") || !(emailId.matches("[a-zA-Z0-9\\_\\.]+@[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z0-9]{3}"))){
                    new AlertDialog.Builder(MainActivity.this).setTitle("Error").
                            setMessage("Please enter valid email ID").setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                new AndroidTaskExecutor().execute(new UserListener() {
                    @Override
                    public void onPreExecute() {
                        //Do nothing
                    }


                    @Override
                    public void onPostExecute(String result) {
                        Intent intent = new Intent(MainActivity.this, ResponseListActivity.class);
                        intent.putExtra("Response Data", result);
                        startActivity(intent);
                    }
                }, new TaskListener() {
                    @Override
                    public String execute() {
                        AndroidHttpReq httpReq = new AndroidHttpReq(url);
                        httpReq.put("emailId", emailId);
                        try {
                            return httpReq.sendHttpRequest();
                        } catch (Exception e) {
                            Log.e("Application", e.getMessage());
                            return null;
                        }
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
