package com.example.varuns.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.example.varuns.capstone.model.Artisan;
import com.example.varuns.capstone.services.ApiService;
import com.example.varuns.capstone.services.ArtisanService;
import com.example.varuns.capstone.services.RestfulResponse;
import com.example.varuns.capstone.services.UserService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });
//        Retrofit retrofit = ApiService.getRetrofitInstance();
//        ArtisanService artisanService = retrofit.create(ArtisanService.class);
//        Call<RestfulResponse<List<Artisan>>> call = artisanService.getAllArtisans();
//        call.enqueue(new Callback<RestfulResponse<List<Artisan>>>() {
//            @Override
//            public void onResponse(Call<RestfulResponse<List<Artisan>>> call, Response<RestfulResponse<List<Artisan>>> response) {
//                List<Artisan> a1 = response.body().getData();
//                Toast.makeText(MainActivity.this, a1.get(0).getBio(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<RestfulResponse<List<Artisan>>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
