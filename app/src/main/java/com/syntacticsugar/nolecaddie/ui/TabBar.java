package com.syntacticsugar.nolecaddie.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import syntacticsugar.nolecaddie.R;

/**
 * Created by Dalton on 7/6/2015.
 * Updated by henny 2018
 */
public class TabBar extends TabActivity implements TabHost.OnTabChangeListener {

    TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_bar);

        // Get TabHost Reference
        tabHost = getTabHost();

        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);

        TabHost.TabSpec spec;
        Intent intent;

        // TAB 1
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, MainTab.class);
        spec = tabHost.newTabSpec("First").setIndicator("Game")
                .setContent(intent);

        //Add intent to tab
        tabHost.addTab(spec);

        // TAB 2
        intent = new Intent().setClass(this, ScoreTab.class);
        spec = tabHost.newTabSpec("Second").setIndicator("Score")
                .setContent(intent);
        tabHost.addTab(spec);

    }//end onCreate

    @Override
    public void onTabChanged(String tabId) {

        /************ Called when tab changed *************/

        //********* Check current selected tab and change according images *******/

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            if (i==0) {
                tabHost.getTabWidget().getChildAt(i);
            }
            else if (i==1) {
                tabHost.getTabWidget().getChildAt(i);
            }
        }//end for


        Log.i("tabs", "CurrentTab: " + tabHost.getCurrentTab());

        if(tabHost.getCurrentTab()==0) {
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
        }
        else if(tabHost.getCurrentTab()==1) {
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
        }
    }//end onTabChanged
}//end class
