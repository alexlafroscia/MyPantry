package edu.pitt.cs.cs1635.mypantry;

import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.widget.EditText;

import java.util.List;

import edu.pitt.cs.cs1635.mypantry.adapters.GListItemAdapter;
import edu.pitt.cs.cs1635.mypantry.model.Item;
import edu.pitt.cs.cs1635.mypantry.model.ItemDao;


public class GroceryListActivity extends BaseActivity implements AddgListItemDialogFragment.AddListItemDialogListener {

    GListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

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

        notifyUser("Grocery List -- tap + to add an item!");
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.g_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new GListItemAdapter(this.itemDao);
        recyclerView.setAdapter(adapter);
    }

    public void showDialog(){
        DialogFragment dialog = new AddgListItemDialogFragment();
        dialog.show(getFragmentManager(),"Tag");
    }

    /**
     * Show a SnackBar to notify the user that some event took place
     * @param message the message to display
     */
    public void notifyUser(String message) {
        Snackbar.make(findViewById(R.id.glistContainer), message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d("MyPantry","add tapped\n");
        Dialog dialogview= dialog.getDialog();
        EditText edit = (EditText) dialogview.findViewById(R.id.dialog_new_item);
        if(edit!=null){
            String txt = edit.getText().toString();
            if(txt.equals("")) {
                return;
            }
            List<Item> matchingItems = this.itemDao.queryBuilder()
                    .where(ItemDao.Properties.Title.eq(txt))
                    .list();
            if (matchingItems.size() > 0) {
                Item item = matchingItems.get(0);
                if (item.getOnGroceryList()) {
                    notifyUser("Item is already in list!");
                } else {
                    item.setOnGroceryList(true);
                    this.itemDao.update(item);
                    adapter.notifyDataSetChanged();
                    notifyUser("Added item: " + txt);
                }
            } else {
                notifyUser("Could not find an item called " + txt);
            }
        }else{
            Log.d("MyPantry", "Edittext no found!");
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
