package com.userchecking.adepter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.userchecking.R;
import com.userchecking.model.Attendance;

import java.util.List;

/**
 * Created by GURUKRUPA on 10/21/2016.
 */

public class UserAttendance extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Attendance> attendances;

    public UserAttendance(Activity activity, List<Attendance> attendances) {
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.attendances = attendances;
    }

    @Override
    public int getCount() {
        return attendances.size();
    }

    @Override
    public Attendance getItem(int position) {
        return attendances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder myViewHolder;

        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            convertView = inflater.inflate(R.layout.item_attendance, null);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.userID = (TextView) convertView.findViewById(R.id.txt_id);
        myViewHolder.userActivity = (TextView) convertView.findViewById(R.id.txt_checkin);
        myViewHolder.usetCDate = (TextView) convertView.findViewById(R.id.txt_cdate);


        Attendance at = attendances.get(position);

        myViewHolder.userID.setText( "Id : " + at.getUser_id());
        myViewHolder.userActivity.setText("Activity : " + at.getUser_activity());
        myViewHolder.usetCDate.setText("Date : " +at.getCreated_date());

        return convertView;
    }

    private class MyViewHolder {

        TextView userID;
        TextView userActivity;
        TextView usetCDate;
    }

}
