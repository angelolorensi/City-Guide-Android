package com.example.cityguidetutorial.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguidetutorial.R;

import java.util.ArrayList;

public class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.MostViewedHolder> {

    ArrayList<MostViewedHelper> mostViewedLocations;

    public MostViewedAdapter(ArrayList<MostViewedHelper> mostViewedLocations) {
        this.mostViewedLocations = mostViewedLocations;
    }

    @NonNull
    @Override
    public MostViewedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_viewed_card_design, parent, false);
        MostViewedHolder mostViewedHolder = new MostViewedHolder(view);
        return mostViewedHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MostViewedHolder holder, int position) {

        MostViewedHelper mostViewedHelper = mostViewedLocations.get(position);

        holder.image.setImageResource(mostViewedHelper.getImage());
        holder.title.setText(mostViewedHelper.getTitle());
        holder.desc.setText(mostViewedHelper.getDescription());

    }

    @Override
    public int getItemCount() {
        return mostViewedLocations.size();
    }

    public static class MostViewedHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc;

        public MostViewedHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.mv_img);
            title = itemView.findViewById(R.id.mv_title);
            desc = itemView.findViewById(R.id.mv_desc);

        }

    }
}
