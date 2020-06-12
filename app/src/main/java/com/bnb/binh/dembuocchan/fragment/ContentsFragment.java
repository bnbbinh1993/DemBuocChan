package com.bnb.binh.dembuocchan.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bnb.binh.dembuocchan.MapsActivity;
import com.bnb.binh.dembuocchan.R;
import com.bnb.binh.dembuocchan.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ContentsFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tablayout;
    private ViewPagerAdapter adapter;


    public ContentsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contents, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        viewPager = view.findViewById(R.id.viewpager);
        tablayout = view.findViewById(R.id.tablayout);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new TrainingFragment(),"Training");
        adapter.addFragment(new HistoryFragment(),"History");
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }




}
