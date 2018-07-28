package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Product_Activity extends AppCompatActivity {

    DatabaseReference mdatabaseref,specificationref,cart_ref,review_ref;
    FirebaseAuth mAuth;
    Bundle rec;
    String productid,Name,Brand,Price,color,Rating,Imageurl,Offers,Imageurl2,Imageurl3,product_type;
    TextView product_desc,price_textview,name_textview,produt_details,color_textiew,brand_textview,rating_textview,offers_textview,recycle1;
    ImageView image,img1,img2,img3;
    String size;
    Spinner size_textview;
    RecyclerView recycle;
    List<Review_model> reviews;
    Review_Adaptor review_adaptor;
    //ArrayAdapter<String> dataAdapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_);
        mAuth=FirebaseAuth.getInstance();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        netcheck();
        pd=new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Loading");
        pd.show();

        recycle1=(TextView)findViewById(R.id.recycle1);
        rating_textview=(TextView)findViewById(R.id.rating_textview);
        offers_textview=(TextView)findViewById(R.id.offers_textview);
        product_desc=(TextView)findViewById(R.id.product_desc);
        produt_details=(TextView)findViewById(R.id.product_detils);
        price_textview=(TextView)findViewById(R.id.price_textview);
        name_textview=(TextView)findViewById(R.id.name_textview);
        color_textiew=(TextView)findViewById(R.id.color_textview);
        size_textview=(Spinner) findViewById(R.id.sizes_textview);
        brand_textview=(TextView)findViewById(R.id.brand_textview);
        setTitle(productid);
        image=(ImageView)findViewById(R.id.image);
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        recycle=(RecyclerView)findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        reviews=new ArrayList<>();
        recycle.setLayoutManager(new LinearLayoutManager(this));
        rec=getIntent().getExtras();
        if(rec!=null)
        {  productid=rec.getString("productid");
            Name=rec.getString("Name");
        Rating=rec.getString("Rating");
        Brand=rec.getString("Brand");
        Price=rec.getString("Price");
        color=rec.getString("Color");
        Imageurl=rec.getString("Imageurl");
        Offers=rec.getString("Offers");
        product_type=rec.getString("product_type");}
        setTitle(Name);



        review_ref=FirebaseDatabase.getInstance().getReference("Reviews/"+productid);
        mdatabaseref=FirebaseDatabase.getInstance().getReference("Additional_Images/"+productid);
        specificationref= FirebaseDatabase.getInstance().getReference("Specifications/"+productid);
        Picasso.with(this).load(Imageurl.toString()).fit().placeholder(R.drawable.ph).centerCrop().into(image);
        Picasso.with(this).load(Imageurl.toString()).fit().placeholder(R.drawable.ph).centerCrop().into(img1);

        name_textview.setText(Name);
        price_textview.setText("₹"+Price);
        brand_textview.setText(Brand+"'s Original");
        offers_textview.setText(Offers);
        rating_textview.setText(Rating+" Ratings");


        specificationref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produt_details.setText("");
                if(product_type.equals("MEN_SHIRT")||product_type.equals("MEN_TROUSERS")) {
                    Specification spec = dataSnapshot.getValue(Specification.class);
                    product_desc.setText("Closure\nFabric\nOccasion\nPockets\nFit");
                    produt_details.append(spec.getClosure() + "\n");
                    produt_details.append(spec.getFabric() + "\n");
                    produt_details.append(spec.getOccasion() + "\n");
                    produt_details.append(spec.getPockets() + "\n");
                    produt_details.append(spec.getFit());
                    color_textiew.setText(color);
                    size=spec.getSize();
                    List<String> spinnerArray = Arrays.asList(("Select-Size "+spec.getSize()).split(" "));
                    ArrayAdapter<String>dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size_textview.setAdapter(dataAdapter);

                }
                else if(product_type.equals("MEN_FOOTWEAR")||product_type.equals("WOMEN_FOOTWEAR")) {
                    Specification_footwear spec = dataSnapshot.getValue(Specification_footwear.class);
                    product_desc.setText("Closure\nMaerial\nOccasion\nShoe Type\nTip Shape");
                    produt_details.append(spec.getClosure() + "\n");
                    produt_details.append(spec.getMaterial() + "\n");
                    produt_details.append(spec.getOccasion() + "\n");
                    produt_details.append(spec.getShoe_type() + "\n");
                    produt_details.append(spec.getTip_shape());
                    color_textiew.setText(color);
                    size=spec.getSize();
                    List<String> spinnerArray = Arrays.asList(("Select-Size "+spec.getSize()).split(" "));
                    ArrayAdapter<String>dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size_textview.setAdapter(dataAdapter);


                }
                else if(product_type.equals("WOMEN_SUITS"))
                {
                    Specification_suit spec = dataSnapshot.getValue(Specification_suit.class);
                    product_desc.setText("Sleeves\nFabric\nOccasion\nDupatta\nSalwar Type");

                    produt_details.append(spec.getSleeves() + "\n");
                    produt_details.append(spec.getFabric() + "\n");
                    produt_details.append(spec.getOccasion() + "\n");
                    produt_details.append(spec.getDupatta() + "\n");
                    produt_details.append(spec.getSalwar_type());
                    color_textiew.setText(color);
                    size=spec.getSize();

                    List<String> spinnerArray = Arrays.asList(("Select-Size "+spec.getSize()).split(" "));
                    ArrayAdapter<String>dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size_textview.setAdapter(dataAdapter);


                }
                else if(product_type.equals("WOMEN_TOP")) {
                    Specification_top spec = dataSnapshot.getValue(Specification_top.class);
                    product_desc.setText("Sleeves\nFabric\nOccasion\nNeck Type\nFit");

                    produt_details.append(spec.getSleeves() + "\n");
                    produt_details.append(spec.getFabric() + "\n");
                    produt_details.append(spec.getOccasion() + "\n");
                    produt_details.append(spec.getNeck() + "\n");
                    produt_details.append(spec.getFit());
                    color_textiew.setText(color);
                    size=spec.getSize();


                    List<String> spinnerArray = Arrays.asList(("Select-Size "+spec.getSize()).split(" "));
                    ArrayAdapter<String>dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size_textview.setAdapter(dataAdapter);



                }
                pd.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UploadImages uploadImages=dataSnapshot.getValue(UploadImages.class);
                Imageurl2=uploadImages.getImage1();
                Imageurl3=uploadImages.getImage2();
                Picasso.with(getApplicationContext()).load(Imageurl2).fit().placeholder(R.drawable.ph).centerCrop().into(img2);
                Picasso.with(getApplicationContext()).load(Imageurl3).fit().placeholder(R.drawable.ph).centerCrop().into(img3);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(getApplicationContext()).load(Imageurl.toString()).placeholder(R.drawable.ph).fit().centerCrop().into(image);
                img1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_rect1));
                img2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_rect));
                img3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_rect));




            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(getApplicationContext()).load(Imageurl2.toString()).placeholder(R.drawable.ph).fit().centerCrop().into(image);
                img2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_rect1));
                img1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_rect));
                img3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_rect));


            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(getApplicationContext()).load(Imageurl3.toString()).placeholder(R.drawable.ph).fit().centerCrop().into(image);
                img3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_rect1));
                img2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_rect));
                img1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_rect));
            }
        });
        review_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reviews.clear();
                if(dataSnapshot.getChildrenCount()==0)
                {
                    recycle.setVisibility(View.GONE);
                    recycle1.setText("No Reviews Yet");
               //     recycle1.setTextColor(Color.parseColor("#000"));

                }
                else{
                    recycle1.setText("Reviews");
             //       recycle1.setTextColor(Color.parseColor("#000"));
                    recycle.setVisibility(View.VISIBLE);

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Review_model rm=ds.getValue(Review_model.class);
                    reviews.add(rm);

                    review_adaptor=new Review_Adaptor(reviews,getApplicationContext());
                    recycle.setAdapter(review_adaptor);

                }

            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    public void add_to_cart(View view)
    {
        if(!Network.isNetworkEnabled(this))
            Network.show_alert(this);
        else{
            if(mAuth.getCurrentUser()==null)
        {
            Toast.makeText(this,"You need to login first",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, Sign_In.class);
            intent.putExtra("check","product");
            startActivity(intent);
        }
        else
            {
                pd.setMessage("Adding to Cart");
                pd.show();
                cart_ref=FirebaseDatabase.getInstance().getReference("Cart/"+mAuth.getCurrentUser().getUid());
            Cart_model cart_model=new Cart_model(product_type,size,"in");
            cart_ref.child(productid).setValue(cart_model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Successfully Added To Cart",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }}}


    public void buy_now(View view)
    {

        if(size_textview.getSelectedItem().equals("Select-Size")||size_textview.getSelectedItem().equals("Select-Size "))
        {
            size_textview.requestFocus();
            Toast.makeText(getApplicationContext(),"Please Select a Size First",Toast.LENGTH_SHORT).show();
        }
        else {
            if(!Network.isNetworkEnabled(this))
                Network.show_alert(this);
            else{
                if(mAuth.getCurrentUser()==null)
            {
                Toast.makeText(this,"You need to login first",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this, Sign_In.class);
                intent.putExtra("check","product");
                startActivity(intent);
            }
            else
                {
                    pd.setMessage("Processing");
                    pd.show();
            DatabaseReference mdataref2 = FirebaseDatabase.getInstance().getReference("Orders/" + FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
            order_model om=new order_model(Imageurl,name_textview.getText().toString(),product_type.toString(),Price,size_textview.getSelectedItem().toString(),productid,"not-paid",Rating);

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            mdataref2.child(formatter.format(date)).setValue(om).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Successfully Ordered\nPlease pay at th counter\nAnd recieve your Order",Toast.LENGTH_SHORT).show();
                    }
            });

        }
    }}}

    public void write_a_review(View view)
    {
        if(mAuth.getCurrentUser()==null)
    {
        Toast.makeText(this,"You need to login first",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, Sign_In.class);
        intent.putExtra("check","product");
        startActivity(intent);
    }
        else{
        Intent intent=new Intent(this,Product_review.class);
        intent.putExtra("product_id",productid);
        intent.putExtra("product_type",product_type);
        intent.putExtra("rating",rating_textview.getText());
        startActivity(intent);
    }}


    public void netcheck(){
        if(!Network.isNetworkEnabled(getApplicationContext()))
            Network.show_alert(getApplicationContext());
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
}
