package com.beacons.shopping.shoppingmall;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Product_review extends AppCompatActivity {
    Bundle rec;
    RatingBar bar;
    Button submit;
    String product_id,product_type,rating;
    DatabaseReference mref,mref2;
    EditText desc,head;
    FirebaseUser user;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review);
        setTitle("Write a Review");
        pd=new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        desc=(EditText)findViewById(R.id.description);
        head=(EditText)findViewById(R.id.heading);
        bar=(RatingBar)findViewById(R.id.ratingBar);
        submit=(Button)findViewById(R.id.submit);
        rec=getIntent().getExtras();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(rec!=null)
        {
            product_id=rec.getString("product_id");
            product_type=rec.getString("product_type");
            rating=rec.getString("rating");
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(desc.getText().toString().isEmpty()||desc.getText()==null)
                {
                    Toast.makeText(getApplicationContext(),"The Description can't be empty",Toast.LENGTH_SHORT).show();
                }
                else if(head.getText().toString().isEmpty()||head.getText()==null)
                {
                    Toast.makeText(getApplicationContext(),"The heading can't be empty",Toast.LENGTH_SHORT).show();

                }
                else if(!Network.isNetworkEnabled(getApplicationContext()))
                Network.show_alert(getApplicationContext());

                else{
                    pd.setMessage("Uploading Your Review");
                    pd.show();
               double rate=(Double.parseDouble(rating.split(" ")[0])*100+bar.getRating())/101;
                String rates=String.format("%.2f",rate);
                //String rates = rate.oString("0.##");
                mref2=FirebaseDatabase.getInstance().getReference(product_type+"/"+product_id+"/"+"rating");
                mref2.setValue(rates);
                mref= FirebaseDatabase.getInstance().getReference("Reviews/"+product_id+"/"+ user.getUid());
                String name=null;
                if(user.getDisplayName()==null)
                {
                    name="verified user";
                }
                else
                    name=user.getDisplayName();
                Review_model review=new Review_model(head.getText().toString(),desc.getText().toString(),name);
                mref.setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        finish();
                        Toast.makeText(getApplication(),"Review Submitted",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }});



    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }

        return true;

    }

}
