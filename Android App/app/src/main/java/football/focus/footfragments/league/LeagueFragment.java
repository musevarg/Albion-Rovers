package football.focus.footfragments.league;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import football.focus.footfragments.R;


public class LeagueFragment extends Fragment {

    public static LeagueFragment newInstance() {
        return new LeagueFragment();
    }

    WebView webView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_league, null);

        webView = fragmentView.findViewById(R.id.webView);
        webView.loadUrl("https://www.bbc.co.uk/sport/football/teams/albion-rovers/table#page");

        return fragmentView;
    }

}