package edu.pitt.cs.cs1635.mypantry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class GroceryListActivity extends BaseActivity implements AddgListItemDialogFragment.AddListItemDialogListener{

    public static Context context;
    public static ArrayList<String> listitems = new ArrayList<>();
    public static ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        context=this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add a new item to the list!
                showDialog();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initNavigationDrawer();

        if(listitems.size()==0){
            populateList();
        }

        adapter= new ArrayAdapter<String>(this, R.layout.list,listitems);
        ListView lv = (ListView)findViewById(R.id.g_list);
        lv.setAdapter(adapter);
        lv.setClickable(false);



        Snackbar.make(findViewById(R.id.glistContainer), "Grocery List -- tap + to add an item!", Snackbar.LENGTH_LONG).show();

    }

    private void populateList() {
        //add stuff to the list here!
        String one="Bread";
        String two="Orange Juice";
        String three="Bananas";
        listitems.add(one);
        listitems.add(three);
        listitems.add(two);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void showDialog(){
        DialogFragment dialog = new AddgListItemDialogFragment();
        dialog.show(getFragmentManager(),"Tag");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d("MyPantry","add tapped\n");
        Dialog dialogview= dialog.getDialog();
        EditText edit = (EditText) dialogview.findViewById(R.id.dialog_new_item);
        if(edit!=null){
            String txt = edit.getText().toString();
            if(txt.equals(""))
                return;
            if(listitems.contains(txt)){
                Snackbar.make(findViewById(R.id.glistContainer),"Item is already in List!",Snackbar.LENGTH_LONG).show();
                return;
            }
            listitems.add(txt);
            adapter.notifyDataSetChanged();
            Snackbar.make(findViewById(R.id.glistContainer),"Added item: "+txt,Snackbar.LENGTH_LONG).show();
        }else{
            Log.d("MyPantry","Edittext no found!");
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
