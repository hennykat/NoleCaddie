package com.syntacticsugar.nolecaddie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.syntacticsugar.nolecaddie.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.syntacticsugar.nolecaddie.config.AppConfig.FIRST_COLUMN;
import static com.syntacticsugar.nolecaddie.config.AppConfig.SECOND_COLUMN;
import static com.syntacticsugar.nolecaddie.config.AppConfig.THIRD_COLUMN;

/**
 * Created by Dalton on 7/10/2015.
 * Edited by Sam on 7/16/2015
 * Updated by henny 2018
 */
public class ScorecardActivity extends AppCompatActivity {

    public static ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ListViewAdapters adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        final Button nextButton = findViewById(R.id.scorecard_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextHole();
            }
        });
        final Button menuButton = findViewById(R.id.scorecard_menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });

        // fill up listview
        ListView listView = findViewById(R.id.scorecard_score_listview);
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
        scoreInsert(Integer.toString(MainActivity.currentHole), MainActivity.currentPar, Integer.toString(MainActivity.currentStroke));
        adapter.notifyDataSetChanged();
    }

    private void nextHole() {

        if (++MainActivity.currentHole == 19) MainActivity.currentHole = 1;
        MainActivity.currentStroke = 1;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToMenu() {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
