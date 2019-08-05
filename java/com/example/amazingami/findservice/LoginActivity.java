package com.example.amazingami.findservice;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
TextView tv_skip,tvlink_signupin;

    EditText input_mobile,input_password;
    Button btn_login;
    CheckBox cbShowPwd;
    String strusername,strpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_skip = (TextView) findViewById(R.id.tv_skip);
        tvlink_signupin = (TextView) findViewById(R.id.tvlink_signupin);
        input_password = (EditText) findViewById(R.id.input_password);
        input_mobile = (EditText) findViewById(R.id.input_mobile);
        btn_login = (Button) findViewById(R.id.btn_login);
        cbShowPwd=(CheckBox)findViewById(R.id.cbShowPwd);

        cbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    input_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    input_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, Menu_Holder.class);
                startActivity(in);
            }
        });
        tvlink_signupin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, Signup_Activity.class);
                startActivity(in);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strusername = input_mobile.getText().toString();
                strpassword = input_password.getText().toString();
                if (strusername.equals("")) {
                    customalert("Please enter Mobile no.", LoginActivity.this);
                    // Helper.customalert("Please enter username", LoginForm.this);
                } else if (strpassword.equals("")) {
                    customalert("Please enter password", LoginActivity.this);
                } else {
                    makeJsonObjReq2();
                }
            }
        });
    }
//---Coding for Calling webservice

    private void makeJsonObjReq2() {
        //---For Display Progressbar
        final ProgressDialog pb=new ProgressDialog(LoginActivity.this);
        pb.setMessage("Please wait..");
        pb.show();
//--Replace your url
        final String urlweb= Url.GetUrl("http://ezeonsoft.in/ezeadd/login_users.php?" + "mobile=" + strusername + "&password=" + strpassword);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlweb, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject result) {
                        Log.d("ressult", result.toString());
//--Dissmiss progress dialog
                        pb.dismiss();
//---Try catch block and your other parsing

                        try
                        {
//

                            JSONObject jobresponse=result.getJSONObject("response");

                            String status=jobresponse.getString("status");
                            String message=jobresponse.getString("message");

                            if(status.equals("1")) {
                                String email=jobresponse.getString("email");
                                String password=jobresponse.getString("password");
                                String mobile=jobresponse.getString("mobile");
                                String name=jobresponse.getString("name");
                                String user_id=jobresponse.getString("user_id");
                                String user_status=jobresponse.getString("user_status");
                                String user_type=jobresponse.getString("user_type");
                                String device_token=jobresponse.getString("device_token");

                                SessionForm.SetSharedPreferences(SessionForm.KEY_email, email, LoginActivity.this);
                                SessionForm.SetSharedPreferences(SessionForm.KEY_name, name, LoginActivity.this);
                                SessionForm.SetSharedPreferences(SessionForm.KEY_mobile, mobile, LoginActivity.this);
                                SessionForm.SetSharedPreferences(SessionForm.Key_password, password, LoginActivity.this);
                                SessionForm.SetSharedPreferences(SessionForm.KEY_user_id, user_id, LoginActivity.this);
                                SessionForm.SetSharedPreferences(SessionForm.KEY_user_status, user_status, LoginActivity.this);
                                SessionForm.SetSharedPreferences(SessionForm.KEY_device_token, device_token, LoginActivity.this);
                                SessionForm.SetSharedPreferences(SessionForm.KEY_user_type, user_type, LoginActivity.this);


                                SessionForm.SetSharedPreferences(SessionForm.KEY_loginstatus, "true", LoginActivity.this);


                                Intent i = new Intent(LoginActivity.this, Menu_Holder.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                customalert(message,LoginActivity.this);
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            customalert("Something went wrong please check",LoginActivity.this);
                        }
//----
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Userchoice", "Error: " + error.getMessage());
            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        // Adding request to request queue
        String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
        MyApplication.getInstance().getRequestQueue().getCache().remove(urlweb);
        MyApplication.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
    //--end for calling webservice
    public  void customalert(String Msg,Activity act)
    {
        final Dialog dialog = new Dialog(act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        TextView text = (TextView) dialog.findViewById(R.id.txmsg);
        text.setText(Msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,FragmentMainHome.class);
                startActivity(i);
                finish();
                // dialog.dismiss();
            }
        });
        dialog.show();
    }
}
