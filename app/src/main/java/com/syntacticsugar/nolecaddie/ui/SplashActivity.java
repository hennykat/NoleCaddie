package com.syntacticsugar.nolecaddie.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import syntacticsugar.nolecaddie.R;

/**
 * Created by Dalton on 7/10/2015.
 * Updated by henny 2018
 */
public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //pause for a moment
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);
    }

}
