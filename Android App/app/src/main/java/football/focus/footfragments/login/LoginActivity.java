package football.focus.footfragments.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import football.focus.footfragments.MainActivity;
import football.focus.footfragments.R;
import football.focus.footfragments.SplashScreen;
import football.focus.footfragments.Util.GetData;
import football.focus.footfragments.Util.NetworkUtil;
import football.focus.footfragments.Util.SaveData;

public class LoginActivity extends AppCompatActivity {

    public TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Albion Rovers F.C.");
        toolbar.setTitleTextColor(Color.rgb(155,39,0));
        setSupportActionBar(toolbar);

        Button loginButton = findViewById(R.id.loginButton);
        Button regButton = findViewById(R.id.toRegButton);
        error = findViewById(R.id.errorMessageView);
        final EditText email = findViewById(R.id.emailView);
        final EditText pass = findViewById(R.id.passView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (NetworkUtil.getConnectivityStatus(LoginActivity.this) == 0)
                {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("No internet")
                            .setMessage("An internet connection is required to login.")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
                else {
                    if (checkFields(email, pass)) {
                        String req = "https://sedhna.com/rovers/php/login.php?email=" + email.getText().toString().trim() + "&pass=" + pass.getText().toString().trim();
                        new getData().execute(req);
                    } else {
                        Toast.makeText(LoginActivity.this, "Please fill out both fields.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private Boolean checkFields(EditText email, EditText pass)
    {

        if(TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(pass.getText()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private class getData extends AsyncTask<String, Void, JSONObject>
    {

        @Override
        protected JSONObject doInBackground(String... params)
        {
            String str = params[0];
            //String str="http://your.domain.here/yourSubMethod";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                //return new JSONObject(stringBuffer.toString());
                return new JSONObject(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonobject)
        {
            if(jsonobject != null)
            {
                try {
                        String status = jsonobject.getString("status");
                        String subtype = jsonobject.getString("subtype");

                        if (status.equals("OK"))
                        {
                            new GetData(LoginActivity.this).execute("https://www.sedhna.com/rovers/api/news", "newsFile");
                            new GetData(LoginActivity.this).execute("https://www.sedhna.com/rovers/api/players", "playersFile");
                            new GetData(LoginActivity.this).execute("https://www.sedhna.com/rovers/api/fixtures", "fixturesFile");
                            String token = jsonobject.getString("token");
                            SaveData.write("token", token, LoginActivity.this);
                            SaveData.write("subtype", subtype, LoginActivity.this);
                            SaveData.write("user", jsonobject.toString(), LoginActivity.this);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            String message = jsonobject.getString("message");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            error.setText(message);
                        }

                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }

}
