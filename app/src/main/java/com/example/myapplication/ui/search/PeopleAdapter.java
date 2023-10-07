package com.example.myapplication.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>{
    private final List<People> mListPeople;

    public PeopleAdapter(List<People> mListPeople){
        this.mListPeople = mListPeople;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people, parent, false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        People people = mListPeople.get(position);
        if(people == null){
            return;
        }

        try {
            Picasso.get().load(people.avatar).into(holder.imgPeople);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            holder.imgPeople.setImageResource(people.getImage());
        }


        holder.tvName.setText(people.getName());
        holder.tvPhotos.setText(people.getPhotos());
    }

    @Override
    public int getItemCount() {
        if(mListPeople != null){
            return mListPeople.size();
        }
        return 0;
    }

    public static class PeopleViewHolder extends RecyclerView.ViewHolder{
        private final CircleImageView imgPeople;
        private final TextView tvName;
        private final TextView tvPhotos;

        public PeopleViewHolder(@NonNull View itemView){
            super(itemView);
            imgPeople = itemView.findViewById(R.id.img_people);
            tvName = itemView.findViewById(R.id.tv_name_people);
            tvPhotos = itemView.findViewById(R.id.tv_photos_people);
        }
    }
}
