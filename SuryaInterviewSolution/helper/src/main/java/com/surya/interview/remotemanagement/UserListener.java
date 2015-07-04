package com.surya.interview.remotemanagement;

/**
 * Created by ManishaKedia on 04/07/2015.
 */
public interface UserListener {

    void onPreExecute();

    void onPostExecute(String result);
}
