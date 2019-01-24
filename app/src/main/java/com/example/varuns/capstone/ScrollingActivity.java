package com.example.varuns.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
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

public class ScrollingActivity extends AppCompatActivity {

    Artisan artisan;
    TextView artisanBio;
    private Integer[] artisanImages = {R.drawable.maria, R.drawable.native5, R.drawable.native3 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(ScrollingActivity.this, Messages.class);
                startActivity(myintent);
            }
        });
        artisanBio = (TextView) findViewById(R.id.artisanBio);
        Integer artisanId = getIntent().getExtras().getInt("artisanId");
        getArtisanById(artisanId);

    }

    private void getArtisanById(final Integer artisanId) {

        Call<RestfulResponse<Artisan>> call = ApiService.artisanService().getArtisanById(artisanId.toString());
        //handle the response
        call.enqueue(new Callback<RestfulResponse<Artisan>>() {
            @Override
            public void onResponse(Call<RestfulResponse<Artisan>> call, Response<RestfulResponse<Artisan>> response) {
                artisan = response.body().getData();
                ImageView imageView = (ImageView)findViewById(R.id.imageButton);
                imageView.setImageResource(artisanImages[(artisan.getArtisanId() - 1)%3]);
                artisanBio = (TextView) findViewById(R.id.artisanBio);
                TextView artisanName = (TextView) findViewById(R.id.artisanName);
                System.out.print("bio: " + artisan.getBio());
                artisanBio.setText(artisan.getBio());
                artisanName.setText(artisan.getFirstName() + " " + artisan.getLastName());
                ListView artisanList = (ListView)findViewById(R.id.artisanProductList);
                if (artisan.getArtisanItems().size() > 0) {
                    ScrollingActivity.ArtisanProductAdapter artisanProductAdapter = new ScrollingActivity.ArtisanProductAdapter(artisan.getArtisanItems());
                    artisanList.setAdapter(artisanProductAdapter);
                }

            }

            @Override
            public void onFailure(Call<RestfulResponse<Artisan>> call, Throwable t) {
                Toast.makeText(ScrollingActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class ArtisanProductAdapter extends BaseAdapter {

        List<ArtisanItem> artisanItems;

        public ArtisanProductAdapter(List<ArtisanItem> artisanItems) {

            this.artisanItems = artisanItems;
        }

        public int getCount() {
            return artisanItems.size();
        }
        public ArtisanItem getItem(int i) {
            return artisanItems.get(i);
        }
        public long getItemId(int i) {
            return 0;
        }
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.artisan_products_layout, null);
            TextView itemName = (TextView)view.findViewById(R.id.itemName);
            TextView itemDescription = (TextView)view.findViewById(R.id.itemDescription);
            itemName.setText(artisanItems.get(i).getItemName());
            itemDescription.setText(artisanItems.get(i).getItemDescription());
            return view;
        }

    }
}
