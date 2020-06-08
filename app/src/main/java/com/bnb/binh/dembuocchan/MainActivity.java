package com.bnb.binh.dembuocchan;

import android.os.Bundle;

import com.bnb.binh.dembuocchan.Fragment.ContentsFragment;
import com.bnb.binh.dembuocchan.Fragment.HomeFragment;
import com.bnb.binh.dembuocchan.Fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private BottomNavigationView view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.nav_view);
        view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setHome();
    }
    private void setHome(){
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,homeFragment).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setHome();
                    return true;
                case R.id.navigation_dashboard:
                    ContentsFragment contents = new ContentsFragment();
                    FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.container,contents).commit();
                    return true;
                case R.id.navigation_notifications:
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.add(R.id.container,profileFragment).commit();
                    return true;
            }
            return false;
        }
    };

}
