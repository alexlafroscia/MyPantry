package edu.pitt.cs.cs1635.mypantry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeDetail extends BaseActivity {

    String name;
    ArrayList<String> ingredients;
    String directions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initNavigationDrawer();

        Intent intent = getIntent();
        name = intent.getStringExtra("RECIPE_NAME");
        directions = intent.getStringExtra("RECIPE_DIRECTIONS");
        ingredients  = intent.getStringArrayListExtra("RECIPE_ING");


        TextView r = (TextView) this.findViewById(R.id.rec_name);
        r.setText(name);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list, ingredients);
        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);

        TextView r4 = (TextView) this.findViewById(R.id.rec_directions);
        r4.setText(directions);

    }
}
