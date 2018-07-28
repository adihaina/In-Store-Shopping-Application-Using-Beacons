package com.beacons.shopping.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by adi on 7/1/2018.
 */

class CartAdaptor extends RecyclerView.Adapter <CartAdaptor.ViewHolder>{
    ArrayAdapter<String> dataAdapter;
    List<String> producttype,productid,sizes_of_product;
    List<Upload> uploadlist;
    Context mcontext;
    DatabaseReference mdataref,mdataref2;
    public CartAdaptor(List<Upload> uplodlist,List<String>productids,Context context,List<String> producttype,List<String> sizes_of_product)
    {
        mcontext=context;
        this.sizes_of_product=sizes_of_product;
        this.uploadlist=uplodlist;
        this.productid=productids;
        this.producttype=producttype;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card_layout,parent,false);
        return new CartAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Upload upload=uploadlist.get(position);
        holder.product_name.setText(upload.getName());
        holder.price.setText("₹"+upload.getPrice());
        holder.offers.setText(upload.getOffers());
        Picasso.with(mcontext).load(upload.getImageurl().toString()).placeholder(R.drawable.ph).fit().centerCrop().into(holder.imageview);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Network.isNetworkEnabled(mcontext))
                    Toast.makeText(mcontext,"Not Connected To Internet",Toast.LENGTH_SHORT).show();

                else{
                mdataref=FirebaseDatabase.getInstance().getReference("Cart/"+ FirebaseAuth.getInstance().getCurrentUser().getUid().toString()+"/"+productid.get(position));
                mdataref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });


            }}
        });
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.sizes.getSelectedItem().equals("Select-Size")||holder.sizes.getSelectedItem().equals("Select-Size "))
                {
                    holder.sizes.requestFocus();
                    Toast.makeText(mcontext,"Please Select A Size",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!Network.isNetworkEnabled(mcontext))
                        Toast.makeText(mcontext,"Not Connected To Internet",Toast.LENGTH_SHORT).show();
                    else{
                mdataref2=FirebaseDatabase.getInstance().getReference("Orders/"+FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                order_model om=new order_model(upload.getImageurl(),upload.getName().toString(),producttype.get(position).toString(),upload.getPrice().toString(),holder.sizes.getSelectedItem().toString(),productid.get(position).toString(),"not-paid",uploadlist.get(position).getRating());

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = new Date();
                mdataref2.child(formatter.format(date)).setValue(om).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mcontext,"Successfully Ordered\nPlease pay at th counter\nAnd recieve your Order",Toast.LENGTH_SHORT).show();
                    }
                });}}}
        });

    List<String> spinnerArray = Arrays.asList(("Select-Size "+sizes_of_product.get(position)).split(" "));
         dataAdapter = new ArrayAdapter<String>(mcontext, android.R.layout.simple_spinner_item, spinnerArray);
             dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.sizes.setAdapter(dataAdapter);





    }

    @Override
    public int getItemCount() {
        return uploadlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,offers,price;
        ImageView imageview;
        Button remove,buy;
        Spinner sizes;

        public ViewHolder(View itemView) {
            super(itemView);
            sizes=(Spinner)itemView.findViewById(R.id.size_spinner);
            buy=(Button)itemView.findViewById(R.id.buy_button);
            price=(TextView)itemView.findViewById(R.id.price);
            remove=(Button)itemView.findViewById(R.id.remove_button);
            product_name=(TextView)itemView.findViewById(R.id.produt_name);
            offers=(TextView)itemView.findViewById(R.id.offers);
            imageview=(ImageView)itemView.findViewById(R.id.imageview);

        }
    }
}
