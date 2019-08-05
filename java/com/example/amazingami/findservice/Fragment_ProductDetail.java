package com.example.amazingami.findservice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Fragment_ProductDetail extends Fragment {

    TextView tvdiscription,tvcost,tvtitle,tvcontact,tvname,tvemail;
    ImageView ivproductview;
    Button btncall;
    String imageurl,owner_mobile;
    Context ctx;
    //int minteger = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fragment__product_detail, container, false);

        tvtitle=(TextView)v.findViewById(R.id.tvtitle);
        ivproductview=(ImageView) v.findViewById(R.id.ivproductview);
        tvcost=(TextView)v.findViewById(R.id.tvcost);
        tvdiscription=(TextView)v.findViewById(R.id.tvdiscription);
        tvcontact=(TextView)v.findViewById(R.id.tvcontact);
        tvname=(TextView)v.findViewById(R.id.tvname);
        tvemail=(TextView)v.findViewById(R.id.tvemail);
        btncall=(Button) v.findViewById(R.id.btncall);


        String title = SessionForm.selectedmap.get(SessionForm.KEY_add_title);
        String description = SessionForm.selectedmap.get(SessionForm.KEY_add_description);
        String add_price = SessionForm.selectedmap.get(SessionForm.KEY_add_price);
        owner_mobile = SessionForm.selectedmap.get(SessionForm.KEY_owner_mobile);
        String owner_name = SessionForm.selectedmap.get(SessionForm.KEY_owner_name);
        String owner_email = SessionForm.selectedmap.get(SessionForm.KEY_owner_email);
        imageurl=SessionForm.selectedmap.get(SessionForm.Key_add_image);

        tvdiscription.setText(description);
        tvtitle.setText(title);
        tvcost.setText(add_price);
        tvcontact.setText(owner_mobile);
        tvname.setText(owner_name);
        tvemail.setText(owner_email);

        //ivproductview.setText(cmap.get(SessionForm.Key_category_icon));
        if(imageurl==null||imageurl.equals(""))
        {
            imageurl="noimage";
        }
        Picasso.with(ctx)
                .load( imageurl)
                .error(R.drawable.hundai)
                .placeholder(R.drawable.ic_loadingimg)
                .into(ivproductview);



       /* btncall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(owner_mobile));
                startActivity(callIntent);
            }});*/


        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                //---add your default calliing number
                intent.setData(Uri.parse("tel:" + MyHelper.selectedservicesmap.get(SessionForm.KEY_owner_mobile)));
                startActivity(intent);

            }
        });
        return v;
    }
}
