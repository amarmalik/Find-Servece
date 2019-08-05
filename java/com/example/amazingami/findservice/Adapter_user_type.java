package com.example.amazingami.findservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amazing Ami on 26-06-2017.
 */

public class Adapter_user_type extends BaseAdapter {
    Context context;
    View view;
    TextView tvsearch;
    LayoutInflater layoutInflater=null;
    ArrayList<HashMap<String,String>>user_type_list;

    public Adapter_user_type(Context contextf, ArrayList<HashMap<String,String>>dataf) {
        context = contextf;
        user_type_list = dataf;
        layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return user_type_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup Parent) {

        if (convertView == null) {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.row_search_list, null);
            tvsearch = (TextView) view.findViewById(R.id.tvsearch);
            HashMap<String,String>cmap=user_type_list.get(position);

            tvsearch.setText(cmap.get(SessionForm.KEY_user_type_name));

        }
    return view;
    }


}
