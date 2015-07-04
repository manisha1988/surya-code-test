package com.surya.interview.codeexercise;

import android.graphics.drawable.Drawable;

/**
 * Created by ManishaKedia on 04/07/2015.
 */
class ResponseData {

    private String emailId;

    private Drawable image;

    private String firstName;

    private String lastName;

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public Drawable getImage() {
        return image;
    }

    public String getFirstName() {
        return firstName;
    }





}
