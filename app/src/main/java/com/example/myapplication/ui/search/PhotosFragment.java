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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;

import java.io.PrintWriter;
import java.io.StringWriter;


public class PhotosFragment extends Fragment {
    private Flickr flickr;
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;
    private final String API_KEY = "296976d1205b04e0c664b0b42581b362";
    private final String API_SECRET = "69a4af5b3b80751c";
    private SharedPreferences sharedPreferences;

    private static final int PAGE_SIZE = 5;

    private int currentPage = 1;

    private boolean isLoading = false;
    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos_search, container, false);
        recyclerView = view.findViewById(R.id.rcv_photo);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        sharedPreferences = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
        loadItems();
        // Add a scroll listener to the RecyclerView to detect when the user has scrolled to the end
        // of the list and start loading more data
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

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
            private PhotoList<Photo> res;

            @Override
            protected Void doInBackground(Void... voids) {

                flickr = new Flickr(API_KEY, API_SECRET, new REST());
                PhotosInterface photosInterface = flickr.getPhotosInterface();

                SearchParameters searchParameters = new SearchParameters();
                searchParameters.setText(sharedPreferences.getString("Search",""));
                Log.e("what",searchParameters.getText());
                try {
                    res = photosInterface.search(searchParameters,PAGE_SIZE,currentPage);
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
                adapter = new PhotoAdapter(res);
                recyclerView.setAdapter(adapter);
            }
        };

        getData.execute();
    }
    private void loadMoreItems() {
        isLoading = true;
        currentPage++;
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Void> getData = new AsyncTask<Void, Void, Void>() {
            private PhotoList<Photo> res;
            @Override
            protected Void doInBackground(Void... voids) {

                flickr = new Flickr(API_KEY, API_SECRET, new REST());
                PhotosInterface photosInterface = flickr.getPhotosInterface();

                SearchParameters searchParameters = new SearchParameters();
                searchParameters.setText(sharedPreferences.getString("Search",""));
                try {
                    res = photosInterface.search(searchParameters,PAGE_SIZE,currentPage);
                    Log.e("what",res.toString());
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

                // Create the adapter and set it on the RecyclerView
                adapter.addData(res);
                adapter.notifyDataSetChanged();
                isLoading = false;

            }
        };

        getData.execute();
    }

}