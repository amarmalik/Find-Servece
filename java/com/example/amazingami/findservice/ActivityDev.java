package com.example.amazingami.findservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityDev extends AppCompatActivity {
    TextView tvmymailid;
    ImageView iv_post_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);

        tvmymailid=(TextView)findViewById(R.id.tvmymailid);
        iv_post_back=(ImageView)findViewById(R.id.iv_post_back);

        tvmymailid.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tvmymailid.setText(Html.fromHtml("<a href=\"mailto:amarmalik715@gmail.com\">Send Feedback</a>"));
                tvmymailid.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
        iv_post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityDev.this,Menu_Holder.class);
                startActivity(i);
                finish();
            }
        });
    }
}
