package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.ui.profile.ViewPagerAdapter;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mTabLayout = view.findViewById(R.id.profile_tab_layout);
        mViewPager = view.findViewById(R.id.profile_view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), 1);
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }
}