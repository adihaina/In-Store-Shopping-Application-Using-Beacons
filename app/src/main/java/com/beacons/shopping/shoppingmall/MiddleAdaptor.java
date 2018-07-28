package com.beacons.shopping.shoppingmall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adi on 7/10/2018.
 */

class MiddleAdaptor extends RecyclerView.Adapter<MiddleAdaptor.ViewHolder> {
    Context contet;
    List<String> images;
    public MiddleAdaptor(Context context,List<String> images){
        this.contet=context;
        this.images=images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.middle_card,parent,false);
        return new MiddleAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(contet).load(images.get(position)).placeholder(R.drawable.ph).fit().centerCrop().into(holder.brandimage);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView brandimage;
        public ViewHolder(View itemView) {
            super(itemView);
            brandimage=(ImageView)itemView.findViewById(R.id.s);

        }

    }
}
