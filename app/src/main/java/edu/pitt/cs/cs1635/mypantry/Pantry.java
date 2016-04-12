package edu.pitt.cs.cs1635.mypantry;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

import edu.pitt.cs.cs1635.mypantry.adapters.PantryItemListAdapter;
import edu.pitt.cs.cs1635.mypantry.model.Item;

public class Pantry extends BaseActivity {

    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pantry.this, NewPantry.class);
                startActivityForResult(intent, 1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initNavigationDrawer();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Set up pantry item list
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pantry_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PantryItemListAdapter adapter = new PantryItemListAdapter(this.itemDao);
        recyclerView.setAdapter(adapter);
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){
            Item item = new Item();
            item.setTitle(data.getStringExtra("name"));
            item.setAmount(2);  // Default new items to "High"
            item.setOnGroceryList(false);
            this.itemDao.insertOrReplace(item);
        }
    }

}
