package com.userchecking.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.userchecking.R;
import com.userchecking.adepter.UserAttendance;
import com.userchecking.model.Attendance;
import com.userchecking.utils.Api;
import com.userchecking.utils.Utilities;
import com.userchecking.volley.AppController;
import com.userchecking.volley.VolleyJsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LastWeekData extends AppCompatActivity {
    private static final String TAG = LastWeekData.class.getSimpleName();

    private ListView mWeeklistView;
    private UserAttendance muserAttendance;
    public static ArrayList<Attendance> attendanceArrayList;
    private Context context;
    private ProgressDialog pDialog;
    private HashMap<String, String> params;
    private VolleyJsonParser volleyParser;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_week_data);

        mWeeklistView = (ListView) findViewById(R.id.week_data_list);

        init();
    }

    private void init() {

        context = LastWeekData.this;
        requestQueue = Volley.newRequestQueue(context);
        volleyParser = new VolleyJsonParser(context);
        attendanceArrayList = new ArrayList<Attendance>();
        attendanceArrayList.clear();

        if (Utilities.isNetworkAvailable(context)) {
            makeJsonObjReq();
        } else {
            Utilities.showAlertDialog(LastWeekData.this, getString(R.string.check_internet_connection));
        }
    }


    private void makeJsonObjReq() {

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("user_id", "10");

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Api.WeekData, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "-->" + response.toString());

                        if (response != null) {
                            String Result = null;
                            try {

                                JSONObject jsonObject = new JSONObject(response.toString());
                                Log.d(TAG, "jsonObject --->" + jsonObject);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                Log.d(TAG, "jsonObject1 --->" + jsonObject1);
                                JSONArray jsonArray = jsonObject1.getJSONArray("userAttendance");
                                Log.d(TAG, "jsonArray --->" + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) ;

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.d(TAG, "length ---> " +jsonArray.length( ));
                                    JSONObject userAttendance = jsonArray.getJSONObject(i);
                                    /*String user_id = userAttendance.getString("user_id");
                                    String user_activity = userAttendance.getString("user_activity");
                                    String created_at = userAttendance.getString("created_at");*/

                                    Attendance attendance = new Attendance();
                                    attendance.setUser_id(userAttendance.getString("user_id"));
                                    attendance.setUser_activity(userAttendance.getString("user_activity"));
                                    attendance.setCreated_date(userAttendance.getString("created_at"));
                                    attendanceArrayList.add(attendance);
                                }
                                muserAttendance = new UserAttendance(LastWeekData.this, attendanceArrayList);
                                mWeeklistView.setAdapter(muserAttendance);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Utilities.showAlertDialog(LastWeekData.this, "Data Not Found");
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "");
    }
}
