package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    DatabaseReference mref,mref1;
    int i;
    ProgressDialog pd;
    FirebaseDatabase mdata;
    RecyclerView recycle;
    CartAdaptor adaptor;
    RelativeLayout view;
    List<String> product_types,product_ids,sizes_of_produt;
    List<Upload> uploadlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("My Cart");
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        view=(RelativeLayout)findViewById(R.id.view);
        recycle = (RecyclerView) findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        mdata = FirebaseDatabase.getInstance();
        uploadlist = new ArrayList<>();
        product_ids = new ArrayList<>();
        product_types = new ArrayList<>();
        sizes_of_produt = new ArrayList<>();

        if(!Network.isNetworkEnabled(this)) {
            Network.show_alert(this);
            view.setVisibility(View.VISIBLE);
            recycle.setVisibility(View.GONE);
        }

        else if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this,"You need to login first",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, Sign_In.class);
            intent.putExtra("check","cart");
            finish();
            startActivity(intent);
        } else {
            mref = mdata.getReference("Cart/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
            pd.setMessage("Loading");
            pd.show();
            mref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()==0) {
                        pd.dismiss();
                        view.setVisibility(View.VISIBLE);
                        recycle.setVisibility(View.GONE);
                    }
                    else{
                        view.setVisibility(View.GONE);
                        recycle.setVisibility(View.VISIBLE);

                    product_types.clear();
                    sizes_of_produt.clear();
                    product_ids.clear();
                    uploadlist.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Cart_model cart_model = dataSnapshot1.getValue(Cart_model.class);
                        final String product_type = cart_model.getProduct_type();
                        product_types.add(product_type);
                        product_ids.add(dataSnapshot1.getKey());
                        sizes_of_produt.add(cart_model.getSizes());
                    }

                    for (i = 0; i < product_types.size(); i++) {
                        mref1 = mdata.getReference(product_types.get(i) + "/" + product_ids.get(i));
                        mref1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshots) {
                                Upload upload = dataSnapshots.getValue(Upload.class);
                                uploadlist.add(upload);
                                pd.dismiss();
                                // Toast.makeText(getApplicationContext(), dataSnapshots.getChildrenCount() + "", Toast.LENGTH_SHORT).show();
                                adaptor = new CartAdaptor(uploadlist, product_ids, getApplicationContext(), product_types, sizes_of_produt);
                                recycle.setAdapter(adaptor);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                view.setVisibility(View.VISIBLE);
                                recycle.setVisibility(View.GONE);
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }}}




                @Override
                public void onCancelled(DatabaseError databaseError) {
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
