package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

;

public class AboutFragment extends Fragment {

    String[] aboutArray = {"Photos", "Description", "Occupation", "Current City", "Hometown", "Website", "Facebook", "Twitter", "Instagram", "Email", "Date Joined"};

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_profile, container, false);

        ArrayAdapter adapter = new ArrayAdapter<String>(this.getContext(), R.layout.activity_listview, aboutArray);

        ListView listView = view.findViewById(R.id.about_list);
        listView.setAdapter(adapter);
        return view;
    }
}
