package football.focus.footfragments.fixtures;

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


public class FixtureFragment extends Fragment {

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }
    public static RecyclerView mRecyclerView;
    public static ArrayList<Fixture> fixtureList = new ArrayList<Fixture>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_fixture, null);

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), llm.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        fixtureList.clear();
        loadData();

        return fragmentView;
    }

    private void loadData()
    {
        SaveData sd = new SaveData();
        sd.read("fixturesFile", getActivity().getApplicationContext());
        try {
            JSONArray response = new JSONArray(sd.content);
            for(int i=0; i < response.length(); i++) {
                JSONObject jsonobject = response.getJSONObject(i);
                String day = jsonobject.getString("day");
                String date = jsonobject.getString("date");
                String opp = jsonobject.getString("opponent");
                String score = jsonobject.getString("score");
                String scoreO = jsonobject.getString("scoreO");
                String homeAway = jsonobject.getString("homeaway");
                Fixture f = new Fixture(day,date,opp,score,scoreO, homeAway, opponentLogo(opp));
                fixtureList.add(f);
            }
            FixtureAdapter nAdapter = new FixtureAdapter(fixtureList);
            mRecyclerView.setAdapter(nAdapter);
        } catch (JSONException ex) {
            Log.e("App", "Failure", ex);
        }
    }

    private int opponentLogo(String opp)
    {
        if (opp.equals("Stenhousemuir"))
            return R.drawable.club_stenhousemuir;
        if (opp.equals("Ayr United"))
            return R.drawable.club_ayr;
        if (opp.equals("Greenock Morton"))
            return R.drawable.club_greenock;
        if (opp.equals("Edinburgh City"))
            return R.drawable.club_edinburgh;
        if (opp.equals("Peterhead"))
            return R.drawable.club_peterhead;
        if (opp.equals("Elgin City"))
            return R.drawable.club_elgin;
        if (opp.substring(0,5).equals("Queen"))
            return R.drawable.club_queenspark;
        if (opp.equals("Berwick Rangers"))
            return R.drawable.club_berwickrangers;
        if (opp.equals("Clyde"))
            return R.drawable.club_clyde;
        if (opp.equals("Cowdenbeath"))
            return R.drawable.club_cowdenbeath;
        if (opp.equals("Stirling Albion"))
            return R.drawable.club_stirling;
        if (opp.equals("Annan Athletic"))
            return R.drawable.club_annan;
        if (opp.equals("Formartine United"))
            return R.drawable.club_fortmarine;
        if (opp.equals("Partick Thistle"))
            return R.drawable.club_patrick;

        return 0;
    }

}
