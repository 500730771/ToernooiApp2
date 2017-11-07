package com.example.test.toernooi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.toernooi.R;
import com.example.test.toernooi.activity.speler.SpelerDetailsActivity;
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
        private final TextView mSpelerclub;

        //initialize the variables
        public ViewHolder(View view) {
            super(view);
            mSpelerNaam = (TextView) view.findViewById(R.id.spelerNaam);
            mSpelerclub = (TextView) view.findViewById(R.id.spelerClub);
            view.setOnClickListener(this);
        }

        /**
         * Deze methode is als er op een speler wordt geklikt, wat gebeurt er dan.
         * De details van de speler wordt laten zien in een nieuwe activity.
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, SpelerDetailsActivity.class);
            Speler selectedSpeler = getItem(getAdapterPosition());
            intent.putExtra("selectedSpeler", selectedSpeler);
            context.startActivity(intent);
        }

        public void populateRow(Speler speler) {
            mSpelerNaam.setText(speler.getNaam());
            mSpelerclub.setText(speler.getClub());
        }
    }
}
