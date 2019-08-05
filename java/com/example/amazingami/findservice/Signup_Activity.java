package com.example.amazingami.findservice;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Signup_Activity extends AppCompatActivity {
    EditText etemail_signup,etmobile_signup,etpassword_signup,etusername_signup;
    Button btnsignup;
    Spinner spnuser_type;
    TextView tvlink_signup;
    String stremail_signup,strmobile_signup,strpassword_signup,strusername_signup;
    String selectteduser_type_id = "";
    String Selectteduser_type_name = "";
    ArrayList<HashMap<String,String>>arrusertpe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        tvlink_signup=(TextView)findViewById(R.id.tvlink_signup);
        etemail_signup=(EditText)findViewById(R.id.etemail_signup);
        etmobile_signup=(EditText)findViewById(R.id.etmobile_signup);
        etpassword_signup=(EditText)findViewById(R.id.etpassword_signup);
        etusername_signup=(EditText)findViewById(R.id.etusername_signup);
        btnsignup=(Button)findViewById(R.id.btnsignup);
        spnuser_type=(Spinner)findViewById(R.id.spnuser_type);

        arrusertpe = new ArrayList<HashMap<String,String>>();
        makeJsonObjReq3();


        tvlink_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Signup_Activity.this, LoginActivity.class);
                startActivity(in);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strmobile_signup = etmobile_signup.getText().toString();
                stremail_signup = etemail_signup.getText().toString();
                strpassword_signup = etpassword_signup.getText().toString();
                strusername_signup=etusername_signup.getText().toString();

                if (strusername_signup.equals("")) {
                    customalert("Please enter username", Signup_Activity.this);
                    //Helper.customalert("Please enter mobile no", Register.this);
                } else
                if (strmobile_signup.equals("")) {
                    customalert("Please enter mobile no", Signup_Activity.this);
                }
                else if (stremail_signup.equals("")) {
                    customalert("Please enter mail", Signup_Activity.this);
                }
                else if (strpassword_signup.equals("")) {
                    customalert("Please enter password", Signup_Activity.this);
                }
                else if (selectteduser_type_id.equals("")) {
                    customalert("Please select user type", Signup_Activity.this);
                }
                else {
                    makeJasonObjReq2();


                }
            }});
       /* if(SessionForm.GetSharedPreferences(SessionForm.KEY_loginstatus, Register.this).equals("true"))
        {
            Intent i=new Intent(Register.this, FragmentHoldingActivity.class);
            startActivity(i);
            finish();

        }*/
    }

//---Coding for Calling webservice

    private void makeJsonObjReq3() {
        //---For Display Progressbar
        final   ProgressDialog pb = new ProgressDialog(Signup_Activity.this);
        pb.setMessage("Please wait..");
        pb.show();
//--Replace your url
        final String urlweb = Url.GetUrl("http://ezeonsoft.in/ezeadd/user_type_view.php?");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlweb, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
//--Dissmiss progress dialog
                        pb.dismiss();
//---Try catch block and your other parsing

                        try {


                            JSONObject jobresponse=response.getJSONObject("response");
                            String status=jobresponse.getString("status");
                            String message=jobresponse.getString("message");

                            if(status.equals("1"))
                            {
                                arrusertpe=new ArrayList<HashMap<String, String>>();
                                JSONArray user_type=jobresponse.getJSONArray("user_type");
                                for(int i=0;i<user_type.length();i++)
                                {
                                    JSONObject jobdata= user_type.getJSONObject(i);
                                    String user_type_id=jobdata.getString("user_type_id");
                                    String user_type_name=jobdata.getString("user_type_name");




                                    HashMap<String,String>map=new HashMap<String,String>();


                                    map.put(SessionForm.KEY_user_type_id, user_type_id);
                                    map.put(SessionForm.KEY_user_type_name, user_type_name);


                                    arrusertpe.add(map);

                                }


                                Adapter_user_type adspuser=new Adapter_user_type(Signup_Activity.this,arrusertpe);
                                spnuser_type.setAdapter(adspuser);

                                spnuser_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        HashMap<String, String> cmap = arrusertpe.get(position);
                                        selectteduser_type_id = cmap.get(SessionForm.KEY_user_type_id);
                                        Selectteduser_type_name = cmap.get(SessionForm.KEY_user_type_name);
                                        SessionForm.SetSharedPreferences(SessionForm.KEY_user_type_name,Selectteduser_type_name,Signup_Activity.this);
                                        SessionForm.SetSharedPreferences(SessionForm.KEY_user_type_id,selectteduser_type_id,Signup_Activity.this);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            else
                            {
                                customalert(message,Signup_Activity.this);
                            }


//
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            customalert("Something went wrong please check",Signup_Activity.this);


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

    private void makeJasonObjReq2()
    {
        final ProgressDialog pb=new ProgressDialog(Signup_Activity.this);
        pb.setMessage("Please Wait....");
        pb.show();
        pb.setCancelable(false);
        final String urlweb= Url.GetUrl("http://ezeonsoft.in/ezeadd/register_add_users.php?" + "name=" + strusername_signup + "&mobile=" + strmobile_signup + "&email=" + stremail_signup + "&password=" + strpassword_signup+"&user_type="+selectteduser_type_id);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlweb, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.d("result", result.toString());
                pb.dismiss();

                try
                {


                    JSONObject jobresponse=result.getJSONObject("response");
                    String status= jobresponse.getString("status");
                    String message= jobresponse.getString("message");
                    if(status.equals("1"))
                    {
                        String name=   jobresponse.getString("name");
                        String mobile=   jobresponse.getString("mobile");
                        String email=   jobresponse.getString("email");
                        String password=   jobresponse.getString("password");

                        SessionForm.SetSharedPreferences(SessionForm.KEY_email,email,Signup_Activity.this);
                        SessionForm.SetSharedPreferences(SessionForm.KEY_name,email,Signup_Activity.this);
                        SessionForm.SetSharedPreferences(SessionForm.KEY_mobile,email,Signup_Activity.this);
                        SessionForm.SetSharedPreferences(SessionForm.Key_password,email,Signup_Activity.this);
                        SessionForm.SetSharedPreferences(SessionForm.KEY_user_type,email,Signup_Activity.this);

                        customalert(message,Signup_Activity.this);

                    }
                    else
                    {
                        customalert(message,Signup_Activity.this);
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
                Intent i=new Intent(Signup_Activity.this,LoginActivity.class);
                startActivity(i);
                finish();

                //  dialog.dismiss();

            }
        });


        dialog.show();
    }




}


