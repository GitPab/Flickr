package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.ui.camera.CameraFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.notifications.NotificationsFragment;
import com.example.myapplication.ui.profile.ProfileFragment;
import com.example.myapplication.ui.search.SearchFragment;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;

public class  MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if(itemId == R.id.home){
                replaceFragment(new HomeFragment());
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            else if(itemId == R.id.search){
                replaceFragment(new SearchFragment());
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            else if(itemId == R.id.profile){
                replaceFragment(new ProfileFragment());
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            else if(itemId == R.id.notifications){
                replaceFragment(new NotificationsFragment());
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            else if(itemId == R.id.camera){
                replaceFragment(new CameraFragment());
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }

            return false;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}