package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Shirt extends Fragment {
    private DatabaseReference mDatabaseRef;
    private RecyclerView recycle;
    private ImageAdapter adaptor;
    public List<Upload> shirts;
    public String producttype;
    public List<String> productIds;
    public Button filter;
    public Spinner sort;
    public String b1,b2,maxprice,minprice,minrate;
    TextView text;
    ImageView cart;
    List<Upload> filteredlist;
    List<String> filteredids;
    ProgressDialog pd;
    String choosen_brand;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =inflater.inflate(R.layout.fragment_shirt,container,false);
        Bundle bundle = this.getArguments();
        producttype = bundle.getString("message");
        choosen_brand=bundle.getString("brand");
        filter=(Button) view.findViewById(R.id.filter);
        pd=new ProgressDialog(getActivity());
        pd.setMessage("Loading");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        b1=" ";
        b2=" ";
        minrate="";
        maxprice="";
        minprice="";
        filteredlist=new ArrayList<>();
        text=(TextView)view.findViewById(R.id.text11);
        cart=(ImageView)view.findViewById(R.id.cart);
        mDatabaseRef=FirebaseDatabase.getInstance().getReference(producttype);
        recycle=(RecyclerView)view.findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        recycle.setItemViewCacheSize(20);
        recycle.setDrawingCacheEnabled(true);
        recycle.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        filteredids=new ArrayList<>();
        recycle.setLayoutManager(new GridLayoutManager(getContext(),2));
        shirts=new ArrayList<>();
        productIds=new ArrayList<>();
        sort=(Spinner) view.findViewById(R.id.sort);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sort.getSelectedItem().equals("Rating-Low-To-High")){
                    sort_by_rate(true);
                }
                else if(sort.getSelectedItem().equals("Rating-High-T0-Low")){
                    sort_by_rate(false);
                }
                else if(sort.getSelectedItem().equals("Price-High-T0-Low")){sort_by_price(false);}

                else if(sort.getSelectedItem().equals("Price-Low-To-High")){sort_by_price(true);}

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (!Network.isNetworkEnabled(getActivity()))
        {
            Network.show_alert(getActivity());
        }


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shirts.clear();
                productIds.clear();
               // Toast.makeText(getActivity(),dataSnapshot.getChildrenCount()+"",Toast.LENGTH_SHORT).show();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                String productid=dataSnapshot1.getKey();
                Upload shirt=dataSnapshot1.getValue(Upload.class);
                shirts.add(shirt);
                productIds.add(productid);
                }
                pd.dismiss();
                adaptor=new ImageAdapter(shirts,productIds,getContext(),producttype);
                recycle.setAdapter(adaptor);

                if(!choosen_brand.equals("no"))
                {
                    if(choosen_brand.equals("John Player")||choosen_brand.equals("Blackberry")||choosen_brand.equals("Puma")||choosen_brand.equals("Lebas")||choosen_brand.equals("Fashion Plannet")||choosen_brand.equals("Red tape"))
                    {
                        b1=choosen_brand.replace(" ","_");
                    }
                    else
                    {
                        b2=choosen_brand.replace(" ","_");
                    }
                    for(int i=0;i<shirts.size();i++){
                        if (shirts.get(i).getBrand().equals(choosen_brand.replace(" ","_")))
                            filteredlist.add(shirts.get(i));
                        filteredids.add(productIds.get(i));
                    }
                    adaptor.filterlist(filteredlist,filteredids);
                    choosen_brand="no";

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    private void check() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog, null);


        final Button apply_filter=(Button)mView.findViewById(R.id.apply_filter_button);
        final Button cancel=(Button)mView.findViewById(R.id.cancel_button);
        final CheckBox brand1=(CheckBox)mView.findViewById(R.id.brand1);
        final CheckBox brand2=(CheckBox)mView.findViewById(R.id.brand2);
        if(producttype.equals("MEN_SHIRT")) {
            brand1.setText("John Player");
            brand2.setText("Peter England");
        }
        else if(producttype.equals("MEN_TROUSERS")) {
            brand1.setText("Blackberry");
            brand2.setText("Van Huesen");
        }
        else if(producttype.equals("MEN_FOOTWEAR")) {
            brand1.setText("Puma");
            brand2.setText("Red Chief");
        }
        else if(producttype.equals("WOMEN_SUITS")) {
            brand1.setText("Lebas");
            brand2.setText("Beba");
        }
        else if(producttype.equals("WOMEN_TOP")) {
            brand1.setText("Fashion Plannet");
            brand2.setText("Harpa");
        }
        else if(producttype.equals("WOMEN_FOOTWEAR")) {
            brand1.setText("Red Tape");
            brand2.setText("CatWalk");
        }
        final EditText min=(EditText)mView.findViewById(R.id.min);
      final EditText max=(EditText)mView.findViewById(R.id.max);
      final Spinner rating_spinner=(Spinner)mView.findViewById(R.id.rating_spinner);


      if(!b1.equals(" "))
          brand1.setChecked(true);
      else brand1.setChecked(false);

        if(!b2.equals(" "))
            brand2.setChecked(true);
        else brand2.setChecked(false);

        if(minprice.equals("")||minprice=="0"){
            min.setText("");
            min.setHint("Min Price");
        }
        else min.setText(minprice);
        if(maxprice.equals("")||maxprice=="1000000"){
            max.setText("");
            max.setHint("Max Price");
        }
        else max.setText(maxprice);

        mBuilder.setView(mView).setTitle("  Filter Your Search");
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(brand1.isChecked())
                {
                    b1=brand1.getText().toString().replace(" ","_");

                }
                else b1=" ";

                if(brand2.isChecked())
                {
                   b2=brand2.getText().toString().replace(" ","_");
                }
                else b2=" ";
                maxprice=max.getText().toString();
                minprice=min.getText().toString();
                minrate=rating_spinner.getSelectedItem().toString();

                    filteredids.clear();
                    filteredlist.clear();
                for(int i=0;i<productIds.size();i++)
                {
                    Upload shirt=shirts.get(i);
                    if(maxprice.equals("")||maxprice==null)
                    {
                        maxprice="1000000";
                    }
                    if(minprice.equals("")||maxprice==null)
                    {
                        minprice="0";
                    }
                   // if((shirt.getBrand().contains(b1)||shirt.getBrand().contains(b2))&&(Integer.parseInt(maxprice)>=Integer.parseInt(shirt.getPrice()))&&(Integer.parseInt(minprice)<=Integer.parseInt(shirt.getPrice()))&&(Integer.parseInt(shirt.getRating())>=Integer.parseInt(minrate)))
                    if(shirt.getBrand().equals(b1)||shirt.getBrand().equals(b2)||b1.equals(b2))
                    {
                        if(Double.parseDouble(shirt.getRating())>=Integer.parseInt(minrate)) {
                            if(Double.parseDouble(maxprice)>=Integer.parseInt(shirt.getPrice())&&Double.parseDouble(minprice)<=Integer.parseInt(shirt.getPrice()))

                            {
                                filteredids.add(productIds.get(i));
                                filteredlist.add(shirt);
                        }}
                    }

                }
                if(filteredids.size()==0)
                {
                    recycle.setVisibility(View.GONE);
                    cart.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
                    adaptor.filterlist(filteredlist,filteredids);


                }
                else{
                    adaptor.filterlist(filteredlist,filteredids);
                    recycle.setVisibility(View.VISIBLE);
                    cart.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);

                }


            }
        });
    }


    public void sort_by_price(Boolean rev)
    {
        if(filteredids.size()==0)
        {
            sort(shirts,productIds,rev);
        }
        else{
            sort(filteredlist,filteredids,rev);
        }
    }

    public void sort(List<Upload>shirts,List<String>productids,Boolean rev)
    {
        for (int i = 0; i < (shirts.size()-1); i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < shirts.size(); j++) {
                if(rev){
                if (Integer.parseInt(shirts.get(j).getPrice()) <= Integer.parseInt(shirts.get(min_idx).getPrice()))
                { min_idx = j;}
            }
            else
            {
                if (Integer.parseInt(shirts.get(j).getPrice()) >= Integer.parseInt(shirts.get(min_idx).getPrice()))
                { min_idx = j;}
            }}
            // Swap the found minimum element with the first
            // element
            Upload temp = shirts.get(min_idx);
            shirts.set(min_idx,shirts.get(i));
            shirts.set(i,temp);

            String temp1 = productIds.get(min_idx);
            productIds.set(min_idx,productIds.get(i));
            productIds.set(i,temp1);
        }
        adaptor.filterlist(shirts,productIds);

    }

    public void sort_by_rate(Boolean rev)
    {
        if(filteredids.size()==0)
        {
            sortrate(shirts,productIds,rev);
        }
        else{
            sortrate(filteredlist,filteredids,rev);
        }
    }

    public void sortrate(List<Upload>shirts,List<String>productids,Boolean rev)
    {
        for (int i = 0; i < (shirts.size()-1); i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < shirts.size(); j++) {
                if(rev){
                    if (Double.parseDouble(shirts.get(j).getRating()) <= Double.parseDouble(shirts.get(min_idx).getRating()))
                    { min_idx = j;}
                }
                else
                {
                    if (Double.parseDouble(shirts.get(j).getRating()) >= Double.parseDouble(shirts.get(min_idx).getRating()))
                    { min_idx = j;}
                }}
            // Swap the found minimum element with the first
            // element
            Upload temp = shirts.get(min_idx);
            shirts.set(min_idx,shirts.get(i));
            shirts.set(i,temp);

            String temp1 = productIds.get(min_idx);
            productIds.set(min_idx,productIds.get(i));
            productIds.set(i,temp1);
        }
        adaptor.filterlist(shirts,productIds);

    }




}