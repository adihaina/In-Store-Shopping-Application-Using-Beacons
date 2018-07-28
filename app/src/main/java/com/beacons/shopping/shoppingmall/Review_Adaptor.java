package com.beacons.shopping.shoppingmall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by adi on 7/5/2018.
 */

public class Review_Adaptor extends RecyclerView.Adapter<Review_Adaptor.ViewHolder>{
    List<Review_model> review_models;
    Context context;

    public Review_Adaptor(List<Review_model>review_models,Context context)
    {
        this.review_models=review_models;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card,parent,false);
        return new Review_Adaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review_model rm=review_models.get(position);

        holder.username.setText(rm.getUsername());
        holder.head.setText(rm.getHeading());
        holder.desc.setText(rm.getDescription());

    }

    @Override
    public int getItemCount() {
        return review_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView head,desc,username;
        public ViewHolder(View itemView) {
            super(itemView);

            head=(TextView)itemView.findViewById(R.id.head);
            desc=(TextView)itemView.findViewById(R.id.desc);
            username=(TextView)itemView.findViewById(R.id.username);


        }
    }
}
