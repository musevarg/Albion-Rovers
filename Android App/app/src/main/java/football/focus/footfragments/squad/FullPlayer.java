package football.focus.footfragments.squad;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import football.focus.footfragments.R;
import football.focus.footfragments.Util.SaveData;

public class FullPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullplayer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Albion Rovers F.C.");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int id = getIntent().getExtras().getInt("PLAYER_ID");
        loadData(id);

    }

    private void loadData(int id)
    {
        TextView name = findViewById(R.id.playerNameText);
        TextView dob = findViewById(R.id.playerDob);
        TextView pob = findViewById(R.id.playerPob);
        TextView height = findViewById(R.id.playerHeight);
        TextView position = findViewById(R.id.playerPosition);
        TextView nationality = findViewById(R.id.playerNationality);
        TextView joined = findViewById(R.id.playerJoined);
        TextView contract = findViewById(R.id.playerContract);
        ImageView picture = findViewById(R.id.playerPic);

        SaveData sd = new SaveData();
        sd.read("playersFile", FullPlayer.this);
        try {
            JSONArray response = new JSONArray(sd.content);

            JSONObject jsonobject = response.getJSONObject(id);
            String nam = jsonobject.getString("name");
            String dobS = jsonobject.getString("dob");
            String pobS = jsonobject.getString("pob");
            String heigh = jsonobject.getString("height");
            String pos = jsonobject.getString("position");
            String nat = jsonobject.getString("nationality");
            String join = jsonobject.getString("joined");
            String contrct = jsonobject.getString("contract");
            String imgUrl = jsonobject.getString("picture");

            name.setText(nam);
            dob.setText(dobS);
            pob.setText(pobS);
            height.setText(heigh);
            position.setText(pos);
            nationality.setText(nat);
            joined.setText(join);
            contract.setText(contrct);

            Picasso.get()
                    .load(imgUrl)
                    .into(picture);

        } catch (JSONException ex) {
            Log.e("App", "Failure", ex);
        }
    }

}
