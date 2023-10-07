package com.example.myapplication.ui.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    public EditText searchbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Button button;
    private final String API_KEY = "296976d1205b04e0c664b0b42581b362";
    private final String API_SECRET = "69a4af5b3b80751c";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
//        search = (EditText) view.findViewById(R.id.search_bar);

        mTabLayout = view.findViewById(R.id.search_tab_layout);
        mViewPager = view.findViewById(R.id.search_view_pager);
        searchbar = view.findViewById(R.id.search_bar);

        sharedPreferences = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), 1);
        searchbar.setText(sharedPreferences.getString("Search",""));
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Search",charSequence.toString());
                editor.apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        button = view.findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), 1);
                mViewPager.setAdapter(viewPagerAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
                try {

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE) ;
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        return view;
    }
}