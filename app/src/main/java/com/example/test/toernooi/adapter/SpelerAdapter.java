package com.example.test.toernooi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.toernooi.R;
import com.example.test.toernooi.activity.SpelerDetailsActivity;
import com.example.test.toernooi.model.Speler;

import java.util.List;

/**
 * Created by Melanie on 15-10-2017.
 */

public class SpelerAdapter extends RecyclerView.Adapter<SpelerAdapter.ViewHolder> {
    final Context context;
    private final List<Speler> spelerList;

    public SpelerAdapter(List<Speler> list, Context context) {
        spelerList = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return spelerList.size();
    }
    private Speler getItem(int position) {
        return spelerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return spelerList.get(position).getId();
    }
    public void updateList(List<Speler> newlist) {
        // Set new updated list
        spelerList.clear();
        spelerList.addAll(newlist);
    }

    @Override
    public SpelerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speler_item, parent, false);
        return new SpelerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpelerAdapter.ViewHolder holder, int position)
    {
        //Populate the row
        holder.populateRow(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView mSpelerNaam;
//        private final TextView mSpelerGeboortedatum;
        private final TextView mSpelerclub;
//        private final TextView mSpelerSoortlid;
//        private final TextView mSpelerSpeelsterkte;
//        private final TextView mSpelerCompetitie;

        //initialize the variables
        public ViewHolder(View view) {
            super(view);
            mSpelerNaam = (TextView) view.findViewById(R.id.spelerNaam);
//            mSpelerGeboortedatum = (TextView) view.findViewById(R.id.spelerGeboortedatum);
            mSpelerclub = (TextView) view.findViewById(R.id.spelerClub);
//            mSpelerSoortlid = (TextView) view.findViewById(R.id.spelerSoortLid);
//            mSpelerSpeelsterkte = (TextView) view.findViewById(R.id.spelerSpeelsterkte);
//            mSpelerCompetitie = (TextView) view.findViewById(R.id.spelerCompetite);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, SpelerDetailsActivity.class);
            // Get the correct toernooi based on which listitem got clicked, and put it as parameter in the intent
            Speler selectedSpeler = getItem(getAdapterPosition());
//            intent.putExtra("selectedSpeler", selectedSpeler);
//             Open SpelerDetailsActivity
            context.startActivity(intent);
        }

        public void populateRow(Speler speler) {
            mSpelerNaam.setText(speler.getNaam());
//            mSpelerGeboortedatum.setText(speler.getGeboortedatum());
            mSpelerclub.setText(speler.getClub());
//            mSpelerSoortlid.setText(speler.getSoortLid());
//            mSpelerSpeelsterkte.setText(speler.getSpeelsterkte());
//            mSpelerCompetitie.setText(speler.getCompetitie());
        }
    }
}
