package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class Uploadsection extends AppCompatActivity {
    Uri mainImage,image1,image2;
    private StorageReference mstorageref;
    private DatabaseReference mDatabaseref;
    ImageView img,img1,img2;
    EditText name,rating,color,price,sizes;
    LinearLayout completesection;
    Spinner brand,offers,fabric,occasion,pockets,fit,closure,type,sleeves,material,dupatta,neck,shoe_type,salwar,tip;
    ProgressDialog pd;


    String image1url,image2url;
    TextView closure_textview,fabric_textview,pockets_textview,fit_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadsection);
        sleeves=(Spinner)findViewById(R.id.sleeves);
        setTitle("Upload Secton");
        material=(Spinner)findViewById(R.id.material);
        dupatta=(Spinner)findViewById(R.id.dupatta);
        neck=(Spinner)findViewById(R.id.neck);
        shoe_type=(Spinner)findViewById(R.id.shoe_type);
        salwar=(Spinner)findViewById(R.id.salwar);
        tip=(Spinner)findViewById(R.id.tip);
        completesection=(LinearLayout)findViewById(R.id.complete_section);
        closure_textview=(TextView)findViewById(R.id.closure_textview);
        fabric_textview=(TextView)findViewById(R.id.fabric_textview);
        pockets_textview=(TextView)findViewById(R.id.pocket_textview);
        fit_textview=(TextView)findViewById(R.id.fit_textview);
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img=(ImageView)findViewById(R.id.img);
        type=(Spinner) findViewById(R.id.type);
        name=(EditText)findViewById(R.id.name);
        rating=(EditText)findViewById(R.id.rating);
        closure=(Spinner) findViewById(R.id.closure);
        color=(EditText)findViewById(R.id.color);
        brand=(Spinner) findViewById(R.id.brand);
        price=(EditText)findViewById(R.id.price);
        offers=(Spinner) findViewById(R.id.offers);
        sizes=(EditText)findViewById(R.id.sizes);
        fabric=(Spinner) findViewById(R.id.fabric);
        occasion=(Spinner) findViewById(R.id.occasion);
        pockets=(Spinner) findViewById(R.id.pockets);
        fit=(Spinner) findViewById(R.id.fit);
        mstorageref= FirebaseStorage.getInstance().getReference();
        mDatabaseref= FirebaseDatabase.getInstance().getReference();
        pd=new ProgressDialog(this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(type.getSelectedItem().equals("Choose a product_type"))
                {
                    completesection.setVisibility(View.GONE);
                }
                else{
                    completesection.setVisibility(View.VISIBLE);
                    if(type.getSelectedItem().equals("Men_Shirt")||type.getSelectedItem().equals("Men_Trousers"))
                {
                    closure_textview.setText("Closure");
                    fabric_textview.setText("Fabric");
                    fit_textview.setText("Fit");
                    pockets_textview.setText("Pockets");

                    closure.setVisibility(View.VISIBLE);
                    fabric.setVisibility(View.VISIBLE);
                    pockets.setVisibility(View.VISIBLE);
                    fit.setVisibility(View.VISIBLE);
                    sleeves.setVisibility(View.GONE);
                    material.setVisibility(View.GONE);
                    dupatta.setVisibility(View.GONE);
                    neck.setVisibility(View.GONE);
                    shoe_type.setVisibility(View.GONE);
                    salwar.setVisibility(View.GONE);
                    tip.setVisibility(View.GONE);
                }
                else if(type.getSelectedItem().equals("Men_Footwear")||type.getSelectedItem().equals("Women_Footwear"))
                {
                    closure_textview.setText("Closure");
                    fabric_textview.setText("Material");
                    fit_textview.setText("Tip Shape");
                    pockets_textview.setText("Shoe Type");

                    closure.setVisibility(View.VISIBLE);
                    fabric.setVisibility(View.GONE);
                    pockets.setVisibility(View.GONE);
                    fit.setVisibility(View.GONE);
                    sleeves.setVisibility(View.GONE);
                    material.setVisibility(View.VISIBLE);
                    dupatta.setVisibility(View.GONE);
                    neck.setVisibility(View.GONE);
                    shoe_type.setVisibility(View.VISIBLE);
                    salwar.setVisibility(View.GONE);
                    tip.setVisibility(View.VISIBLE);

                }
                else if(type.getSelectedItem().equals("Women_Suits"))
                {
                    closure_textview.setText("Sleeves");
                    fabric_textview.setText("Fbaric");
                    pockets_textview.setText("Dupatta");
                    fit_textview.setText("Salwar Type");

                    closure.setVisibility(View.GONE);
                    fabric.setVisibility(View.VISIBLE);
                    pockets.setVisibility(View.GONE);
                    fit.setVisibility(View.GONE);
                    sleeves.setVisibility(View.VISIBLE);
                    material.setVisibility(View.GONE);
                    dupatta.setVisibility(View.VISIBLE);
                    neck.setVisibility(View.GONE);
                    shoe_type.setVisibility(View.GONE);
                    salwar.setVisibility(View.VISIBLE);
                    tip.setVisibility(View.GONE);

                }
                else if(type.getSelectedItem().equals("Women_Top"))
                {
                    closure_textview.setText("Sleeves");
                    fabric_textview.setText("Fabric");
                    pockets_textview.setText("Neck Type");
                    fit_textview.setText("Fit");

                    closure.setVisibility(View.GONE);
                    fabric.setVisibility(View.VISIBLE);
                    pockets.setVisibility(View.GONE);
                    fit.setVisibility(View.VISIBLE);
                    sleeves.setVisibility(View.VISIBLE);
                    material.setVisibility(View.GONE);
                    dupatta.setVisibility(View.GONE);
                    neck.setVisibility(View.VISIBLE);
                    shoe_type.setVisibility(View.GONE);
                    salwar.setVisibility(View.GONE);
                    tip.setVisibility(View.GONE);

                }}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void upload(View view){
        pd.setMessage("Uploading");
        pd.show();
        if(!Network.isNetworkEnabled(this)) {
            pd.dismiss();
            Network.show_alert(this);
        }

       else if(mainImage!=null && image1!=null && image2!=null)
        {

            StorageReference fileRef1=mstorageref.child(brand.getSelectedItem().toString().trim().toUpperCase()+"/"+System.currentTimeMillis()+"."+getfileExtension(image1));
            fileRef1.putFile(image1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    image1url=taskSnapshot.getDownloadUrl().toString();
                }
            });
            StorageReference fileRef2=mstorageref.child(brand.getSelectedItem().toString().trim().toUpperCase()+"/"+System.currentTimeMillis()+"."+getfileExtension(image2));
            fileRef2.putFile(image2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    image2url=taskSnapshot.getDownloadUrl().toString();
                }
            });


            StorageReference fileRef=mstorageref.child(brand.getSelectedItem().toString().trim().toUpperCase()+"/"+System.currentTimeMillis()+"."+getfileExtension(mainImage));
            fileRef.putFile(mainImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Upload upload=new Upload(name.getText().toString(), brand.getSelectedItem().toString(), price.getText().toString(), color.getText().toString(),taskSnapshot.getDownloadUrl().toString(), offers.getSelectedItem().toString(), rating.getText().toString());

                    if(type.getSelectedItem().equals("Men_Shirt")||type.getSelectedItem().equals("Men_Trousers"))
                    {
                        Toast.makeText(getApplicationContext(),type.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        Specification specifications=new Specification(sizes.getText().toString(), fabric.getSelectedItem().toString(), occasion.getSelectedItem().toString(), closure.getSelectedItem().toString(), pockets.getSelectedItem().toString(), fit.getSelectedItem().toString());
                        UploadImages uploadimages=new UploadImages(image1url,image2url);
                        String product_id=System.currentTimeMillis()+brand.getSelectedItem().toString();
                        mDatabaseref.child(type.getSelectedItem().toString().toUpperCase()).child(product_id).setValue(upload);
                        mDatabaseref.child("Additional_Images").child(product_id).setValue(uploadimages);
                        mDatabaseref.child("Specifications").child(product_id).setValue(specifications);
                    }
                    else if(type.getSelectedItem().equals("Men_Footwear")||type.getSelectedItem().equals("Women_Footwear"))
                    {
                        Specification_footwear specifications=new Specification_footwear(sizes.getText().toString(), material.getSelectedItem().toString(), occasion.getSelectedItem().toString(), closure.getSelectedItem().toString(), shoe_type.getSelectedItem().toString(), tip.getSelectedItem().toString());
                        UploadImages uploadimages=new UploadImages(image1url,image2url);
                        String product_id=System.currentTimeMillis()+brand.getSelectedItem().toString();
                        mDatabaseref.child(type.getSelectedItem().toString().toUpperCase()).child(product_id).setValue(upload);
                        mDatabaseref.child("Additional_Images").child(product_id).setValue(uploadimages);
                        mDatabaseref.child("Specifications").child(product_id).setValue(specifications);

                    }
                    else if(type.getSelectedItem().equals("Women_Suits"))
                    {

                        Specification_suit specifications=new Specification_suit(sizes.getText().toString(), fabric.getSelectedItem().toString(), occasion.getSelectedItem().toString(), sleeves.getSelectedItem().toString(), dupatta.getSelectedItem().toString(), salwar.getSelectedItem().toString());
                        UploadImages uploadimages=new UploadImages(image1url,image2url);
                        String product_id=System.currentTimeMillis()+brand.getSelectedItem().toString();
                        mDatabaseref.child(type.getSelectedItem().toString().toUpperCase()).child(product_id).setValue(upload);
                        mDatabaseref.child("Additional_Images").child(product_id).setValue(uploadimages);
                        mDatabaseref.child("Specifications").child(product_id).setValue(specifications);
                    }
                    else if(type.getSelectedItem().equals("Women_Top"))
                    {

                        Specification_top specifications=new Specification_top(sizes.getText().toString(), fabric.getSelectedItem().toString(), occasion.getSelectedItem().toString(), sleeves.getSelectedItem().toString(), neck.getSelectedItem().toString(), fit.getSelectedItem().toString());
                        UploadImages uploadimages=new UploadImages(image1url,image2url);
                        String product_id=System.currentTimeMillis()+brand.getSelectedItem().toString();
                        mDatabaseref.child(type.getSelectedItem().toString().toUpperCase()).child(product_id).setValue(upload);
                        mDatabaseref.child("Additional_Images").child(product_id).setValue(uploadimages);
                        mDatabaseref.child("Specifications").child(product_id).setValue(specifications);
                    }



                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();


                }
            });
        }
        else{
            pd.dismiss();
            Toast.makeText(this,"Upload Images of product",Toast.LENGTH_SHORT).show();
        }

    }

    private String getfileExtension(Uri uri) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mp=MimeTypeMap.getSingleton();
        return mp.getExtensionFromMimeType(cr.getType(uri));
    }


    public void product_image(View view){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }
    public void additional_image1(View view){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,2);

    }
    public void additional_image2(View view){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,3);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if((requestCode==1) && (resultCode==RESULT_OK) && (data!=null )&& (data.getData()!=null))
        {
            mainImage=data.getData();
            Picasso.with(this).load(mainImage).into(img);

        }
        else if((requestCode==2) && (resultCode==RESULT_OK) && (data!=null )&& (data.getData()!=null))
        {
            image1=data.getData();
           // img.setImageURI(image1);
            Picasso.with(this).load(image1).into(img1);
        }
        else if((requestCode==3) && (resultCode==RESULT_OK) && (data!=null )&& (data.getData()!=null))
        {
            image2=data.getData();
            Picasso.with(this).load(image2).into(img2);

        }
        super.onActivityResult(requestCode, resultCode, data);


    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }

        return true;

    }
}
