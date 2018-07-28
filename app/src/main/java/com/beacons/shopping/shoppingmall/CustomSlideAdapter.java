package com.beacons.shopping.shoppingmall;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adi on 7/8/2018.
 */

class CustomSlideAdapter extends PagerAdapter {
    private Context context;
    DatabaseReference mref;
    List<String> imagestop;
    private LayoutInflater layoutInflater;

    public CustomSlideAdapter(Context context,List<String>imagestop) {
        this.context=context;
        this.imagestop=imagestop;
    }

    public int getCount(){
        return imagestop.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view=layoutInflater.inflate(R.layout.slider,container,false);
        ImageView viewpage_image=(ImageView)item_view.findViewById(R.id.viewpage_image);

        Picasso.with(context).load(imagestop.get(position).toString()).fit().placeholder(R.drawable.ph).into(viewpage_image);

        container.addView(item_view);
        return item_view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(ImageView)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}

