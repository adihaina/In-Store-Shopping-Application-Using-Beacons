package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity{
    FirebaseAuth mAuth;
    ProgressDialog pd;
    private GoogleSignInClient mGoogleSignInClient;
    EditText fullname,mobile,address;
    TextView email,orders,loyalty;
    RadioGroup gender;
    FirebaseUser currentuser;
    DatabaseReference mdatabaseref;
    Button save_details;
    RadioButton selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        pd=new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);


        mAuth=FirebaseAuth.getInstance();
        fullname=(EditText)findViewById(R.id.full_name);
        fullname.addTextChangedListener(new MyTextWatcher(fullname));
        mobile=(EditText)findViewById(R.id.mobile);
        mobile.addTextChangedListener(new MyTextWatcher(mobile));
        address=(EditText)findViewById(R.id.address);
        address.addTextChangedListener(new MyTextWatcher(address));
        email=(TextView)findViewById(R.id.email);
        orders=(TextView)findViewById(R.id.orders);
        loyalty=(TextView)findViewById(R.id.loyalty);
        gender=(RadioGroup)findViewById(R.id.gender);
        currentuser=mAuth.getCurrentUser();
        mdatabaseref=FirebaseDatabase.getInstance().getReference("Users/"+currentuser.getUid());
        save_details=(Button)findViewById(R.id.save_details);
        email.setText((CharSequence) currentuser.getEmail().toString());


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selected=(RadioButton)findViewById(gender.getCheckedRadioButtonId());
                save_details.setEnabled(true);
            }
        });
        if(!Network.isNetworkEnabled(this))
            Network.show_alert(this);
        pd.setMessage("Loading");
        pd.show();

        if(currentuser.getDisplayName()!=null)
            fullname.setText((CharSequence) currentuser.getDisplayName().toString());




        mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_Details user_details=dataSnapshot.getValue(User_Details.class);
                if(user_details!=null)
                {if(user_details.getAddress()!=null)
                {
                    address.setText(user_details.getAddress());
                }
                if(user_details.getLoyalty()!=null)
                {
                    loyalty.setText(user_details.getLoyalty());
                }
                if(user_details.getGender()!=null)
                {

                    RadioButton rb=(RadioButton)findViewById(R.id.male);
                    RadioButton rbb=(RadioButton)findViewById(R.id.female);
                    if(user_details.getGender().toLowerCase().equals("male"))
                        rb.setChecked(true);
                    else
                        rbb.setChecked(true);
                }


                if(user_details.mobile!=null)
                mobile.setText(user_details.mobile);
                pd.dismiss();
                }
                else
                {
                    pd.dismiss();
                    RadioButton rb=(RadioButton)findViewById(R.id.male);
                    rb.setChecked(true);
                }

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.dismiss();

            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,Orders.class));
            }
        });








    }



    public void save_the_details(View view)
    {
        if(!Network.isNetworkEnabled(this))
            Network.show_alert(this);
        else{
            if(validatemobile()){
        pd.setMessage("updating");
        pd.show();
        if(!fullname.getText().toString().equals(currentuser.getDisplayName()))
        {UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullname.getText().toString()).build();
        currentuser.updateProfile(profileUpdates);
    }

    User_Details user_details=new User_Details(address.getText().toString(),loyalty.getText().toString(),selected.getText().toString(),mobile.getText().toString());
    mdatabaseref.setValue(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            pd.dismiss();
            Toast.makeText(getApplicationContext(),"Updated Sucessfully",Toast.LENGTH_SHORT).show();
        }
    });
    }}}

    public void logout(View view){
        if(!Network.isNetworkEnabled(this))
            Network.show_alert(this);
        else{
     //   mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                    }
                });
        finish();
        Intent intent=new Intent(this,Sign_In.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }}

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;

    }
    private class MyTextWatcher implements TextWatcher {
        View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.mobile:
                    validatemobile();
                    save_details.setEnabled(true);
                    break;
                case R.id.full_name:
                    save_details.setEnabled(true);
                    break;
                case R.id.address:
                    save_details.setEnabled(true);
                    break;




            }
        }
}

    private boolean validatemobile() {
        if (mobile.getText().toString().isEmpty()) {
            return true;
        } else if (!Patterns.PHONE.matcher(mobile.getText().toString().trim()).matches()||mobile.getText().toString().trim().length()!=10) {
            mobile.setError("Invalid Number");
            return false;
        } else
        {
            mobile.setError(null);//removes error
        }
        return true;
    }
}
