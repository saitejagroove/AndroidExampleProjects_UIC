package com.example.cs478proj3app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toolbar;

public class Main2Activity extends AppCompatActivity  implements BaseList2Frag.Namelistener2 {

    MyReceiver receiver = new MyReceiver();
    WebView websitedetails;
    Integer itempos;
    Boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        configureReceiver();

        websitedetails= (WebView) findViewById(R.id.webView2);
        if(savedInstanceState!=null){
            flag= Boolean.TRUE;
            itempos= savedInstanceState.getInt("itempos");
            onNameSelected(itempos);
        }else{
            {
                FragmentManager manager = this.getSupportFragmentManager();
                manager.beginTransaction()
                        .hide(manager.findFragmentById(R.id.detail2Frag))
                        .show(manager.findFragmentById(R.id.base2Frag))
                        .commit();
            }

            if(findViewById(R.id.layout_land)!=null)
            {
                FragmentManager manager = this.getSupportFragmentManager();
                manager.beginTransaction()
                        .hide(manager.findFragmentById(R.id.detail2Frag))
                        .show(manager.findFragmentById(R.id.base2Frag))
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 0, "Attractions");
        menu.add(0, 1, 0, "Restaurants");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        if (item.getTitle() == "Attractions") {
            Intent i = new Intent(this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(i);
        }else if(item.getTitle() == "Restaurants"){
            Intent i = new Intent(this,Main2Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(i);
        }
        else {
            return  false;
        }
        return true;
    }

    private void configureReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.cs478.app2");
        registerReceiver(receiver, filter);


    }
    // to do: 1. saveconfigurationchanges

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(itempos!=null){
            outState.putInt("itempos",itempos);
        }
    }

    @Override
    public void onNameSelected(int index) {
        if(flag){
            if(findViewById(R.id.layout2_default)!=null){
                FragmentManager manager = this.getSupportFragmentManager();
                manager.beginTransaction()
                        .show(manager.findFragmentById(R.id.detail2Frag))
                        .hide(manager.findFragmentById(R.id.base2Frag))
                        .commit();
            }
            if(findViewById(R.id.layout2_land)!=null){
                FragmentManager manager = this.getSupportFragmentManager();
                manager.beginTransaction()
                        .show(manager.findFragmentById(R.id.detail2Frag))
                        .show(manager.findFragmentById(R.id.base2Frag))
                        .commit();
            }
        }else{
            if(findViewById(R.id.layout2_default)!=null){
                FragmentManager manager = this.getSupportFragmentManager();
                manager.beginTransaction()
                        .show(manager.findFragmentById(R.id.detail2Frag))
                        .hide(manager.findFragmentById(R.id.base2Frag))
                        .addToBackStack(null)
                        .commit();
            }
            if(findViewById(R.id.layout2_land)!=null){
                FragmentManager manager = this.getSupportFragmentManager();
                manager.beginTransaction()
                        .show(manager.findFragmentById(R.id.detail2Frag))
                        .show(manager.findFragmentById(R.id.base2Frag))
                        .addToBackStack(null)
                        .commit();
            }
        }


        itempos=index;
        String [] websites2 = getResources().getStringArray(R.array.reswebsites);
        websitedetails.setWebViewClient(new WebViewClient());
        websitedetails.loadUrl(websites2[index]);

    }
}
