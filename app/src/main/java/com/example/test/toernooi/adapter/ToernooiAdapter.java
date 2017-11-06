package com.example.test.toernooi.adapter;

/**
 * Created by Melanie on 15-10-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.toernooi.R;
import com.example.test.toernooi.activity.ToernooiDetailsActivity;
import com.example.test.toernooi.model.Toernooi;

import java.util.List;

public class ToernooiAdapter extends RecyclerView.Adapter<ToernooiAdapter.ViewHolder> {
    final Context context;
    private final List<Toernooi> toernooiList;

    public ToernooiAdapter(List<Toernooi> list, Context context) {
        toernooiList = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return toernooiList.size();
    }
    private Toernooi getItem(int position) {
        return toernooiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return toernooiList.get(position).getId();
    }
    public void updateList(List<Toernooi> newlist) {
        // Set new updated list
        toernooiList.clear();
        toernooiList.addAll(newlist);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.toernooi_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //Populate the row
        holder.populateRow(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView mToernooiNaam;
        private final TextView mToernooiDatum;
        //initialize the variables
        public ViewHolder(View view) {
            super(view);
            mToernooiNaam = (TextView) view.findViewById(R.id.toernooiNaam);
            mToernooiDatum = (TextView) view.findViewById(R.id.toernooiDatum);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ToernooiDetailsActivity.class);
            // Get the correct game based on which listitem got clicked, and put it as parameter in the intent
            Toernooi selectedToernooi = getItem(getAdapterPosition());
            intent.putExtra("selectedToernooi", selectedToernooi);
            // Open GameDetailsActivity
            context.startActivity(intent);

        }
        public void populateRow(Toernooi toernooi) {
            mToernooiNaam.setText(toernooi.getNaam());
            mToernooiDatum.setText(toernooi.getDatum());
        }
    }
}
