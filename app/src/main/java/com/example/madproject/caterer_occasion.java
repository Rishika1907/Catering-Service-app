package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class caterer_occasion extends AppCompatActivity {
    RecyclerView dataList;
    ArrayList<String> titles;
    ArrayList<Integer> images;
    TextView heading_name;
    //Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_main);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("Name");

        heading_name = (TextView)findViewById(R.id.occasion_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        heading_name.setText("Hi,"+name);
        dataList=findViewById(R.id.dataList);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Birthday Parties");
        titles.add("Wedding");
        titles.add("Corporate Events");
        titles.add("Puja");
        titles.add("House Warming");

        images.add(R.drawable.birthday);
        images.add(R.drawable.wedding);
        images.add(R.drawable.office);
        images.add(R.drawable.puja);
        images.add(R.drawable.house);

        caterer_Adapter adapter = new caterer_Adapter(caterer_occasion.this,titles,images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);



        adapter.OnRecyclerViewClickListener(new caterer_Adapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = getIntent();
                String key = intent.getExtras().getString("Key");

                intent = new Intent(caterer_occasion.this,Update_Itemlist.class);
                intent.putExtra("Key",key);

                switch (position){
                    case 0:
                        intent.putExtra("Occasion","Birthday Parties");
                        break;
                    case 1:
                        intent.putExtra("Occasion","Wedding");
                        break;
                    case 2:
                        intent.putExtra("Occasion","Corporate Events");
                        break;
                    case 3:
                        intent.putExtra("Occasion","Puja");
                        break;
                    case 4:
                        intent.putExtra("Occasion","House Warming");
                        break;
                }
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.caterer_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = getIntent();
        String key = intent.getExtras().getString("Key");
        String service = intent.getExtras().getString("Service");

        switch(item.getItemId()){
            case R.id.profile:
                intent = new Intent(caterer_occasion.this,Profile_view.class);
                intent.putExtra("Key",key);
                startActivity(intent);
                return true;
            case R.id.pending:
                intent = new Intent(caterer_occasion.this,Pendingvieworders.class);
                intent.putExtra("Key",key);
                intent.putExtra("Service",service);
                startActivity(intent);
                return true;
            case R.id.accepted:
                intent = new Intent(caterer_occasion.this,View_orders.class);
                intent.putExtra("Key",key);
                intent.putExtra("Service",service);
                startActivity(intent);
                return true;
            case R.id.logout:
                intent = new Intent(caterer_occasion.this,Login_Page.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
