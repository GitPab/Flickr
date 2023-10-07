package com.example.myapplication.ui.home.interact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.interact.LikeAndCommentPageAdapter;

import com.google.android.material.tabs.TabLayout;

public class LikeAndCommentFragment extends Fragment {
    private ImageButton backBtn;

    public LikeAndCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_like_and_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LikeAndCommentPageAdapter adapter = new LikeAndCommentPageAdapter(getChildFragmentManager());
        ViewPager pager = view.findViewById(R.id.likeAndCommentPager);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.likeAndCommentTab);
        tabLayout.setupWithViewPager(pager);

        backBtn = view.findViewById(R.id.backBtn);
        setOnBack();
    }

    private void setOnBack() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack(null,0);
            }
        });
    }
}