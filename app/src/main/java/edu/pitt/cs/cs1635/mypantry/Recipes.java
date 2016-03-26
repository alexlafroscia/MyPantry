package edu.pitt.cs.cs1635.mypantry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Recipes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<TempData> rec = new ArrayList<>();
    public final static String RECIPE_NAME = "RECIPE_NAME";

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewRecipe.class);
                startActivityForResult(intent, 1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        populateTemp();


        ArrayAdapter adapter = new ArrayAdapter<TempData>(this, R.layout.content_recipes, R.id.list_text, rec);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(context, RecipeDetail.class);

                intent.putExtra(RECIPE_NAME, ((TempData)arg0.getItemAtPosition(position)).getName());
                intent.putExtra("RECIPE_ING", ((TempData)arg0.getItemAtPosition(position)).getIngredients());
                intent.putExtra("RECIPE_DIRECTIONS", ((TempData)arg0.getItemAtPosition(position)).getDirections());
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //return from NewRecipe
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String name = data.getStringExtra("new_name");
                String directions = data.getStringExtra("new_directions");
                ArrayList<String> ingredients = data.getStringArrayListExtra("new_ingredients");
                TempData temp = new TempData(name, ingredients, directions);

            }
        }
    }




    public void populateTemp(){
        ArrayList<String> ing = new ArrayList<>();
        ing.add("ingredient 1");
        ing.add("ingredient 2");
        ing.add("ingredient 3");
        ing.add("ingredient 4");
        ing.add("ingredient 5");
        rec.add(new TempData("Apple Pie", ing, "bake the pie"));
        rec.add(new TempData("Banana Bread", ing, "bake the banana bread"));
        rec.add(new TempData("Chicken Noodle Soup", ing, "put it in a pot and boil it"));
        rec.add(new TempData("Dumplings", ing, "steam them"));
        rec.add(new TempData("Eggplant Parmesan", ing, "cook it"));
        rec.add(new TempData("French Toast", ing, "cook stuff"));
        rec.add(new TempData("Garlic Bread", ing, "put garlic on bread"));
        rec.add(new TempData("Hash Browns", ing, "shred potatoes"));
        rec.add(new TempData("Ice Cream Sandwich", ing, "something"));
        rec.add(new TempData("Jalapeno Poppers", ing, "just cook stuff"));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_recipes){
            Intent intent = new Intent(this, Recipes.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
