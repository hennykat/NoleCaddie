package com.syntacticsugar.nolecaddie.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.syntacticsugar.nolecaddie.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.syntacticsugar.nolecaddie.model.AppConfig.FIRST_COLUMN;
import static com.syntacticsugar.nolecaddie.model.AppConfig.SECOND_COLUMN;
import static com.syntacticsugar.nolecaddie.model.AppConfig.THIRD_COLUMN;

/**
 * Created by Dalton on 7/10/2015.
 * Edited by Sam on 7/16/2015
 * Updated by henny 2018
 */
public class ScoreTab extends Activity {

    public static ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ListViewAdapters adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);


        // fill up listview
        ListView listView = findViewById(R.id.listView1);
        adapter = new ListViewAdapters(this, list);
        listView.setAdapter(adapter);
    }//end onCreate

    public void scoreInsert(String holeNum, String parNum, String userScore) {

        HashMap<String, String> temp1 = new HashMap<String, String>();
        temp1.put(FIRST_COLUMN, holeNum);
        temp1.put(SECOND_COLUMN, parNum);
        temp1.put(THIRD_COLUMN, userScore);
        list.add(temp1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scoreInsert(Integer.toString(MainTab.currentHole), MainTab.currentPar, Integer.toString(MainTab.currentStroke));
        adapter.notifyDataSetChanged();
    }

    public void nextHole(View view) {

        if (++MainTab.currentHole == 19) MainTab.currentHole = 1;
        MainTab.currentStroke = 1;
        Intent intent = new Intent(this, MainTab.class);
        startActivity(intent);
    }

    public void gotoMenu(View view) {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
