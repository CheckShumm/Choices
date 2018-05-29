package com.example.nathanshumm.decided.view.favorites;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.model.api.Place;
import com.example.nathanshumm.decided.viewmodel.favorites.FavoritesListAdapter;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements Serializable{

    private ListView favoritesList;
    private FavoritesListAdapter mFavoritesListAdapter;
    private ArrayList<Place> placeArrayList;
    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View favoritesView = inflater.inflate(R.layout.fragment_favorites, container, false);
        favoritesList = (ListView)favoritesView.findViewById(R.id.lv_favorites);
        return favoritesView;
    }

    public void loadList(ArrayList<Place> newfavoritesList) {
       if (!newfavoritesList.isEmpty()) {
           mFavoritesListAdapter = new FavoritesListAdapter(this.getContext(), newfavoritesList);
           favoritesList.setAdapter(mFavoritesListAdapter);
            Log.d("loadList", "list size: " + newfavoritesList.size());

        }
    }
}
