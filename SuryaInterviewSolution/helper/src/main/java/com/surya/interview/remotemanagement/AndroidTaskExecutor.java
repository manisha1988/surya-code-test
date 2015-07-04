package com.surya.interview.remotemanagement;

import android.os.AsyncTask;

/**
 * Created by ManishaKedia on 04/07/2015.
 */
public class AndroidTaskExecutor extends AsyncTask<Void, String, String>{

    private UserListener userListener;
    private TaskListener taskListener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        userListener.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        return taskListener.execute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        userListener.onPostExecute(result);
    }

    public void execute(UserListener userListener, TaskListener taskListener) {
        this.userListener = userListener;
        this.taskListener = taskListener;
        this.execute();
    }

}
