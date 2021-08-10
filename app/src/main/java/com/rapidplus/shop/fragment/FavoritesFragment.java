package com.rapidplus.shop.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rapidplus.shop.R;
import com.rapidplus.shop.adapter.VisitAdapter;
import com.rapidplus.shop.model.House;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    Context context;
    VisitAdapter visitAdapter;
    List<House> houseList;
    @BindView(R.id.favorite_rv)
    RecyclerView favoriteRv;
    Unbinder unbinder;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        houseList = new ArrayList<>();
        context = getActivity();
        title.setText(getString(R.string.favorites));
        houseList.add(new House("George avenue", "District 2 1642 avenue, divide roads PO box 3700 S30 225-432", "Visited", "3BHK Villa 1200sqft."));
        houseList.add(new House("Mexixan Villas", "District 2 1642 avenue, divide roads PO box 3700 S30 225-432", "Cancelled", "3BHK Villa 1200sqft."));
        houseList.add(new House("Neywork Villas", "District 2 1642 avenue, divide roads PO box 3700 S30 225-432", "Visited", "3BHK Villa 1200sqft."));
        visitAdapter = new VisitAdapter(houseList, context);
        favoriteRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        favoriteRv.setHasFixedSize(true);
        favoriteRv.setAdapter(visitAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
