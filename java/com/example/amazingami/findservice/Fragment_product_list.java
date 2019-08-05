package com.example.amazingami.findservice;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

/**
 * Created by Amazing Ami on 23-06-2017.
 */

public class Fragment_product_list extends Fragment {
    ListView lvproduct;
    TextView tvtitle,tvcost,tvdiscription;
    ImageView btnplus,btnminus;
    LinearLayout llcarinfo;
    ArrayList<HashMap<String,String>>arrcar;
    int minteger = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_product_list, container, false);
        tvtitle=(TextView)v.findViewById(R.id.tvtitle);
        tvcost=(TextView)v.findViewById(R.id.tvcost);
        lvproduct=(ListView)v.findViewById(R.id.lvproduct);
        tvdiscription=(TextView)v.findViewById(R.id.tvdiscription);

        makeJsonObjReq2();

        return v;
    }

    private void makeJsonObjReq2() {
        //---For Display Progressbar
        final ProgressDialog pb = new ProgressDialog(getActivity());
        pb.setMessage("Please wait..");
        pb.show();
//--Replace your url
        final String urlweb = Url.GetUrl("http://ezeonsoft.in/ezeadd/addview_grocery.php?" + "add_category_id=" + SessionForm.GetSharedPreferences(SessionForm.Key_category_id,getActivity())+"&add_city_id="+SessionForm.GetSharedPreferences(SessionForm.Key_city_id,getActivity())+"&area_id="+SessionForm.GetSharedPreferences(SessionForm.Key_area_id,getActivity()));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,   //add_grocery_category
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
                                arrcar=new ArrayList<HashMap<String, String>>();
                                JSONArray fetchecatogery=jobresponse.getJSONArray("view_userdetail");
                                for(int i=0;i<fetchecatogery.length();i++)
                                {
                                    JSONObject jobdata= fetchecatogery  .getJSONObject(i);
                                    String add_category_id=jobdata.getString("add_category_id");
                                    String add_id=jobdata.getString("add_id");
                                    String add_title= jobdata.getString("add_title");
                                    String add_price= jobdata.getString("add_price");
                                    String add_description= jobdata.getString("add_description");
                                    String add_image= jobdata.getString("add_image");
                                    String owner_name= jobdata.getString("owner_name");
                                    String owner_email= jobdata.getString("owner_email");
                                    String owner_mobile= jobdata.getString("owner_mobile");


                                    HashMap<String,String>map=new HashMap<String,String>();


                                    map.put(SessionForm.Key_category_id, add_category_id);
                                    map.put(SessionForm.KEY_add_id, add_id);
                                    map.put(SessionForm.KEY_add_title, add_title);
                                    map.put(SessionForm.KEY_add_price, add_price);
                                    map.put(SessionForm.KEY_add_description, add_description);
                                    map.put(SessionForm.Key_add_image, add_image);
                                    map.put(SessionForm.KEY_owner_mobile, owner_mobile);
                                    map.put(SessionForm.KEY_owner_name, owner_name);
                                    map.put(SessionForm.KEY_owner_email, owner_email);

                                    arrcar.add(map);

                                }
                                Adapter_product_list car=new Adapter_product_list(getActivity(), arrcar);
                                lvproduct.setAdapter(car);



                                //this code is the adapter set is the gridview

                                ///this code is the gridview click on run

                                lvproduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        //this code  is the value session in the save of the set value
                                        SessionForm.selectedmap=arrcar.get(position);
                                        Fragment Frag=new Fragment_ProductDetail();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,Frag).commit();

                                    }
                                });




                            }
                            else
                            {
                                customalert(message,getActivity());
                            }


//
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            customalert("Something went wrong please check",getActivity());



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


    public void customalert(String Msg, Activity act) {

        final Dialog dialog = new Dialog(act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.customlert);
        TextView txtmsg = (TextView) dialog.findViewById(R.id.txtmsg);
        txtmsg.setText(Msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btnok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


            }
        });


        dialog.show();


    }


}
