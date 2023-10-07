package com.example.myapplication.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.groups.Group;
import com.flickr4java.flickr.groups.GroupsInterface;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

public class GroupsFragment2 extends Fragment {
    private RecyclerView rcvGroups;
    private GroupsAdapter groupsAdapter;
    private SharedPreferences sharedPreferences;
    private final String API_KEY = "296976d1205b04e0c664b0b42581b362";
    private final String API_SECRET = "69a4af5b3b80751c";
    private static final int PAGE_SIZE = 5;
    private int currentPage = 1;
    private boolean isLoading = false;
    private Flickr flickr;
    private String namePackage;
    public GroupsFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups_search, container, false);
        rcvGroups = view.findViewById(R.id.rcv_groups);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rcvGroups.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rcvGroups.addItemDecoration(itemDecoration);
        sharedPreferences = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
        loadItems();
        // Add a scroll listener to the RecyclerView to detect when the user has scrolled to the end
        // of the list and start loading more data
        rcvGroups.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        Log.e("loading....",currentPage+"");
                        loadMoreItems();
                    }
                }
            }
        });
        return view;
    }
    private void loadItems() {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Void> getData = new AsyncTask<Void, Void, Void>() {
            private Collection<Group> res;

            @Override
            protected Void doInBackground(Void... voids) {

                flickr = new Flickr(API_KEY, API_SECRET, new REST());
                GroupsInterface groupsInterface = flickr.getGroupsInterface();



                try {
                    res = groupsInterface.search(sharedPreferences.getString("Search",""),PAGE_SIZE,currentPage);
                    Log.e("what",res.toString());
                } catch (FlickrException e) {
                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));
                    Log.d("photo search data",errors.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                // Create the adapter and set it on the RecyclerView
                groupsAdapter = new GroupsAdapter(res);
                rcvGroups.setAdapter(groupsAdapter);
            }
        };

        getData.execute();
    }


    private void loadMoreItems() {
        isLoading = true;
        currentPage++;
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Void> getData = new AsyncTask<Void, Void, Void>() {
            private Collection<Group> res;

            @Override
            protected Void doInBackground(Void... voids) {

                flickr = new Flickr(API_KEY, API_SECRET, new REST());
                GroupsInterface groupsInterface = flickr.getGroupsInterface();



                try {
                    res = groupsInterface.search(sharedPreferences.getString("Search",""),PAGE_SIZE,currentPage);
                    Log.e("what",res.toString());
                } catch (FlickrException e) {
                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));
                    Log.d("photo search data",errors.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                // Create the adapter and set it on the RecyclerView
                groupsAdapter.addData(res);
                groupsAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        };

        getData.execute();
    }


}