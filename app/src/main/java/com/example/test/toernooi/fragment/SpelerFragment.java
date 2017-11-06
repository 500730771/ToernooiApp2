package com.example.test.toernooi.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.toernooi.R;
import com.example.test.toernooi.adapter.SpelerAdapter;
import com.example.test.toernooi.data.DataSource;
import com.example.test.toernooi.model.Speler;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpelerFragment extends Fragment {

    private static final String TITLE = "TABSPELERFRAGMENT";
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private RecyclerView list;
    private SpelerAdapter mSpelerAdapter;
    private List<Speler> mSpelers;

    public SpelerFragment() {
        // Required empty public constructor
    }

    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speler, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_speler, container, false);
        rootView.setTag(TAG);

        list = (RecyclerView) rootView.findViewById(R.id.spelerlist);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(mLayoutManager);
        list.setHasFixedSize(true);

        //Adding ItemAnimator
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(100L);
        itemAnimator.setRemoveDuration(100L);
        list.setItemAnimator(itemAnimator);

        //Adding Gestures, this makes you possible to swipe and move cards inside the ListView
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP
                | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(mSpelers, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                mSpelerAdapter.updateList(mSpelers);
                mSpelerAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // Create a DataSource object, and pass it the context of this activity
                DataSource dataSource = new DataSource(getActivity());
                // Delete the list of games from Database
                dataSource.deleteAllSpelers();
                for (Speler speler : mSpelers) {
                    dataSource.saveSpeler(speler);
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                // Create a DataSource object, and pass it the context of this activity
                DataSource dataSource = new DataSource(getActivity());
                // Delete the list of games from Database
                dataSource.deleteSpeler(mSpelers.get(viewHolder.getAdapterPosition()).getId());

                // Remove toernooi from temporary list
                mSpelers.remove(viewHolder.getAdapterPosition());

                mSpelerAdapter.updateList(mSpelers);
                mSpelerAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                mSpelerAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), mSpelers.size());

                // Display toast with Feedback
                //showToast(getString(R.string.swipe_delete));
                Context context = getActivity();
                String text = String.format("Speler deleted");
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(list);
        return rootView;
    }

    public void onResume() {
        super.onResume();  // Always call the superclass method first
        updateUI();
    }

    private void updateUI() {
        DataSource dataSource = new DataSource(getActivity());
        // Get the list of games from Database
        mSpelers = dataSource.getSpelers();
        if (mSpelerAdapter == null) {
            mSpelerAdapter = new SpelerAdapter(mSpelers, getActivity());
            list.setAdapter(mSpelerAdapter);
        } else {
            mSpelerAdapter.updateList(mSpelers);
            mSpelerAdapter.notifyDataSetChanged();
        }
    }
}
