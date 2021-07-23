package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Update_Itemlist extends AppCompatActivity{

    RecyclerView featuredView;
    featuredAdapter adapter;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__itemlist);

        featuredView = (RecyclerView)findViewById(R.id.featured_recycler);
        heading = (TextView)findViewById(R.id.update_heading);

        featuredRecycler();
    }

    private void featuredRecycler() {

        featuredView.setHasFixedSize(true);
        featuredView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<homeadapter>  home = new ArrayList<>();

        home.add(new homeadapter(R.drawable.ic_baseline_add_chart_24,"Add Item"));
        home.add(new homeadapter(R.drawable.ic_baseline_pageview_24,"View Item"));
        //home.add(new homeadapter(R.drawable.ic_baseline_update_24,"Update Item"));
        home.add(new homeadapter(R.drawable.ic_baseline_delete_forever_24,"Delete Item"));

        adapter = new featuredAdapter(home);

        featuredView.setAdapter(adapter);

        adapter.OnRecyclerViewClickListener(new featuredAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent=getIntent();
                String key = intent.getExtras().getString("Key");
                String occasion = intent.getExtras().getString("Occasion");

                heading.setText(occasion);
                switch(position)
                {
                    case 0:
                        intent = new Intent(Update_Itemlist.this,Additemtodb.class);
                        intent.putExtra("Key",key);
                        intent.putExtra("Occasion",occasion);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(Update_Itemlist.this,Viewdatafromdb.class);
                        intent.putExtra("Key",key);
                        intent.putExtra("Occasion",occasion);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Update_Itemlist.this,DeleteItem.class);
                        intent.putExtra("Key",key);
                        intent.putExtra("Occasion",occasion);
                        startActivity(intent);
                        break;
//                    case 3:
//                        intent = new Intent(Update_Itemlist.this,occasion.class);
//                        intent.putExtra("Key",key);
//                        startActivity(intent);
//                        break;

                }
            }
        });
    }
}
