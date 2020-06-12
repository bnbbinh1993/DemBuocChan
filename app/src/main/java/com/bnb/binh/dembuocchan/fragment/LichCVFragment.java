package com.bnb.binh.dembuocchan.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bnb.binh.dembuocchan.R;
import com.bnb.binh.dembuocchan.adapter.homeAdapter;
import com.bnb.binh.dembuocchan.models.LichTrinh;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LichCVFragment extends Fragment {
    private RecyclerView recyclerviewHome;
    private homeAdapter adapter;
    private List<LichTrinh> lichTrinhList;
    public LichCVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lich_cv, container, false);
        init(view);
        initEvent();
        return view;
    }
    private void init(View view) {
        recyclerviewHome = view.findViewById(R.id.recyclerviewHome);
    }
    private void initEvent(){
        lichTrinhList = new ArrayList<>();
        addData();
        adapter = new homeAdapter(getContext(),lichTrinhList);
        recyclerviewHome.setHasFixedSize(true);
        recyclerviewHome.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerviewHome.setAdapter(adapter);
    }

    private void addData() {
        lichTrinhList.add(new LichTrinh("Đi chơi với bạn"));
        lichTrinhList.add(new LichTrinh("Chạy bộ thể dục"));
        lichTrinhList.add(new LichTrinh("Đi chơi net"));
        lichTrinhList.add(new LichTrinh("Đi học trên trường"));
        lichTrinhList.add(new LichTrinh("Đi đá phò"));
    }

}
