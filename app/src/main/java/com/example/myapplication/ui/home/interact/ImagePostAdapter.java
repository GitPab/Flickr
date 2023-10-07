package com.example.myapplication.ui.home.interact;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.PostData;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class ImagePostAdapter extends RecyclerView.Adapter<ImagePostAdapter.ViewHolder> {

    private List<PostData> postData;

    public ImagePostAdapter(List<PostData> postData) {
        this.postData = postData;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPhotos(List<PostData> postData) {
        this.postData = postData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostData data = postData.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView avatar;
        TextView usernameTextView;
        TextView postTitle;
        TextView favorites;
        TextView comments;
        TextView commentsTotal;
        TextView recommendationTextView;
        ImageButton moreButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.post_img);
            avatar = itemView.findViewById(R.id.feed_avatar);
            postTitle = itemView.findViewById(R.id.post_title);
            favorites = itemView.findViewById(R.id.feed_favorites);
            comments = itemView.findViewById(R.id.feed_comment);
            commentsTotal = itemView.findViewById(R.id.feed_total_comments);
            usernameTextView = itemView.findViewById(R.id.feed_username);
            recommendationTextView = itemView.findViewById(R.id.feed_recommend);
            moreButton = itemView.findViewById(R.id.feed_more);
        }

        @SuppressLint("SetTextI18n")
        public void bind(PostData postData) {
            // Set the data for the views using the Photo object

            try {
                Picasso.get().load(postData.imageUrl).into(imageView);
                Picasso.get().load(postData.avatarUrl).error(R.drawable.mainbg).into(avatar);
            } catch (Exception e) {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                Log.d("picasso",errors.toString());
            }

            // Set the username and recommendation text using the data from the Photo object
            usernameTextView.setText(postData.username);
            postTitle.setText(postData.title);
            recommendationTextView.setText("Recommended");
            favorites.setText(postData.favorites);
            comments.setText(postData.comments);
            commentsTotal.setText(postData.comments + " of " + postData.comments);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<PostData> newData) {
        postData.addAll(newData);
        notifyDataSetChanged();
    }
}
