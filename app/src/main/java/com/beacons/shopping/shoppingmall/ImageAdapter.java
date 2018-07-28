package com.beacons.shopping.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adi on 6/18/2018.
 */

class ImageAdapter extends RecyclerView.Adapter <ImageAdapter.ViewHolder>{
    public List<Upload> shirts;
    public List<String> productIds;
    public Context mcontext;
    public String product_type;

    public ImageAdapter(List<Upload> shirts,List<String> productIds,Context context,String producttype) {
        this.shirts=shirts;
        this.productIds=productIds;
        this.product_type=producttype;
        this.mcontext=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shirt_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Upload shirt=shirts.get(position);
        holder.text1.setText(shirt.getName());
        holder.price_tag.setText("₹"+shirt.getPrice());
        holder.offers_text.setText(shirt.getOffers());
        Picasso.with(mcontext).load(shirt.getImageurl().toString()).fit().centerCrop().into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext,Product_Activity.class);
                intent.putExtra("product_type",product_type);
                intent.putExtra("productid",productIds.get(position));
                intent.putExtra("Name",shirt.getName());
                intent.putExtra("Brand",shirt.getBrand());
                intent.putExtra("Color",shirt.getColor());
                intent.putExtra("Price",shirt.getPrice());
                intent.putExtra("Rating",shirt.getRating());
                intent.putExtra("Offers",shirt.getOffers());
                intent.putExtra("Imageurl",shirt.getImageurl());
                mcontext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return shirts.size();
    }

    public void filterlist(List<Upload> filteredShirts, List<String> filtered_productids) {
        shirts=filteredShirts;
        productIds=filtered_productids;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView img;
        public TextView text1,price_tag,offers_text;

        public ViewHolder(View ItemView)
        {
            super(ItemView);
            offers_text=(TextView)ItemView.findViewById(R.id.offers_textview);
            img=(ImageView)ItemView.findViewById(R.id.img);
            text1=(TextView)ItemView.findViewById(R.id.text1);
            price_tag=(TextView)ItemView.findViewById(R.id.price_tag);
        }
    }
}
