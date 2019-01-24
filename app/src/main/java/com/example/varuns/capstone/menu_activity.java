package com.example.varuns.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varuns.capstone.model.Artisan;
import com.example.varuns.capstone.model.ArtisanItem;
import com.example.varuns.capstone.services.ApiService;
import com.example.varuns.capstone.services.RestfulResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView artisanList;
    private ArtisanAdapter artisanAdapter;
    private Integer[] artisanImages = {R.drawable.maria, R.drawable.native5, R.drawable.native3 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Artisans");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        artisanList = (ListView)findViewById(R.id.artisanList);
        //TODO - uncomment this getArtisans();
        getArtisansNoDB();
        artisanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Artisan artisan = (Artisan) parent.getAdapter().getItem(position);
                Intent intent = new Intent(menu_activity.this, ScrollingActivity.class);
                intent.putExtra("artisanId", artisan.getArtisanId());
                startActivity(intent);

            }
        });

//        test();

//        Button button = (Button) findViewById(R.id.artisan_temp);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//                Intent myintent = new Intent(menu_activity.this, ScrollingActivity.class);
//                startActivity(myintent);
//            }
//        });
    }

    public void getArtisansNoDB() {
        List<Artisan> artisans = new ArrayList<Artisan>();
        //Integer artisanId, String firstName, String lastName, String bio, List<ArtisanItem> artisanItems
        artisans.add(new Artisan(1, "martha", "blah", "hello", new ArrayList<ArtisanItem>()));
        menu_activity.ArtisanAdapter artisanAdapter = new menu_activity.ArtisanAdapter(artisans);
        artisanList.setAdapter(artisanAdapter);
    }

    public void getArtisans() {
        Call<RestfulResponse<List<Artisan>>> call = ApiService.artisanService().getAllArtisans();
        //handle the response
        call.enqueue(new Callback<RestfulResponse<List<Artisan>>>() {
            @Override
            public void onResponse(Call<RestfulResponse<List<Artisan>>> call, Response<RestfulResponse<List<Artisan>>> response) {
                List<Artisan> artisans = response.body().getData();
                Toast.makeText(menu_activity.this, "success", Toast.LENGTH_SHORT).show();
                menu_activity.ArtisanAdapter artisanAdapter = new menu_activity.ArtisanAdapter(artisans);
                artisanList.setAdapter(artisanAdapter);
            }

            @Override
            public void onFailure(Call<RestfulResponse<List<Artisan>>> call, Throwable t) {
                Toast.makeText(menu_activity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_tasks) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class ArtisanAdapter extends BaseAdapter {

        List<Artisan> artisans;
        List<Artisan> original;

        public ArtisanAdapter(List<Artisan> artisans) {
            this.artisans = artisans;
        }

        public void filterArtisans(List<Artisan> newArtisans) {
            this.original = this.artisans;
            this.artisans = newArtisans;
        }

        public void undoFilter() {
            this.artisans = original;
        }

        public void addArtisan(Artisan a) {
            artisans.add(a);
        }

        public int getCount() {
            return artisans.size();
        }
        public Artisan getItem(int i) {
            return artisans.get(i);
        }
        public long getItemId(int i) {
            return 0;
        }
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.artisan_list_layout, null);
            ImageView artisanImage = (ImageView)view.findViewById(R.id.artisanImage);
            TextView artisanName = (TextView)view.findViewById(R.id.artisanName);
            artisanName.setText(artisans.get(i).getFirstName() + " " + artisans.get(i).getLastName());
            artisanImage.setImageResource(artisanImages[i%3]);
            return view;
        }

    }


}
