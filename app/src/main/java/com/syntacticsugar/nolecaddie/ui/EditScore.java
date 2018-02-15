package com.syntacticsugar.nolecaddie.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import syntacticsugar.nolecaddie.R;

/**
 * Created by sam on 7/26/15.
 * Updated by henny 2018
 */
public class EditScore extends Activity{

    TextView strokeText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        strokeText = (TextView) findViewById(R.id.editStrokesText);
        strokeText.setText(String.valueOf(MainTab.currentStroke));
    }

    public void finishHole(View view) {
        Intent intent = new Intent(this,ScoreTab.class);
        startActivity(intent);
    }

    public void upScore(View view) {
        ++MainTab.currentStroke;
        strokeText.setText(String.valueOf(MainTab.currentStroke));
    }

    public void downScore(View view) {
        --MainTab.currentStroke;
        strokeText.setText(String.valueOf(MainTab.currentStroke));
    }
}
