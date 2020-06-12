package com.bnb.binh.dembuocchan;

import android.os.Bundle;

import com.bnb.binh.dembuocchan.fragment.ContentsFragment;
import com.bnb.binh.dembuocchan.fragment.HomeFragment;
import com.bnb.binh.dembuocchan.fragment.LichCVFragment;
import com.bnb.binh.dembuocchan.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private BottomNavigationView view;
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.nav_view);
        view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (fragment != null){
                        transaction.remove(fragment);
                    }
                    fragment= new HomeFragment();
                    break;
                case R.id.lichCV:
                    if (fragment != null){
                        transaction.remove(fragment);
                    }
                    fragment =new LichCVFragment();
                    break;
                case R.id.navigation_dashboard:
                    if (fragment != null){
                        transaction.remove(fragment);
                    }
                    fragment = new ContentsFragment();
                    break;
                case R.id.navigation_notifications:
                    if (fragment != null){
                        transaction.remove(fragment);
                    }
                    fragment = new ProfileFragment();
                    break;

            }

            transaction.add(R.id.container,fragment).commit();
            return true;
        }
    };

}
