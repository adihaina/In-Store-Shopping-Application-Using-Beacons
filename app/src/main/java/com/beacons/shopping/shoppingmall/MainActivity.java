package com.beacons.shopping.shoppingmall;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Bundle rec;
    DatabaseReference mref;
    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@mall.com")) {
                navigationView.getMenu().findItem(R.id.uploadsection).setVisible(true);
            } else {
                navigationView.getMenu().findItem(R.id.uploadsection).setVisible(false);
            }
        } else {
            navigationView.getMenu().findItem(R.id.uploadsection).setVisible(false);
        }

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        if(appLinkData!=null){
        if(appLinkData.toString().equals("http://www.jnanagni.in/shirtpeter"))
        {
            displayit(R.id.men_shirt,"Peter England");
        }
            else if(appLinkData.toString().equals("http://www.jnanagni.in/shirtjohn"))
            {
                displayit(R.id.men_shirt,"John Player");
            }
           else if(appLinkData.toString().equals("http://www.jnanagni.in/suit"))
            {
                displayit(R.id.women_suits,"no");
            }
            else if(appLinkData.toString().equals("http://www.jnanagni.in/top"))
            {
                displayit(R.id.women_tops,"no");
            }
            else if(appLinkData.toString().equals("http://www.jnanagni.in/menshoe"))
            {
                displayit(R.id.men_footwear,"no");
            }
           else if(appLinkData.toString().equals("http://www.jnanagni.in/womenshoe"))
            {
                displayit(R.id.women_footwear,"no");
            }
            if(appLinkData.toString().equals("http://www.jnanagni.in/trouser"))
            {
                displayit(R.id.men_jeans,"no");
            }


        }
        else {
            displayit(R.id.home,"no");

        }
        if (!Network.isNetworkEnabled(this)) {
            Network.show_alert(this);
        }
        // ATTENTION: This was auto-generated to handle app links.

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@mall.com")) {
                navigationView.getMenu().findItem(R.id.uploadsection).setVisible(true);
            }
            else{
                navigationView.getMenu().findItem(R.id.uploadsection).setVisible(false);
            }}
        else{            navigationView.getMenu().findItem(R.id.uploadsection).setVisible(false);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {

                super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.gotocart) {
            displayit(R.id.Cart,"no");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displayit(id, "no");
        return true;
    }
        public void displayit(int id,String brand)
    {
        if (id == R.id.men_shirt) {
            Fragment frag = new Shirt();
            Bundle bundle = new Bundle();
            String myMessage = "MEN_SHIRT";
            bundle.putString("brand",brand);
            bundle.putString("message", myMessage );
            frag.setArguments(bundle);
            FragmentTransaction Ft=getSupportFragmentManager().beginTransaction();
            Ft.replace(R.id.context_main,frag);
            setTitle("Shirts");
            Ft.commit();
        } else if (id == R.id.men_jeans) {
            Fragment frag = new Shirt();
            Bundle bundle = new Bundle();
            bundle.putString("brand",brand);
            String myMessage = "MEN_TROUSERS";
            bundle.putString("message", myMessage );
            frag.setArguments(bundle);
            FragmentTransaction Ft=getSupportFragmentManager().beginTransaction();
            Ft.replace(R.id.context_main,frag);
            setTitle("Trousers");
            Ft.commit();

        } else if (id == R.id.women_suits) {
            Fragment frag = new Shirt();
            Bundle bundle = new Bundle();
            String myMessage = "WOMEN_SUITS";
            bundle.putString("brand",brand);
            bundle.putString("message", myMessage );
            frag.setArguments(bundle);
            FragmentTransaction Ft=getSupportFragmentManager().beginTransaction();
            Ft.replace(R.id.context_main,frag);
            setTitle("Suits");
            Ft.commit();

        } else if (id == R.id.women_tops) {
            Fragment frag = new Shirt();
            Bundle bundle = new Bundle();
            bundle.putString("brand",brand);
            String myMessage = "WOMEN_TOP";
            bundle.putString("message", myMessage );
            frag.setArguments(bundle);
            FragmentTransaction Ft=getSupportFragmentManager().beginTransaction();
            Ft.replace(R.id.context_main,frag);
            setTitle("Tops");
            Ft.commit();


        }
        else if (id == R.id.men_footwear) {
            Fragment frag = new Shirt();
            Bundle bundle = new Bundle();
            bundle.putString("brand",brand);
            String myMessage = "MEN_FOOTWEAR";
            bundle.putString("message", myMessage );
            frag.setArguments(bundle);
            FragmentTransaction Ft=getSupportFragmentManager().beginTransaction();
            Ft.replace(R.id.context_main,frag);
            setTitle("Men Footwear");
            Ft.commit();


        }
        else if (id == R.id.women_footwear) {
            Fragment frag = new Shirt();
            Bundle bundle = new Bundle();
            String myMessage = "WOMEN_FOOTWEAR";
            bundle.putString("message", myMessage );
            bundle.putString("brand",brand);
            frag.setArguments(bundle);
            FragmentTransaction Ft=getSupportFragmentManager().beginTransaction();
            Ft.replace(R.id.context_main,frag);
            setTitle("Women Footwear");
            Ft.commit();

        }
        else if (id == R.id.home) {
            Fragment frag = new Home();
            FragmentTransaction Ft=getSupportFragmentManager().beginTransaction();
            setTitle("Home");
            Ft.replace(R.id.context_main,frag);
            Ft.commit();

        }


        else if (id == R.id.Profile) {
            Intent in;
            if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            {
                in=new Intent(this,Profile.class);

            }
            else
            in=new Intent(this,Sign_In.class);
            startActivity(in);

        } else if (id == R.id.Cart) {
            Intent in=new Intent(this,Cart.class);
            startActivity(in);

        }
        else if (id == R.id.orders) {
            Intent in=new Intent(this,Orders.class);
            startActivity(in);

        }
        else if(id==R.id.uploadsection)
        {startActivity(new Intent(this,Uploadsection.class));}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}
