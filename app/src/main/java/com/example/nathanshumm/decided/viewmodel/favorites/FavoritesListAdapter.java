package com.example.nathanshumm.decided.viewmodel.favorites;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.model.api.Place;

import java.util.ArrayList;

public class FavoritesListAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Place> placeArrayList;

    public FavoritesListAdapter(Context mContext, ArrayList<Place> placeArrayList) {
        this.mContext = mContext;
        this.placeArrayList = placeArrayList;
    }

    @Override
    public int getCount() {
        return this.placeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.placeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.list_favorites, null);

        TextView title = (TextView)v.findViewById(R.id.lv_title);
        Button delBtn = (Button)v.findViewById(R.id.lv_button);

        title.setText(placeArrayList.get(position).getName());

        return v;
    }
}
