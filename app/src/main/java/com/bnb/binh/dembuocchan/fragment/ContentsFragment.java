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

import com.bnb.binh.dembuocchan.MapsActivity;
import com.bnb.binh.dembuocchan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentsFragment extends Fragment {

    private ImageButton btnRemove, btnAdd;
    private Button btnStart;
    private TextView tvKm;
    public static int cout = 2000;

    public ContentsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contents, container, false);
        init(view);
        listenClickView();
        return view;
    }

    private void init(View view) {
        btnRemove = view.findViewById(R.id.btnRemove);
        btnAdd = view.findViewById(R.id.btnAdd);
        tvKm = view.findViewById(R.id.tvKm);
        btnStart = view.findViewById(R.id.btnStart);
    }

    private void listenClickView() {
        tvKm.setText(String.valueOf(cout / 1000) + "." + (cout % 1000) / 100);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cout > 0) {
                    cout = cout - 100;
                    tvKm.setText(String.valueOf(cout / 1000) + "." + (cout % 1000) / 100);
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cout = cout + 100;
                tvKm.setText(String.valueOf(cout / 1000) + "." + (cout % 1000) / 100);
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MapsActivity.class));
            }
        });
    }

}
