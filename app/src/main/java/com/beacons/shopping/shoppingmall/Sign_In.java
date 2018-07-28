package com.beacons.shopping.shoppingmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Sign_In extends AppCompatActivity implements View.OnClickListener {
    private GoogleApiClient mGoogleApiClient;
    public Button signinwithemail,SigninButton;
    private FirebaseAuth mAuth;
    final private String TAG="Adi_check";
    private int RC_SIGN_IN=1;
    private GoogleSignInClient mGoogleSignInClient;
    public ProgressDialog pd;
    public EditText email,password;
    public TextInputLayout email_layout,password_layout;
    Bundle rec;
    String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        setTitle("Sign In");
        check="";
        rec=getIntent().getExtras();
        if(rec!=null)
        {
            check=rec.getString("check");
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        SigninButton=(Button)findViewById(R.id.SigninButton);
        signinwithemail=(Button)findViewById(R.id.signinwithemail);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        password_layout=(TextInputLayout)findViewById(R.id.password_layout);
        email_layout=(TextInputLayout)findViewById(R.id.name_layout);

        email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));
       // email.addTextChangedListener(new MyTextWatcher(email));

        SigninButton.setOnClickListener(this);
        signinwithemail.setOnClickListener(this);
        pd=new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();


    }

    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        if(mAuth!=null&&mAuth.getCurrentUser()!=null&&mAuth.getCurrentUser().getDisplayName()!=null)
        {
            if(mAuth.getCurrentUser().getDisplayName().equals("")){
                Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

            }
            else
            Toast.makeText(this,mAuth.getCurrentUser().getDisplayName(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(Sign_In.this,Profile.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);}
        //updateUI(currentUser);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                pd.setMessage("Logging You In");
                pd.show();
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

                Toast.makeText(getApplicationContext(),"Server Issues",Toast.LENGTH_SHORT).show();
                // [START_EXCLUDE]
          //      updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.SigninButton) {
            if(!Network.isNetworkEnabled(getApplicationContext()))
                Network.show_alert(getApplicationContext());
            else
            signIn();
        }
        else if (i == R.id.signinwithemail) {
            if(!Network.isNetworkEnabled(getApplicationContext()))
                Network.show_alert(getApplicationContext());
            else
            signinwithemail();
        }
    }

    private void signinwithemail() {
        if(validateemail()&&validatepassword()){
         if(!Network.isNetworkEnabled(this))
            Network.show_alert(this);
        else{
            pd.setMessage("Logging You In");
        pd.show();
        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            pd.dismiss();
                            if(mAuth.getCurrentUser().getDisplayName()==null||mAuth.getCurrentUser().getDisplayName().equals("")){
                                Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

                            }
                            else
                            Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                            if(check.equals("")) {
                                finish();
                                startActivity(new Intent(Sign_In.this, Profile.class));
                            }
                            else if(check.equals("product"))
                            {
                                finish();
                            }
                            else if(check.equals("orders"))
                            {
                                finish();
                                startActivity(new Intent(Sign_In.this,Orders.class));
                            }
                            else if(check.equals("cart"))
                            {
                                finish();
                                startActivity(new Intent(Sign_In.this,Cart.class));


                            }
                        }
                        else  {
                        pd.dismiss();
                            if(task.getException()instanceof FirebaseAuthInvalidUserException||task.getException()instanceof FirebaseAuthInvalidCredentialsException)
                                Toast.makeText(getApplicationContext(),"Email or Password Incorrect",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }}}

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
      //  showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(check.equals("")) {
                                finish();
                                startActivity(new Intent(Sign_In.this, Profile.class));
                            }
                            else if(check.equals("product"))
                            {
                                finish();
                            }
                            else if(check.equals("orders"))
                            {
                                finish();
                                startActivity(new Intent(Sign_In.this,Orders.class));
                            }
                            else if(check.equals("cart"))
                            {
                                finish();
                                startActivity(new Intent(Sign_In.this,Cart.class));


                            }

                            //                    updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.sign_in_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
          //                  updateUI(null);
                        }

                        // [START_EXCLUDE]
                       // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void gotosignup(View view)
    {
        Intent intent=new Intent(this,Sign_Up.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
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
            password_layout.setError("Passwords must be six character long");
            password_layout.requestFocus();
            return false;

        }
        else password_layout.setErrorEnabled(false);
        return true;
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


            }
        }
    }

    public void forgot_password(View view) {
        String emailaddress;
        if(!Network.isNetworkEnabled(this))
            Network.show_alert(this);

        else if (validateemail()) {
            emailaddress = email.getText().toString();
            pd.setMessage("Loading");
            pd.show();
            mAuth.sendPasswordResetEmail(emailaddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Reset password instructions has sent to your email",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),
                                        "Email don't exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
