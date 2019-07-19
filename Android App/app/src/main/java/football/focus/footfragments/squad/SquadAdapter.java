package football.focus.footfragments.squad;

import android.app.AlertDialog;
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
import football.focus.footfragments.Util.SaveData;

public class SquadAdapter extends RecyclerView.Adapter<SquadHolder> {

    private final List<SquadMember> mUsers;

    public SquadAdapter(ArrayList players) {
        mUsers = players;
    }

    @Override
    public SquadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SquadHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.squadlist, parent, false));
    }

    @Override
    public void onBindViewHolder(SquadHolder holder, int position) {
        holder.pName.setText(mUsers.get(position).getName());
        holder.pPos.setText(mUsers.get(position).getPos());
        //holder.pImg.setImageResource(mUsers.get(position).getPic());
        Picasso.get()
                .load(mUsers.get(position).getImgurl())
                .into(holder.pImg);
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

}

class SquadHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView pName;
    public TextView pPos;
    public ImageView pImg;

    public SquadHolder(View itemView)  {
        super(itemView);
        pName = (TextView) itemView.findViewById(R.id.playerNameText);
        pPos = (TextView) itemView.findViewById(R.id.playerPositionText);
        pImg = (ImageView) itemView.findViewById(R.id.playerpic);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Context c = view.getContext();

        SaveData sd = new SaveData();
        sd.read("subtype",c);

        if (sd.content.equals("Full"))
        {
            int pos = getLayoutPosition();
            Intent intent = new Intent(c, FullPlayer.class);
            intent.putExtra("PLAYER_ID", pos);
            c.startActivity(intent);
        }
        else
        {
            new AlertDialog.Builder(c)
                    .setTitle("Partial Subscription")
                    .setMessage("Only users with a full subscription can see the full player details.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }

    }
}

