package com.example.amazingami.findservice;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.HashMap;

/**
 * Created by Sarvesh on 27-06-2017.
 */

public class MyHelper {
    public static HashMap<String,String>selectedmap=new HashMap<String,String>();

    public static HashMap<String,String>selectedservicesmap=new HashMap<String,String>();

    public static String currentpage="home";

    public static int isvarificaiton=0;


    public static void customlogout(String Msg,final Activity act)
    {

        //Toast.makeText(getApplicationContext(), "Main Act Back", Toast.LENGTH_LONG).show();
        // or just go back to main activity

        final Dialog dialog = new Dialog(act);
        Log.i("Dialog", "Created");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.customalert_confirm_red);
        dialog.closeOptionsMenu();
        Button btnok = (Button)dialog.findViewById(R.id.btnok);
        Button btncancel  = (Button)dialog.findViewById(R.id.btncancel);
        btnok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

           dialog.dismiss();

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
        dialog.setCancelable(true);

        //super.onBackPressed();

    }



}
