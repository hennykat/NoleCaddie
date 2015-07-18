package syntacticsugar.golfproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

/**
 * Created by Dalton on 7/6/2015.
 * Edited by Sam on 7/10/2015
 */
public class MainTab extends Activity  {

    int[] parArray = {2,3,3,3,3,3,3,2,4,3,3,2,3,3,2,2,2,2};

    public static int currentStroke = 1;
    public static int currentHole = 1;
    public static String currentPar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }//end onCreate

    @Override
    protected void onResume() {
        super.onResume();

        currentPar = checkPar(currentHole);
    }

    public void markStroke(View view) {

        //display in short period of time
        Toast.makeText(getApplicationContext(), "Throw Marked.",
                Toast.LENGTH_SHORT).show();
        ++currentStroke;
    }

    public String checkPar(int hole) {
        String par = Integer.toString(parArray[(hole-1)]);
        return par;
    }

    public void gotoMenu(View view) {
        Intent intent = new Intent(this,MenuScreen.class);
        startActivity(intent);
    }

    public void finishHole(View view) {

        Intent intent = new Intent(this,ScoreTab.class);
        startActivity(intent);
    }


}//end class
