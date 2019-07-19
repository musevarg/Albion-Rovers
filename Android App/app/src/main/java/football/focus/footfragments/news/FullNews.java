package football.focus.footfragments.news;

import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Collections;

import football.focus.footfragments.R;
import football.focus.footfragments.Util.SaveData;

import static football.focus.footfragments.news.NewsFragment.newsList;

public class FullNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullnews);
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

        int id = getIntent().getExtras().getInt("NEWS_ID");
        loadData(id);
    }

    private void loadData(int id)
    {
        TextView title = findViewById(R.id.newsTitleText);
        TextView date = findViewById(R.id.newsDateText);
        TextView content = findViewById(R.id.newsSummaryText);
        ImageView picture = findViewById(R.id.newspic);

        SaveData sd = new SaveData();
        sd.read("newsFile", FullNews.this);
        try {
            JSONArray response = new JSONArray(sd.content);
            JSONArray fResponse = new JSONArray();

            for(int i=(response.length()-1); i > -1; i--) {
                fResponse.put(response.get(i));
            }

                JSONObject jsonobject = fResponse.getJSONObject(id);
                String datePosted = jsonobject.getString("datePosted");
                String titleText = jsonobject.getString("headline");
                String summary = jsonobject.getString("summary");
                String imgUrl = jsonobject.getString("picture");

                title.setText(titleText);
                date.setText(datePosted);
                content.setText(summary);
                Picasso.get()
                    .load(imgUrl)
                    .into(picture);

        } catch (JSONException ex) {
            Log.e("App", "Failure", ex);
        }
    }

}
