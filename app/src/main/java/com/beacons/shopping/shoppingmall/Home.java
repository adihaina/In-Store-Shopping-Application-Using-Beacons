package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**

 */
public class Home extends Fragment {
    ViewPager viewpager;
    RecyclerView recyclerView;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 900;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    ScrollView sv;
    BrandAdaptor adaptor;
    MiddleAdaptor adaptor2;
    CustomSlideAdapter adapter;
    DatabaseReference mref;
    List<String> images,images_middle,images_top;
    RecyclerView recycle1;
    ProgressDialog pd;
    View v1,v2;
    TextView textwa;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_home,container,false);
        viewpager=(ViewPager)view.findViewById(R.id.viewpager);
        getActivity().setTitle("Home");
        mref= FirebaseDatabase.getInstance().getReference();
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle);
       recycle1=(RecyclerView)view.findViewById(R.id.recycle1);
       recycle1.setHasFixedSize(true);
       recycle1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        images=new ArrayList<>();
        images_middle=new ArrayList<>();
        images_top=new ArrayList<>();
        pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Loading");
        recyclerView.setHasFixedSize(true);
        v1=(View)view.findViewById(R.id.v1);
        v2=(View)view.findViewById(R.id.v2);
        textwa=(TextView)view.findViewById(R.id.textwa);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        if(!Network.isNetworkEnabled(getActivity()))
            Network.show_alert(getActivity());
        pd.show();

            mref.child("View").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    images_top.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        images_top.add(dataSnapshot1.getValue().toString());
                    }
                    adapter = new CustomSlideAdapter(getActivity(), images_top);
                    viewpager.setAdapter(adapter);
                    pd.dismiss();
                    textwa.setVisibility(View.VISIBLE);
                    v1.setVisibility(View.VISIBLE);
                    v2.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == adapter.getCount()) {
                                currentPage = 0;
                            }
                            viewpager.setCurrentItem(currentPage++, true);
                        }
                    };

                    timer = new Timer(); // This will create a new Thread
                    timer.schedule(new TimerTask() { // task to be scheduled

                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, DELAY_MS, PERIOD_MS);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });

            mref.child("Brands").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    images.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        images.add(dataSnapshot1.getValue().toString());
                        adaptor = new BrandAdaptor(getActivity(), images);
                        recyclerView.setAdapter(adaptor);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });

            mref.child("Middle").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    images_middle.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        images_middle.add(dataSnapshot1.getValue().toString());

                    }
                    adaptor2 = new MiddleAdaptor(getActivity(), images_middle);
                    recycle1.setAdapter(adaptor2);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });



        return view;
    }

}