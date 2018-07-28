package com.beacons.shopping.shoppingmall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by adi on 7/11/2018.
 */

class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    List<String >uids;
    List<User_Details> user_details;
    Context mcontext;

    public UserAdapter(Context mcontext,List<User_Details> user_details,List<String> uids) {
        this.uids = uids;
        this.user_details = user_details;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card,parent,false);
        return new UserAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return uids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
