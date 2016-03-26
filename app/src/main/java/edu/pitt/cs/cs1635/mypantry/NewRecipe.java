package edu.pitt.cs.cs1635.mypantry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class NewRecipe extends BaseActivity {

    public ArrayList<String> ingredients = new ArrayList<> ();
    Context context;
    public int numIngredients = 0;
    public ArrayAdapter adapter;
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

        adapter = new ArrayAdapter<>(this, R.layout.list, ingredients);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Button button = (Button)findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText entry = (EditText) findViewById(R.id.new_ingredient);
                String ing = entry.getText().toString();
                ingredients.add(ing);
                numIngredients++;
                adapter.notifyDataSetChanged();
                entry.setText("");
            }
        });

    }

    public void submitRecipe(View v){
        String name = ((EditText) findViewById(R.id.new_name)).getText().toString();
        String directions = ((EditText) findViewById(R.id.new_directions)).getText().toString();

        Intent intent = new Intent();
        intent.putExtra("new_name", name);
        intent.putExtra("new_ingredients", ingredients);
        intent.putExtra("new_directions", directions);
        setResult(RESULT_OK, intent);
        finish();
    }
}
