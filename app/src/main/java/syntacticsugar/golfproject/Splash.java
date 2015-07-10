package syntacticsugar.golfproject;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dalton on 7/10/2015.
 */
public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //pause for a moment
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, MenuScreen.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, 2000);

    }//end onCreate

}//end class
