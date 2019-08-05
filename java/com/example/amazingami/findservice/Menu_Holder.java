package com.example.amazingami.findservice;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu_Holder extends FragmentActivity {

    ImageView imgmenu;
    FrameLayout frameLayout;
    DrawerLayout drawer_menu__holder;
    LinearLayout llleftslidemenu,ll_select_city;
LinearLayout lnpostadd,lnhome,lnnotification,lnlogout,lndeveloper,lncontact,lnprofile,lnmypost,lnwishlist;
    TextView txthome,txtnoti,txtwish,txtlogout,txtmypost,txtcontact,txtdev,tvselectcity,txtpostadd,txtprofile;
    //SliderLayout sliderimage;
    HashMap<String,Integer>Hash_file_maps;
    ArrayList<HashMap<String,String>>datalist;
String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_menu__holder);
       lnhome=(LinearLayout)findViewById(R.id.lnhome);
       lnnotification=(LinearLayout)findViewById(R.id.lnnotification);
       lnpostadd=(LinearLayout)findViewById(R.id.lnpostadd);
       lnmypost=(LinearLayout)findViewById(R.id.lnmypost);
       lncontact=(LinearLayout)findViewById(R.id.lncontact);
       lndeveloper=(LinearLayout)findViewById(R.id.lndeveloper);
       lnlogout=(LinearLayout)findViewById(R.id.lnlogout);
       lnprofile=(LinearLayout)findViewById(R.id.lnprofile);
       lnwishlist=(LinearLayout)findViewById(R.id.lnwishlist);
        imgmenu=(ImageView)findViewById(R.id.imgmenu);
        txthome=(TextView)findViewById(R.id.txthome);
        txtmypost=(TextView)findViewById(R.id.txtmypost);
        txtpostadd=(TextView)findViewById(R.id.txtpostadd);
        txtnoti=(TextView)findViewById(R.id.txtnoti);
        txtwish=(TextView)findViewById(R.id.txtwish);
        txtlogout=(TextView)findViewById(R.id.txtlogout);
        txtcontact=(TextView)findViewById(R.id.txtcontact);
        txtprofile=(TextView)findViewById(R.id.txtprofile);
        txtdev=(TextView)findViewById(R.id.txtdev);
        tvselectcity=(TextView)findViewById(R.id.tvselectcity);
        llleftslidemenu=(LinearLayout)findViewById(R.id.llleftslidemenu);
        ll_select_city=(LinearLayout)findViewById(R.id.ll_select_city);
String cityname=SessionForm.GetSharedPreferences(SessionForm.Key_city_name,Menu_Holder.this);
String areaname=SessionForm.GetSharedPreferences(SessionForm.Key_area_name,Menu_Holder.this);
        frameLayout=(FrameLayout)findViewById(R.id.framelayout);
        drawer_menu__holder=(DrawerLayout)findViewById(R.id.drawer_menu__holder);
        drawer_menu__holder.setScrimColor(Color.parseColor("#80000000"));
        tvselectcity.setText(areaname+","+cityname);

        type=SessionForm.GetSharedPreferences(SessionForm.KEY_user_type,Menu_Holder.this);

        if(type.equals("1"))
        {
            lnhome.setVisibility(View.VISIBLE);
            lnnotification.setVisibility(View.VISIBLE);
            lncontact.setVisibility(View.VISIBLE);
            lnwishlist.setVisibility(View.VISIBLE);
            lnprofile.setVisibility(View.VISIBLE);
            lndeveloper.setVisibility(View.VISIBLE);
            lnlogout.setVisibility(View.VISIBLE);
            lnpostadd.setVisibility(View.GONE);
            lnmypost.setVisibility(View.GONE);

            Fragment Frag=new FragmentMainHome();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,Frag).commit();
            drawer_menu__holder.closeDrawer(llleftslidemenu);

        }
        else
        {
            lnhome.setVisibility(View.GONE);
            lnwishlist.setVisibility(View.GONE);
            lnnotification.setVisibility(View.VISIBLE);
            lnpostadd.setVisibility(View.VISIBLE);
            lnmypost.setVisibility(View.VISIBLE);
            lndeveloper.setVisibility(View.VISIBLE);
            lnprofile.setVisibility(View.VISIBLE);
            lnlogout.setVisibility(View.VISIBLE);
            lncontact.setVisibility(View.VISIBLE);

            Fragment Frag=new Fragment_product_list();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,Frag).commit();
            drawer_menu__holder.closeDrawer(llleftslidemenu);
        }

        ll_select_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Menu_Holder.this, Activity_CityChoice.class);
                startActivity(in);
            }
        });

        txtprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Menu_Holder.this, ActivityProfile.class);
                startActivity(in);
            }
        });
        txtdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Menu_Holder.this, ActivityDev.class);
                startActivity(in);
            }
        });
        txtcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Menu_Holder.this, ActivityHelpSupport.class);
                startActivity(in);
            }
        });

        txtpostadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionForm.GetSharedPreferences(SessionForm.KEY_loginstatus, Menu_Holder.this).equals("true")) {
                Intent in=new Intent(Menu_Holder.this,Activity_AddPost.class);
                startActivity(in);
                } else {
                    //Toast.makeText(Menu_Holder.this, "Please login first...", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(Menu_Holder.this,LoginActivity.class);
                    startActivity(in);
                }
            }
        });
        txtmypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionForm.GetSharedPreferences(SessionForm.KEY_loginstatus, Menu_Holder.this).equals("true")) {
                    Intent in = new Intent(Menu_Holder.this, Activity_Myadd.class);
                    startActivity(in);
                } else {
                    //Toast.makeText(Menu_Holder.this, "Please login first...", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(Menu_Holder.this,LoginActivity.class);
                    startActivity(in);
                }
            }
        });

        /*txtselectcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(Menu_Holder.this,Activity_search.class);
                startActivity(in);
            }
        });*/

        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionForm.SetSharedPreferences(SessionForm.KEY_loginstatus,"false",Menu_Holder .this);
                Intent i = new Intent(Menu_Holder.this, LoginActivity.class);
                startActivity(i);

            }
        });
        txthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvselectcity.setText("Select City");
                changefragment(new FragmentMainHome());
            }
        });


        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer_menu__holder.isDrawerOpen(llleftslidemenu)){
                    drawer_menu__holder.closeDrawer(llleftslidemenu);

                }else{
                    drawer_menu__holder.openDrawer(llleftslidemenu);
                }
            }
        });


    }

    public void changefragment(Fragment fragment)
    {
        //--change fetched fragment to frame layout

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).addToBackStack(null).commit();

        drawer_menu__holder.closeDrawer(llleftslidemenu);
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()!=0)
        {
            changefragment(new FragmentMainHome());
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount()==0)
        {
            //changeFragment(new Home());
            finish();
        }
        else {
            finish();
        }
    }


}
