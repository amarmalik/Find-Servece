package com.example.amazingami.findservice;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityHelpSupport extends AppCompatActivity {
    Button call_num1, call_num2;
    TextView tvmymailid;
    ImageView iv_post_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);

        call_num1 = (Button) findViewById(R.id.call_num1);
        call_num2 = (Button) findViewById(R.id.call_num2);
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
                Intent i=new Intent(ActivityHelpSupport.this,Menu_Holder.class);
                startActivity(i);
                finish();
            }
        });

        call_num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("8052425430"));
                if (ActivityCompat.checkSelfPermission(ActivityHelpSupport.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                if (ActivityCompat.checkSelfPermission(ActivityHelpSupport.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(callIntent);
            }
        });

        call_num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("9045452889"));
                if (ActivityCompat.checkSelfPermission(ActivityHelpSupport.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }});

    }
}
