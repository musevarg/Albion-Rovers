package football.focus.footfragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import football.focus.footfragments.Util.GetData;
import football.focus.footfragments.Util.SaveData;
import football.focus.footfragments.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new GetData(SplashScreen.this).execute("https://www.sedhna.com/rovers/api/news", "newsFile");
        new GetData(SplashScreen.this).execute("https://www.sedhna.com/rovers/api/players", "playersFile");
        new GetData(SplashScreen.this).execute("https://www.sedhna.com/rovers/api/fixtures", "fixturesFile");

        //Call the Logo Screen Activity
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SaveData sd = new SaveData();
                sd.read("token",SplashScreen.this);
                String tok = sd.content;
                //Log.d("TOK", tok);
                if(tok == null) {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Log.d("TOK", tok);
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}
