package com.userchecking.model;

/**
 * Created by GURUKRUPA on 10/21/2016.
 */

public class Attendance {

    String user_id;
    String user_activity;
    String created_date;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_activity() {
        return user_activity;
    }

    public void setUser_activity(String user_activity) {
        this.user_activity = user_activity;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
