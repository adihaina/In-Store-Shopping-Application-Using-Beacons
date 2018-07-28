package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Sign_Up extends AppCompatActivity {

    public EditText email,password,confirm_password;
    public FirebaseAuth mAuth;
    public TextInputLayout email_layout,password_layuot,confirm_password_layout;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        email=(EditText)findViewById(R.id.email);
        setTitle("Sign Up");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        password=(EditText)findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        confirm_password=(EditText)findViewById(R.id.confirm_password);
        email_layout=(TextInputLayout)findViewById(R.id.name_layout);
        confirm_password_layout=(TextInputLayout)findViewById(R.id.confirm_password_layout);
        password_layuot=(TextInputLayout)findViewById(R.id.password_layout);
        pd=new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Registering");
        email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));
        confirm_password.addTextChangedListener(new MyTextWatcher(confirm_password));
    }

    public void signup(View view)
    {
        if (validatepassword()&&validateemail()&&validateconfirm_password())
        {
            if(!Network.isNetworkEnabled(this)){Network.show_alert(this);}
            else{
                pd.show();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseUser user=mAuth.getCurrentUser();
                                finish();
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Sign_Up.this,Profile.class));
                            }
                            else{
                                pd.dismiss();
                                if(task.getException()instanceof FirebaseAuthUserCollisionException)
                                Toast.makeText(getApplicationContext(),"Email Already Registered",Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                    }}
                });


    }}}


    private boolean validateemail() {
        if(email.getText().toString().equals(""))
        {
            email_layout.requestFocus();
            email_layout.setError("Email can't be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
        {
            email_layout.requestFocus();
            email_layout.setError("Invalid Email");
            return false;
        }
        else email_layout.setErrorEnabled(false);
        return true;
    }
    public boolean validatepassword()
    {

         if(password.getText().toString().length()<6)
        {
            password_layuot.setError("Passwords must be six character long");
            password_layuot.requestFocus();
            return false;

        }
        else password_layuot.setErrorEnabled(false);
        return true;
    }
    public boolean validateconfirm_password()
    {
        if (confirm_password.getText().toString().length()<6)
        {
            confirm_password_layout.setError("Passwords must be six character long");
            confirm_password_layout.requestFocus();
            return false;

        }
        else if(!password.getText().toString().equals(confirm_password.getText().toString()))
        {
            confirm_password_layout.setError("Passwords Don't Match");
            confirm_password_layout.requestFocus();
            return false;
        }
        else confirm_password_layout.setErrorEnabled(false);
        return true;
    }

    public void gotosignin(View view)
    {
        Intent intent=new Intent(this,Sign_In.class);
        finish();
     //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
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
                case R.id.email:
                    validateemail();
                    break;
                case R.id.password:
                    validatepassword();
                    break;
                case R.id.confirm_password:
                    validateconfirm_password();
                    break;


            }
        }
    }}
