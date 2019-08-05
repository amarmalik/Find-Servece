package com.example.amazingami.findservice;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentMainHome extends Fragment {

ViewFlipper flipper;
    GridView gridView;
    HashMap<String,Integer>Hash_file_maps;
    ArrayList<HashMap<String,String>>datalist1;
    SliderLayout slider1;

   /* int[] images={
            R.drawable.car_icon,
            R.drawable.bike_icon,
            R.drawable.mobile_icon,
            R.drawable.home_icon,
            R.drawable.services_icon,
            R.drawable.car_icon,
            R.drawable.bike_icon,
            R.drawable.mobile_icon,
            R.drawable.home_icon,

    };*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View vi = inflater.inflate(R.layout.fragment_fragment_main_home, container, false);


        gridView = (GridView) vi.findViewById(R.id.gridview);
        slider1 = (SliderLayout) vi.findViewById(R.id.slider1);

        datalist1 = new ArrayList<HashMap<String, String>>();

        Hash_file_maps = new HashMap<String, Integer>();
        Hash_file_maps.put("Grocery Products", R.drawable.grocery);
        Hash_file_maps.put("Bakery Products", R.drawable.bakery);
        Hash_file_maps.put("Stationary Products", R.drawable.stationary);
        Hash_file_maps.put("Food Items", R.drawable.burgur);
        Hash_file_maps.put("Order at your fingertips", R.drawable.drinks);
        Hash_file_maps.put("Form your nearest shop", R.drawable.sweets);

        loadingslider();
        makeJsonObjReq4();

        /*datalist1 = new ArrayList<HashMap<String, String>>();
        HashMap<String, String>
                map = new HashMap<String, String>();
        map.put("CategoryName", "Car");
        datalist1.add(map);
        map = new HashMap<String, String>();
        map.put("CategoryName", "Bike");
        datalist1.add(map);
        map = new HashMap<String, String>();
        map.put("CategoryName", "Mobile");
        datalist1.add(map);
        map = new HashMap<String, String>();
        map.put("CategoryName", "Home");
        datalist1.add(map);
        map = new HashMap<String, String>();
        map.put("CategoryName", "Service");
        datalist1.add(map);
        map = new HashMap<String, String>();
        map.put("CategoryName", "Car");
        datalist1.add(map);
        map = new HashMap<String, String>();
        map.put("CategoryName", "Bike");
        datalist1.add(map);
        map = new HashMap<String, String>();
        map.put("CategoryName", "Mobile");
        datalist1.add(map);
        map = new HashMap<String, String>();
        map.put("CategoryName", "Home");
        datalist1.add(map);


        Grid_Adapter ga = new Grid_Adapter(getActivity(), datalist1);
        gridView.setAdapter(ga);*/

        return vi;

    }


//---Coding for Calling webservice

    private void makeJsonObjReq4() {
        //---For Display Progressbar
        final ProgressDialog pb=new ProgressDialog(getActivity());
        pb.setMessage("Please wait..");
        pb.show();
//--Replace your url
        final String urlweb= Url.GetUrl("http://ezeonsoft.in/ezeadd/grocerry_category.php?");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlweb, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject result) {
                        Log.d("ressult", result.toString());
//--Dissmiss progress dialog
//                        pb.dismiss();
//---Try catch block and your other parsing

                        try
                        {
//
                            JSONObject jobresponse=result.getJSONObject("response");
                            String status=jobresponse.getString("status");
                            String message=jobresponse.getString("message");

                            if(status.equals("1"))
                            {

                                JSONArray jarrdata=jobresponse.getJSONArray("fetch_category");
                                for(int i=0;i<jarrdata.length();i++)
                                {

                                    JSONObject jobdata=jarrdata.getJSONObject(i);
                                    String category_id= jobdata.getString("category_id");
                                    String category_name= jobdata.getString("category_name");
                                    String category_icon= jobdata.getString("category_icon");
                                    HashMap<String,String>map=new HashMap<String,String>();
                                    map.put(SessionForm.Key_category_id,category_id);
                                    map.put(SessionForm.Key_category_name,category_name);
                                    map.put(SessionForm.Key_category_icon,category_icon);
                                    datalist1.add(map);
                                }

                                Grid_Adapter ap=new Grid_Adapter(getActivity(),datalist1);
                                gridView.setAdapter(ap);

                                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                         SessionForm.SetSharedPreferences(SessionForm.Key_category_id,datalist1.get(position).get(SessionForm.Key_category_id),getActivity());
                                        Fragment fragment= new Fragment_product_list();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).addToBackStack(null).commit();
                                    }
                                });


                            }
                            else
                            {
                                customalert(message,getActivity());
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

                dialog.dismiss();


            }
        });


        dialog.show();
    }


    public void loadingslider()
    {
        for(String name : Hash_file_maps.keySet()){

            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            slider1.addSlider(textSliderView);

            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.makeText(getActivity(),slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
                }
            });
        }
        slider1.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider1.setCustomAnimation(new DescriptionAnimation());
        slider1.setDuration(3000);
        slider1.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Slider Demo", "Page Changed: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




}
