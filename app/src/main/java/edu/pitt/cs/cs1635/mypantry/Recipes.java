package edu.pitt.cs.cs1635.mypantry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Recipes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<TempData> rec = new ArrayList<>();
    public final static String RECIPE_NAME = "RECIPE_NAME";
    public final static String RECIPE_ING = "RECIPE_ING";
    public final static String RECIPE_DIRECTIONS = "RECIPE_DIRECTIONS";
    public static ArrayAdapter adapter;
    public static Context context;

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

        if(rec.size() == 0){
            populateTemp();
        }

        adapter = new ArrayAdapter<TempData>(this, R.layout.list, rec);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(context, RecipeDetail.class);

                intent.putExtra(RECIPE_NAME, ((TempData)arg0.getItemAtPosition(position)).getName());
                intent.putExtra(RECIPE_ING, ((TempData)arg0.getItemAtPosition(position)).getIngredients());
                intent.putExtra(RECIPE_DIRECTIONS, ((TempData)arg0.getItemAtPosition(position)).getDirections());
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
                rec.add(temp);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void populateTemp(){
        ArrayList<String> ing = new ArrayList<>();
        makeApplePie();
        makeChicken();
        makeFettuccini();
    }

    private static void makeApplePie(){
        ArrayList<String> ing = new ArrayList<>();
        ing.add("1 Pie Crust");
        ing.add("6 Cups Sliced Apples");
        ing.add("3/4 Cup Sugar");
        ing.add("2 Tb All-Purpose Flour");
        ing.add("3/4 tsp Ground Cinnamon");
        ing.add("1/4 tsp Salt");
        ing.add("1/8 tsp Ground Nutmeg");
        ing.add("1 Tb Lemon Juice");
        String directions = "1. Heat oven to 425°F. Place 1 pie crust in ungreased 9-inch glass pie plate. Press firmly against side and bottom.\n"
                + "2. In large bowl, gently mix filling ingredients; spoon into crust-lined pie plate. Top with second crust. Wrap excess top crust under bottom crust edge, pressing edges together to seal; flute. Cut slits or shapes in several places in top crust.\n"
                + "3. Bake 40 to 45 minutes or until apples are tender and crust is golden brown. Cover edge of crust with 2- to 3-inch wide strips of foil after first 15 to 20 minutes of baking to prevent excessive browning. Cool on cooling rack at least 2 hours before serving.";
        rec.add(new TempData("Apple Pie", ing, directions));
    }

    private static void makeChicken(){
        ArrayList<String> ing = new ArrayList<>();
        ing.add("2 Tb olive oil");
        ing.add("1 clove garlic");
        ing.add("1 cup dry bread crumbs");
        ing.add("2/3 cup grated parmesan cheese");
        ing.add("1 tsp dried basil leaves");
        ing.add("1/4 tsp ground black pepper");
        ing.add("6 skinless boneless chicken breast halves");
        String directions = "Preheat oven to 350 degrees F (175 degrees C). Lightly grease a 9x13 inch baking dish.\n"
                + "In a bowl, blend the olive oil and garlic. In a separate bowl, mix the bread crumbs, Parmesan cheese, basil, and pepper. Dip each chicken breast in the oil mixture, then in the bread crumb mixture. Arrange the coated chicken breasts in the prepared baking dish, and top with any remaining bread crumb mixture.\n"
                + "Bake 30 minutes in the preheated oven, or until chicken is no longer pink and juices run clear.";
        rec.add(new TempData("Baked Garlic Parmesan Chicken", ing, directions));
    }

    private static void makeFettuccini(){
        ArrayList<String> ing = new ArrayList<>();
        ing.add("10 oz fettuccini pasta");
        ing.add("1/2 cup butter");
        ing.add("5 cloves garlic");
        ing.add("1 cup heavy cream");
        ing.add("1 egg yolk");
        ing.add("2 cups grated parmesan cheese");
        ing.add("2 Tb dried parsley");
        String directions = "Bring a large pot of lightly salted water to a boil. Add fettucine pasta and cook for 8 to 10 minutes or until al dente; drain\n"
                + "In a large skillet melt the butter and add the chopped garlic. Cook on low for about 5 minutes, stirring often, making sure not to burn the garlic.\n" +
                "Pour about a 1/4 cup of the heavy cream into a small bowl. Add the egg yolk and beat together; put aside. Pour the remaining cream into the frying pan. Increase the heat to medium-high. As the cream starts to boil, mix rapidly using a whisk. Slowly add the cream/egg mixture. You do not want the egg to curdle. Continue whisking until well blended.\n" +
                "Add 1 cup of the Parmesan cheese and continue to mix the cream. Pour in the remaining Parmesan and the parsley, mix until smooth. Immediately remove from stove. Serve over cooked pasta.";
        rec.add(new TempData("Fettuccini Alfredo", ing, directions));

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
