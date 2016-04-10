package edu.pitt.cs.cs1635.mypantry;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import edu.pitt.cs.cs1635.mypantry.model.DaoMaster;
import edu.pitt.cs.cs1635.mypantry.model.DaoSession;
import edu.pitt.cs.cs1635.mypantry.model.ItemDao;
import edu.pitt.cs.cs1635.mypantry.model.RecipeDao;

/**
 * Base activity class to extend other activities from
 *
 * Useful so that we can keep the navigation drawer handler in one place, without duplicating it
 * across each Activity.
 *
 * Created by alex on 3/26/16.
 */
public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    protected ItemDao itemDao;
    protected RecipeDao recipeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "pantry-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        itemDao = daoSession.getItemDao();
        recipeDao = daoSession.getRecipeDao();
    }

    protected void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recipes){
            Intent intent = new Intent(this, Recipes.class);
            startActivity(intent);
        } else if (id == R.id.nav_pantry) {
            Intent intent = new Intent(this, Pantry.class);
            startActivity(intent);
        } else if (id == R.id.nav_grocerylist){
            Intent intent = new Intent(this, GroceryListActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
