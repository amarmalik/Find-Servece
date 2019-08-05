package com.example.amazingami.findservice;

import android.app.Activity;
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

import com.example.amazingami.findservice.R;
import com.example.amazingami.findservice.SessionForm;
import com.squareup.picasso.Picasso;

public class FragmentHomePost extends Fragment {
    TextView tvaddpost, tvaddmyadd;

    //int minteger = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment__product_detail, container, false);

        tvaddpost = (TextView) v.findViewById(R.id.tvaddpost);
        tvaddmyadd = (TextView) v.findViewById(R.id.tvaddmyadd);

        /*tvaddmyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Activity_AddPost.class);
                startActivity(intent);
            }
        });*/
        /*tvaddmyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Activity_AddPost.class);
                startActivity(intent);
            }
        });*/
        return v;
    }



}


