package com.example.amazingami.findservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_Frontpage extends Activity {
    final int splash_time = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__frontpage);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Activity_Frontpage.this, LoginActivity.class);
                intent.putExtra("loginby", "Guest");
                startActivity(intent);
                finish();

            }
        }, splash_time);



        }
}
