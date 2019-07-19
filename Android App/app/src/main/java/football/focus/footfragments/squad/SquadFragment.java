package football.focus.footfragments.squad;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import football.focus.footfragments.R;
import football.focus.footfragments.Util.SaveData;
import football.focus.footfragments.news.NewsFragment;


public class SquadFragment extends Fragment {

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }
    public static RecyclerView mRecyclerView;
    public static ArrayList<SquadMember> squadList = new ArrayList<SquadMember>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_squad, null);

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), llm.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        squadList.clear();
        loadData();

        return fragmentView;
    }

    private void loadData()
    {
        SaveData sd = new SaveData();
        sd.read("playersFile", getActivity().getApplicationContext());
        try {
            JSONArray response = new JSONArray(sd.content);
            for(int i=0; i < response.length(); i++) {
                JSONObject jsonobject = response.getJSONObject(i);
                String name = jsonobject.getString("name");
                String pos = jsonobject.getString("position");
                String imgUrl = jsonobject.getString("picture");
                SquadMember s = new SquadMember(name, pos, R.drawable.p1, imgUrl);
                squadList.add(s);
            }
            SquadAdapter nAdapter = new SquadAdapter(squadList);
            mRecyclerView.setAdapter(nAdapter);
        } catch (JSONException ex) {
            Log.e("App", "Failure", ex);
        }
    }

}
