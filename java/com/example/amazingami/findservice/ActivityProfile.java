package com.example.amazingami.findservice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ActivityProfile extends AppCompatActivity {
    EditText etnames,etmobiles,etemails,etpasswords;
    Button btnupdate;
    String strname,strmobile,stremail,strpassword;
    ImageView iv_post_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        etnames=(EditText)findViewById(R.id.etnames);
        etmobiles=(EditText)findViewById(R.id.etmobiles);
        etemails=(EditText)findViewById(R.id.etemails);
        etpasswords=(EditText)findViewById(R.id.etpasswords);
        btnupdate=(Button)findViewById(R.id.btnupdate);
        iv_post_back=(ImageView)findViewById(R.id.iv_post_back);

        makeJsonObjReq2();
        strname= SessionForm.GetSharedPreferences(SessionForm.KEY_name,ActivityProfile.this);
        strmobile=SessionForm.GetSharedPreferences(SessionForm.KEY_mobile,ActivityProfile.this);
        stremail=SessionForm.GetSharedPreferences(SessionForm.KEY_email,ActivityProfile.this);
        strpassword=SessionForm.GetSharedPreferences(SessionForm.Key_password,ActivityProfile.this);


        etnames.setText(strname);
        etmobiles.setText(strmobile);
        etemails.setText(stremail);
        etpasswords.setText(strpassword);

        iv_post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityProfile.this,Menu_Holder.class);
                startActivity(i);
                finish();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strname=etnames.getText().toString();
                strmobile=etmobiles.getText().toString();
                stremail=etemails.getText().toString();
                strpassword=etpasswords.getText().toString();

                if (strname.equals("")) {
                    customalert("Please enter your name");
                } else if (strmobile.equals("")) {
                    customalert("Please enter your mobile number");

                }
                else if(strmobile.length()!=10) {
                    customalert("Please enter valid mobile number");
                }

                else if (stremail.equals("")) {
                    customalert("Please enter your email");
                }
                else if (!isValidEmail(stremail)) {
                    customalert("Please enter valid email id ");
                }else if (strpassword.equals("")) {
                    customalert("Please enter your password");
                } else {
                    makeJsonObjReq3();

                }
            }
        });

    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    private void makeJsonObjReq2() {
        //---For Display Progressbar
        final CustomProgressDialog pb=new CustomProgressDialog(ActivityProfile.this);
        //pb.setMessage("Please wait..");
        pb.show();
//--Replace your url

        final String urlweb= Url.GetUrl("http://http://ezeonsoft.in/ezeadd/view_userdetail.php?" + "user_id=" + SessionForm.GetSharedPreferences(SessionForm.KEY_user_id,ActivityProfile.this));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlweb, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject result) {
                        Log.d("ressult", result.toString());
                        Log.d("url", urlweb);

//--Dissmiss progress dialog
                       /* pb.dismiss();*/
//---Try catch block and your other parsing

                        try
                        {
//
                            JSONObject jobresponse=result.getJSONObject("response");

                            String status = jobresponse.getString("status");
                            String message = jobresponse.getString("message");

                            if(status.equals("1"))
                            {

                                JSONArray jadata=jobresponse.getJSONArray("view_userdetail");


                                for(int i=0;i<jadata.length();i++)
                                {
                                    JSONObject jobdata=jadata.getJSONObject(i);

                                    String name=jobdata.getString("name");

                                    String mobile=jobdata.getString("mobile");
                                    String email=jobdata.getString("email");
                                    String password=jobdata.getString("password");
                                    SessionForm.SetSharedPreferences(SessionForm.KEY_email,email,ActivityProfile.this);
                                    SessionForm.SetSharedPreferences(SessionForm.KEY_name,name,ActivityProfile.this);
                                    SessionForm.SetSharedPreferences(SessionForm.KEY_mobile,mobile,ActivityProfile.this);
                                    SessionForm.SetSharedPreferences(SessionForm.Key_password,password,ActivityProfile.this);

                                }

                            }
                            else
                            {
                                customalert(message);
                            }






                        }
                        catch (Exception ex)
                        {
                            /*ex.printStackTrace();*/


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


    private void makeJsonObjReq3() {
        //---For Display Progressbar
        final ProgressDialog pb=new ProgressDialog(ActivityProfile.this);
        pb.setMessage("Please wait..");
        pb.show();
//--Replace your url

        final String urlweb= Url.GetUrl("http://ezeonsoft.in/ezeadd/upddate_userdetail.php?" + "user_id=" + SessionForm.GetSharedPreferences(SessionForm.KEY_user_id,ActivityProfile.this) + "&name=" + strname + "&mobile=" + strmobile + "&email=" + stremail + "&password=" + strpassword);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlweb, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject result) {
                        Log.d("ressult", result.toString());
                        Log.d("url", urlweb);

//--Dissmiss progress dialog
                        pb.dismiss();
//---Try catch block and your other parsing

                        try
                        {
//
                            JSONObject jobresponse=result.getJSONObject("response");

                            String status = jobresponse.getString("status");
                            String message = jobresponse.getString("message");

                            if(status.equals("1"))
                            {


                                String name=strname;
                                String mobile=strmobile;
                                String email=stremail;
                                String password=strpassword;
                                SessionForm.SetSharedPreferences(SessionForm.KEY_email,email,ActivityProfile.this);
                                SessionForm.SetSharedPreferences(SessionForm.KEY_name,name,ActivityProfile.this);
                                SessionForm.SetSharedPreferences(SessionForm.KEY_mobile,mobile,ActivityProfile.this);
                                SessionForm.SetSharedPreferences(SessionForm.Key_password,password,ActivityProfile.this);



                            }
                            else
                            {
                                customalert(message);
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
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
    public void customalert(String msg)
    {
        final Dialog dialog=new Dialog(ActivityProfile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customlert);
        TextView txtmsg=(TextView)dialog.findViewById(R.id.txtmessage);
        Button btnok=(Button)dialog.findViewById(R.id.btnok);
        txtmsg.setText(msg);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }
}
