package football.focus.footfragments.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import football.focus.footfragments.R;
import football.focus.footfragments.Util.SaveData;

public class NewsFragment extends Fragment {

    public static  ArrayList<News> newsList = new ArrayList<News>();
    public static RecyclerView mRecyclerView;
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.news_fragment, null);

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView2);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        newsList.clear();
        //new getData().execute("http://www.sedhna.com/rovers/api/news");
        loadData();

        return fragmentView;
    }

    private void loadData()
    {
        //Read JSON string from internal storage.
        SaveData sd = new SaveData();
        sd.read("newsFile", getActivity().getApplicationContext());
        //Parse JSON string and create objects
        try {
            JSONArray response = new JSONArray(sd.content); //Parse JSON Array
            for(int i=(response.length()-1); i > -1; i--) {
                JSONObject jsonobject = response.getJSONObject(i); //Parse JSON object

                // Get content from the parsed object using variables name
                String datePosted = jsonobject.getString("datePosted");
                String title = jsonobject.getString("headline");
                String summary = jsonobject.getString("summary");
                String imgUrl = jsonobject.getString("picture");

                //Create News Object and add it to an array of News objects
                News n = new News(imgUrl, title, datePosted, summary);
                newsList.add(n);
            }
            //Pass the list of News Object to the NewsAdapter and inflate view in each element of the recyclerview
            NewsAdapter nAdapter = new NewsAdapter(newsList);
            mRecyclerView.setAdapter(nAdapter);
        } catch (JSONException ex) {
            Log.e("App", "Failure", ex);
        }
    }
}
