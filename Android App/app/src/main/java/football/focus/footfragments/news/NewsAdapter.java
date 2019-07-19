package football.focus.footfragments.news;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import football.focus.footfragments.R;
import football.focus.footfragments.Util.SaveData;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    private final List<News> newsList;

    public NewsAdapter(ArrayList news) {
        newsList = news;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View newsView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newslist, parent, false);

        return new NewsHolder(newsView);

    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.nTitle.setText(newsList.get(position).getNewsTitle());
        holder.nDate.setText(newsList.get(position).getDate());
        if(newsList.get(position).getSummary().length()>128) {
            holder.nSummary.setText(newsList.get(position).getSummary().substring(0, 128) + "...");
        }
        else
        {
            holder.nSummary.setText(newsList.get(position).getSummary());
        }

        Picasso.get()
                .load(newsList.get(position).getImgUrl())
                .into(holder.nImg);

        //holder.nImg.setImageResource(newsList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return newsList != null ? newsList.size() : 0;
    }

}

class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView nTitle;
    public TextView nDate;
    public ImageView nImg;
    public TextView nSummary;

    public NewsHolder(View itemView) {
        super(itemView);
        nTitle = (TextView) itemView.findViewById(R.id.newsTitleText);
        nDate = (TextView) itemView.findViewById(R.id.newsDateText);
        nImg = (ImageView) itemView.findViewById(R.id.newspic);
        nSummary = (TextView) itemView.findViewById(R.id.newsSummaryText);
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
            Intent intent = new Intent(c, FullNews.class);
            intent.putExtra("NEWS_ID", pos);
            c.startActivity(intent);
        }
        else
        {
            new AlertDialog.Builder(c)
                    .setTitle("Partial Subscription")
                    .setMessage("Only users with a full subscription can see the full news.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.ok, null)
                    /*.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    })*/

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    //.setNegativeButton(android.R.string.no, null)
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


    }
}
