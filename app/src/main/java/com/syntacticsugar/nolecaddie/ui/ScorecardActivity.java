package com.syntacticsugar.nolecaddie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.syntacticsugar.nolecaddie.R;
import com.syntacticsugar.nolecaddie.model.Hole;
import com.syntacticsugar.nolecaddie.storage.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 7/10/2015.
 * Edited by Sam on 7/16/2015
 * Updated by henny 2018
 */
public class ScorecardActivity extends AppCompatActivity {

    public static final String TAG = ScorecardActivity.class.getSimpleName();

    private Storage storage;
    private int currentHole;
    // UI
    private ScorecardAdapter adapter;
    private List<ScorecardItem> itemList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        // init global vars
        this.storage = new Storage(this);
        this.currentHole = storage.loadCurrentHole();
        this.adapter = new ScorecardAdapter(this, R.layout.item_scorecard, itemList);

        // setup UI
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
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateList();
    }

    private void updateList() {

        itemList.clear();
        adapter.notifyDataSetChanged();

        List<Hole> holeList = storage.loadScoreList();
        if (holeList == null || holeList.isEmpty()) {
            Log.w(TAG, "failed to update list, invalid hole list");
            return;
        }

        for (Hole hole : holeList) {
            scoreInsert(hole);
        }

        adapter.notifyDataSetChanged();
    }

    private void scoreInsert(Hole hole) {

        if (hole == null) {
            Log.w(TAG, "failed to insert score, invalid hole");
            return;
        }

        String holeNum = String.valueOf(hole.getIndex());
        String par = String.valueOf(hole.getPar());
        String userScore = "";
        Integer score = hole.getScore();
        if (score != null) {
            userScore = String.valueOf(score);
        }

        ScorecardItem item = new ScorecardItem(holeNum, par, userScore);
        itemList.add(item);
    }

    private void nextHole() {

        // update current hole
        ++currentHole;
        if (currentHole >= 19) {
            // TODO: show start of new game
            // TODO: clear scorecard
            currentHole = 1;
        }
        storage.saveCurrentHole(currentHole);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
