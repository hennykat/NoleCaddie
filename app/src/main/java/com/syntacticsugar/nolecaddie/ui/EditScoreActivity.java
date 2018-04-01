package com.syntacticsugar.nolecaddie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.syntacticsugar.nolecaddie.R;

/**
 * Created by sam on 7/26/15.
 * Updated by henny 2018
 */
public class EditScoreActivity extends AppCompatActivity {

    private TextView strokeTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_score);

        strokeTextView = findViewById(R.id.edit_score_stroke_textview);
        strokeTextView.setText(String.valueOf(MainActivity.currentStroke));

        final Button upButton = findViewById(R.id.edit_score_up_button);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upScore();
            }
        });

        final Button downButton = findViewById(R.id.edit_score_down_button);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downScore();
            }
        });

        final Button finishButton = findViewById(R.id.edit_score_finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishHole();
            }
        });
    }

    private void upScore() {
        ++MainActivity.currentStroke; // TODO: DESTROY this
        strokeTextView.setText(String.valueOf(MainActivity.currentStroke));
    }

    private void downScore() {
        --MainActivity.currentStroke; // TODO: DESTROY this
        strokeTextView.setText(String.valueOf(MainActivity.currentStroke));
    }

    private void finishHole() {
        Intent intent = new Intent(this, ScorecardActivity.class);
        startActivity(intent);
    }
}
