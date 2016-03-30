package edu.pitt.cs.cs1635.mypantry;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Pantry extends BaseActivity {

    Button btn1;
    Button btn2;
    Button btn3;

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
        btn1 = (Button) findViewById(R.id.amt1);
        btn2 = (Button) findViewById(R.id.amt2);
        btn3 = (Button) findViewById(R.id.button);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn1.getText().equals("High")){
                    btn1.setText("Low");
                    btn1.setTextColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.item1)).setTextColor(Color.YELLOW);
                }
                else if(btn1.getText().equals("Low")){
                    btn1.setText("Out");
                    btn1.setTextColor(Color.RED);
                    ((TextView)findViewById(R.id.item1)).setTextColor(Color.RED);
                }
                else if(btn1.getText().equals("Out")){
                    btn1.setText("High");
                    btn1.setTextColor(Color.BLACK);
                    ((TextView)findViewById(R.id.item1)).setTextColor(Color.BLACK);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn2.getText().equals("High")){
                    btn2.setText("Low");
                    btn2.setTextColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.item2)).setTextColor(Color.YELLOW);
                }
                else if(btn2.getText().equals("Low")){
                    btn2.setText("Out");
                    btn2.setTextColor(Color.RED);
                    ((TextView)findViewById(R.id.item2)).setTextColor(Color.RED);
                }
                else if(btn2.getText().equals("Out")){
                    btn2.setText("High");
                    btn2.setTextColor(Color.BLACK);
                    ((TextView)findViewById(R.id.item2)).setTextColor(Color.BLACK);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn3.getText().equals("High")){
                    btn3.setText("Low");
                    btn3.setTextColor(Color.YELLOW);
                    ((TextView)findViewById(R.id.textView)).setTextColor(Color.YELLOW);
                }
                else if(btn3.getText().equals("Low")){
                    btn3.setText("Out");
                    btn3.setTextColor(Color.RED);
                    ((TextView)findViewById(R.id.textView)).setTextColor(Color.RED);
                }
                else if(btn3.getText().equals("Out")){
                    btn3.setText("High");
                    btn3.setTextColor(Color.BLACK);
                    ((TextView)findViewById(R.id.textView)).setTextColor(Color.BLACK);
                }
            }
        });
    }

    @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    //     // Inflate the menu; this adds items to the action bar if it is present.
    //     getMenuInflater().inflate(R.menu.menu_pantry, menu);
    //     return true;
    // }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){
            View third = (View)findViewById(R.id.view);
            third.setVisibility(View.VISIBLE);
        }
    }

}
