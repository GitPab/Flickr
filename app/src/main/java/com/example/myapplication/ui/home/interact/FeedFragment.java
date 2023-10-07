package com.example.myapplication.ui.home.interact;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.home.interact.ImagePostAdapter;
import com.example.myapplication.PostData;
import com.example.myapplication.R;
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.interestingness.InterestingnessInterface;
import com.flickr4java.flickr.people.PeopleInterface;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private Flickr flickr;
    private RecyclerView recyclerView;
    private ImagePostAdapter adapter;
    private TextView loadingText;
    // Initialize the Flickr object with your API key and secret
    private final String API_KEY = "296976d1205b04e0c664b0b42581b362";
    private final String API_SECRET = "69a4af5b3b80751c";

    // Page size for the infinite scroll
    private static final int PAGE_SIZE = 5;
    // Current page
    private int currentPage = 1;
    // Flag to track if a load is in progress
    private boolean isLoading = false;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingText = view.findViewById(R.id.loading_text);

        // Load the first page of data
        loadData();

        recyclerView = view.findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Add a scroll listener to the RecyclerView to detect when the user has scrolled to the end
        // of the list and start loading more data
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems();
                    }
                }
            }
        });
    }

    private void loadData() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> getData = new AsyncTask<Void, Void, Void>() {
            private List<PostData> res;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingText.setVisibility(View.VISIBLE);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    flickr = new Flickr(API_KEY, API_SECRET, new REST());
                    res = getNewsFeedData();
                } catch (FlickrException e) {
                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));
                    Log.d("load data",errors.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                loadingText.setVisibility(View.GONE);

                // Create the adapter and set it on the RecyclerView
                adapter = new ImagePostAdapter(res);
                recyclerView.setAdapter(adapter);
            }
        };

        getData.execute();
    }

    private void loadMoreItems() {
        isLoading = true;
        currentPage++;

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> getData = new AsyncTask <Void, Void, Void>() {
            private List<PostData> res;
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    res = getNewsFeedData();
                } catch (FlickrException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                // Add the new data to the adapter and update the RecyclerView
                adapter.addData(res);
                adapter.notifyDataSetChanged();

                isLoading = false;
            }
        };

        getData.execute();
    }

    private List<PostData> getNewsFeedData() throws FlickrException {
        List<PostData> postData = new ArrayList<>();


        InterestingnessInterface i = flickr.getInterestingnessInterface();
//        PhotoList<Photo> photos = photosInterface.getRecent(null, 4, currentPage);
        PhotoList<Photo> photos = i.getList("2022-12-10", null, PAGE_SIZE, currentPage);

        Log.d("photos",""+photos.size());

        photos.forEach(photo -> {
            // Get username
            String userId = photo.getOwner().getId();

            PeopleInterface peopleInterface = flickr.getPeopleInterface();
            String avatarUrl = "";
            String username = "";
            try {
                username = peopleInterface.getInfo(userId).getUsername();
                avatarUrl = peopleInterface.getInfo(userId).getBuddyIconUrl();
            } catch (FlickrException e) {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                Log.d("flickr error",errors.toString());
            }

//            Log.d("post data",username+ " "+String.valueOf(photo.getStats().getFavorites())+" "+ photo.getTitle()+ " "+photo.getMediumUrl());

            PostData data = new PostData(
                    username,
                    String.valueOf(photo.getStats().getFavorites()),
                    String.valueOf(photo.getStats().getComments()),
                    photo.getTitle(),
                    photo.getMediumUrl(),
                    avatarUrl
            );

            Log.d("post data",data.toString());

            postData.add(data);
        });
        return postData;
    }
}