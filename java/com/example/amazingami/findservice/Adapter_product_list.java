package com.example.amazingami.findservice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amazing Ami on 23-06-2017.
 */

public class Adapter_product_list extends BaseAdapter {

    Context ctx;
    ArrayList<HashMap<String, String>> arrcar;
    LayoutInflater inflater = null;
    TextView tvdiscription,tvcost,tvtitle;
    ImageView ivproductview;
    String imageurl;
    public Adapter_product_list(Context ctxf, ArrayList<HashMap<String, String>> dataf) {
        ctx = ctxf;
        arrcar = dataf;
        inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrcar.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View   v = inflater.inflate(R.layout.row_product_list, null);

        tvtitle=(TextView)v.findViewById(R.id.tvtitle);
        tvcost=(TextView)v.findViewById(R.id.tvcost);
       // tvdiscription=(TextView)v.findViewById(R.id.tvdiscription);
        ivproductview=(ImageView)v.findViewById(R.id.ivproductview);



        HashMap<String, String> cmap = arrcar.get(position);

        String title = cmap.get(SessionForm.KEY_add_title);
        //String description = cmap.get(SessionForm.KEY_add_description);
        String add_price = cmap.get(SessionForm.KEY_add_price);
         imageurl=cmap.get(SessionForm.Key_add_image);

        //tvdiscription.setText(description);
        tvtitle.setText(title);
        tvcost.setText(add_price);

       //ivproductview.setText(cmap.get(SessionForm.Key_category_icon));

        Picasso.with(ctx)
                .load( imageurl)
                .error(R.drawable.hundai)
                .placeholder(R.drawable.ic_loadingimg)
                .into(ivproductview);
        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }
}
