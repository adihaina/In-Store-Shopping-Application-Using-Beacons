package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderAdaptor adaptor;
    DatabaseReference mdataref;
    FirebaseUser user;
    List<order_model> orderlist;
    List<String>date_times;
    RelativeLayout view;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        setTitle("My Orders");
        pd=new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Loading");

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        view=(RelativeLayout)findViewById(R.id.view);
        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderlist=new ArrayList<>();
        date_times=new ArrayList<>();

        user=FirebaseAuth.getInstance().getCurrentUser();
        if(!Network.isNetworkEnabled(this)){
            view.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Network.show_alert(this);
        }
        else if(user==null)
        {
            Toast.makeText(this,"You need to login first",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, Sign_In.class);
            intent.putExtra("check","orders");
            finish();
            startActivity(intent);
        }
        else {
            pd.show();
            mdataref = FirebaseDatabase.getInstance().getReference("Orders/" + user.getUid().toString());
            mdataref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    orderlist.clear();
                    date_times.clear();
                    if (dataSnapshot.getChildrenCount() == 0) {
                        pd.dismiss();
                        view.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    } else {
                        view.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            order_model om = dataSnapshot1.getValue(order_model.class);
                            orderlist.add(om);
                            date_times.add(dataSnapshot1.getKey());
                            adaptor = new OrderAdaptor(getApplicationContext(), orderlist, date_times);
                            recyclerView.setAdapter(adaptor);
                            pd.dismiss();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    view.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }

        return true;

    }
}
