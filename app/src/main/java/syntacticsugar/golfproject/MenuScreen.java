package syntacticsugar.golfproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Dalton on 7/6/2015.
 * Edited by Sam on 7/16/2015
 */
public class MenuScreen extends Activity {

    RelativeLayout mRelativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.mRelativeLayout);

        weatherType(2);

    }//end onCreate

    // set weather type screen on main menu
    public void weatherType(int weather ) {

        // temp ** delete from code
        weather = (int) ( Math.random() * 3 + 0);
        // temp ** ^^^^^^

        switch(weather){
            case 0:  // sunny
                mRelativeLayout.setBackgroundResource(R.drawable.screensunny);
                break;
            case 1:  // rainy
                mRelativeLayout.setBackgroundResource(R.drawable.screenrain);
                break;
            case 2:default:  // partly cloudy
                mRelativeLayout.setBackgroundResource(R.drawable.screenpartly);
                break;
        }

    }

    public void startGame(View view) {
       /*
        Intent intent = new Intent(this,MainTab.class);
        startActivity(intent);
        */

        Intent mainIntent = new Intent(MenuScreen.this, MainTab.class);
        MenuScreen.this.startActivity(mainIntent);
        MenuScreen.this.finish();

    }


}//end class
