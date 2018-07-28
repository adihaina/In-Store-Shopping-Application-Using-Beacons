package com.beacons.shopping.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adi on 7/4/2018.
 */

class OrderAdaptor extends RecyclerView.Adapter<OrderAdaptor.ViewHolder> {
    Context context;
    List<order_model> orderlist;
    List<String> date_time;

    public OrderAdaptor(Context context,List<order_model>orderlist,List<String> date_time)
    {
        this.context=context;
        this.orderlist=orderlist;
        this.date_time=date_time;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cart_layout,parent,false);
        return new OrderAdaptor.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final order_model om=orderlist.get(position);
        holder.name.setText(om.getProduct_name());
        holder.price.setText("₹"+om.getPrice());
        holder.size.setText("Size : "+om.getSize());
        if(!om.getStatus().equals("not-paid"))
        {
            holder.verification.setTextColor(Color.parseColor("#228B22"));
            holder.verification.setText(om.getStatus());
            holder.cancel.setVisibility(View.GONE);
            holder.review.setVisibility(View.VISIBLE);

        }
        else
            {holder.verification.setTextColor(Color.parseColor("#FF0000"));
                holder.verification.setText(om.getStatus());
                holder.cancel.setVisibility(View.VISIBLE);
                holder.review.setVisibility(View.GONE);
            }
        holder.date.setText(date_time.get(position));

        Picasso.with(context).load(om.getImage_url().toString()).placeholder(R.drawable.ph).fit().centerCrop().into(holder.imageView);
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Network.isNetworkEnabled(context))
                    Toast.makeText(context,"Not Connected To Internet",Toast.LENGTH_SHORT).show();
                else{
                FirebaseDatabase.getInstance().getReference("Orders/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+date_time.get(position)).removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(context,"Order Cancelled",Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
            }}
        });

            holder.review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,Product_review.class);
                    intent.putExtra("product_id",om.getProduct_id());
                    intent.putExtra("product_type",om.getProduct_type());
                    intent.putExtra("rating",om.getRate().trim()+" rating");
                    context.startActivity(intent);
                }
            });



    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,size,price,verification,date;
        ImageView imageView;
        Button cancel,review;
        public ViewHolder(View itemView) {
            super(itemView);
            cancel=(Button)itemView.findViewById(R.id.cancel);
            date=(TextView)itemView.findViewById(R.id.date);
            name=(TextView)itemView.findViewById(R.id.produt_name);
            size=(TextView)itemView.findViewById(R.id.size);
            verification=(TextView)itemView.findViewById(R.id.payment);
            price=(TextView)itemView.findViewById(R.id.price);
            review=(Button)itemView.findViewById(R.id.review);
            imageView=(ImageView)itemView.findViewById(R.id.imageview);


        }
    }
}
