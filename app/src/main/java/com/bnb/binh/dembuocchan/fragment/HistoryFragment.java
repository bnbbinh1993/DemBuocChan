package com.bnb.binh.dembuocchan.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.dembuocchan.R;
import com.bnb.binh.dembuocchan.adapter.HistoryAdapter;
import com.bnb.binh.dembuocchan.models.HIstory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<HIstory> hIstoryList;

    public HistoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        init(view);
        initEvent();
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewHistory);
    }

    private void initEvent() {
        hIstoryList = new ArrayList<>();
        addData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new HistoryAdapter(getContext(), hIstoryList);
        recyclerView.setAdapter(adapter);
    }

    private void addData() {
        //data mãu
        hIstoryList.add(new HIstory("Chạy"));
        hIstoryList.add(new HIstory("Chạy"));
        hIstoryList.add(new HIstory("Đi bộ"));
        hIstoryList.add(new HIstory("Đi bộ"));
        hIstoryList.add(new HIstory("Đi bộ"));
        hIstoryList.add(new HIstory("Chạy"));
        hIstoryList.add(new HIstory("Chạy"));
        hIstoryList.add(new HIstory("Chạy"));
        hIstoryList.add(new HIstory("Chạy"));
    }

}
