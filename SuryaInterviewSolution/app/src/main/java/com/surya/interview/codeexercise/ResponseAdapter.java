package com.surya.interview.codeexercise;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by ManishaKedia on 04/07/2015.
 */
public class ResponseAdapter extends ArrayAdapter<ResponseData>{

    private LayoutInflater inflater;
    private List<ResponseData> responseDataList;

    public ResponseAdapter(Activity activity, List<ResponseData> responseDataList){
        super(activity, R.layout.row, responseDataList);
        this.responseDataList = responseDataList;
        inflater = activity.getWindow().getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.row, parent, false);

        TextView txtEmailId = (TextView) view.findViewById(R.id.email);
        txtEmailId.setText(responseDataList.get(position).getEmailId());

        ImageView imgProfileImage = (ImageView) view.findViewById(R.id.profileImage);
        imgProfileImage.setImageDrawable(responseDataList.get(position).getImage());

        TextView txtFirstName = (TextView) view.findViewById(R.id.name);
        txtFirstName.setText(responseDataList.get(position).getFirstName() + " " + responseDataList.get(position).getLastName());

        return view;
    }
}
