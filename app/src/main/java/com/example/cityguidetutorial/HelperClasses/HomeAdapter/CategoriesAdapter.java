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

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    ArrayList<CategoriesHelper> categories;

    public CategoriesAdapter(ArrayList<CategoriesHelper> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_card_design, parent, false);
        CategoriesViewHolder categoriesViewHolder = new CategoriesViewHolder(view);
        return categoriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

        CategoriesHelper categoriesHelper = categories.get(position);

        holder.image.setImageResource(categoriesHelper.getImage());
        holder.title.setText(categoriesHelper.getTitle());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.categories_image);
            title = itemView.findViewById(R.id.categories_title);
        }
    }
}
