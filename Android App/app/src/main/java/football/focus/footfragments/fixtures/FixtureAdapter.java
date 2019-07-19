package football.focus.footfragments.fixtures;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import football.focus.footfragments.R;
import football.focus.footfragments.squad.SquadMember;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureHolder> {

    private final List<Fixture> fixtures;

    public FixtureAdapter(ArrayList fixtures) {
        this.fixtures = fixtures;
    }

    @Override
    public FixtureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FixtureHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fixturelist, parent, false));
    }

    @Override
    public void onBindViewHolder(FixtureHolder holder, int position) {
        holder.date.setText(fixtures.get(position).getDate());
        if (fixtures.get(position).getHomeAway().equals("Home"))
        {
            holder.albionText.setText("Albion Rovers");
            holder.albionScore.setText(fixtures.get(position).getScore());
            holder.opponentScore.setText(fixtures.get(position).getScoreO());
            holder.opponentText.setText(fixtures.get(position).getOpponent());
            holder.opponentLogo.setImageResource(fixtures.get(position).getOpponentLogo());
            holder.albionLogo.setImageResource(R.drawable.logo);
        }
        else
        {
            holder.albionText.setText(fixtures.get(position).getOpponent());
            holder.albionScore.setText(fixtures.get(position).getScoreO());
            holder.opponentScore.setText(fixtures.get(position).getScore());
            holder.opponentText.setText("Albion Rovers");
            holder.opponentLogo.setImageResource(R.drawable.logo);
            holder.albionLogo.setImageResource(fixtures.get(position).getOpponentLogo());
        }
    }

    @Override
    public int getItemCount() {
        return fixtures != null ? fixtures.size() : 0;
    }

}

class FixtureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView date;
    public TextView albionText;
    public TextView albionScore;
    public TextView opponentScore;
    public TextView opponentText;
    public ImageView opponentLogo;
    public ImageView albionLogo;

    public FixtureHolder(View itemView) {
        super(itemView);
        date = (TextView) itemView.findViewById(R.id.fixtureDate);
        albionText = (TextView) itemView.findViewById(R.id.albionText);
        albionScore = (TextView) itemView.findViewById(R.id.albionScore);
        opponentScore = (TextView) itemView.findViewById(R.id.opponentScore);
        opponentText = (TextView) itemView.findViewById(R.id.opponentText);
        opponentLogo = (ImageView) itemView.findViewById(R.id.opponentLogoView);
        albionLogo = (ImageView) itemView.findViewById(R.id.albionLogoView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
 
    }
}
