package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Users extends AppCompatActivity {

    List<User_Details> users;
    List<String> userids;
    RecyclerView recycler;
    DatabaseReference mref;
    UserAdapter adapter;
    ProgressDialog pd;
    RelativeLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        users=new ArrayList<>();
        userids=new ArrayList<>();
        recycler=(RecyclerView)findViewById(R.id.recycle);
        recycler.setHasFixedSize(true);
        mref=FirebaseDatabase.getInstance().getReference("Users");
        pd=new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCanceledOnTouchOutside(false);
        view=(RelativeLayout)findViewById(R.id.view);

        if(Network.isNetworkEnabled(this))
        {
            Network.show_alert(this);
            view.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        }
        else{
            pd.show();
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    view.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                    pd.dismiss();
                }
                   else{
                    view.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                    userids.clear();
                    users.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    User_Details ud = dataSnapshot1.getValue(User_Details.class);
                    String uid = dataSnapshot1.getKey().toString();
                    userids.add(uid);
                    users.add(ud);
                    adapter = new UserAdapter(getApplicationContext(), users, userids);
                    recycler.setAdapter(adapter);
                }    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }}
}
