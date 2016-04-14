package edu.pitt.cs.cs1635.mypantry;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.pitt.cs.cs1635.mypantry.adapters.RecipeItemListAdapter;
import edu.pitt.cs.cs1635.mypantry.model.Item;
import edu.pitt.cs.cs1635.mypantry.model.Recipe;
import edu.pitt.cs.cs1635.mypantry.services.Store;

public class NewRecipe extends BaseActivity {

    public ArrayList<Item> ingredients = new ArrayList<>();
    Context context;
    public ArrayAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initNavigationDrawer();

        adapter = new RecipeItemListAdapter(this, ingredients);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        final Store store = Store.getInstance();

        Button button = (Button)findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText entry = (EditText) findViewById(R.id.new_ingredient);
                String ingredientName = entry.getText().toString();
                Item searchResult = store.getItem(ingredientName);

                if (searchResult != null) {
                    ingredients.add(searchResult);
                    adapter.notifyDataSetChanged();
                    entry.setText("");
                    setListViewHeightBasedOnChildren(listView);
                }
            }
        });
    }

    public void submitRecipe(View v){
        String name = ((EditText) findViewById(R.id.new_name)).getText().toString();
        String directions = ((EditText) findViewById(R.id.new_directions)).getText().toString();

        Store store = Store.getInstance();

        Recipe recipe = new Recipe();
        recipe.setTitle(name);
        recipe.setDirections(directions);

        // Need to save the recipe before we can add the items,
        store.saveRecipe(recipe);

        for (Item item : ingredients) {
            store.addItemToRecipe(item, recipe);
        }

        setResult(RESULT_OK);
        finish();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
