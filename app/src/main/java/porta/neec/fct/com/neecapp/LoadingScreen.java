package porta.neec.fct.com.neecapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class LoadingScreen extends AppCompatActivity {


    private static int SPLASH_TIME_OUT;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String MY_PREFS_NAME = "dbneec";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String loaded = prefs.getString("load", "Human Habitat");


        if (loaded.contains("NEEC SOM")) {
            SPLASH_TIME_OUT = 2300;
            MediaPlayer mp = MediaPlayer.create(this, R.raw.pad_confirm);
            mp.start();

        } else if (loaded.contains("ESCAPE POD")) {
            SPLASH_TIME_OUT = 5000;
            MediaPlayer mp = MediaPlayer.create(this, R.raw.glados_escapepod);
            mp.start();

        } else if (loaded.contains("ELETRIC DOOR")) {
            SPLASH_TIME_OUT = 1300;
            MediaPlayer mp = MediaPlayer.create(this, R.raw.electric_door);
            mp.start();

        } else if (loaded.contains("Human Habitat")) {
            SPLASH_TIME_OUT = 4000;
            MediaPlayer mp = MediaPlayer.create(this, R.raw.glados_human_habitath);
            mp.start();

        } else {
            SPLASH_TIME_OUT = 1500;
        }


        setContentView(R.layout.activity_home_actibity);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LoadingScreen.this, LoginActivity.class);
                startActivity(homeIntent);
                this.finish();
            }

            protected void finish() {

            }
        }, SPLASH_TIME_OUT);


    }
}
