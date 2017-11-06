package com.example.test.toernooi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.test.toernooi.adapter.ToernooiAdapter;
import com.example.test.toernooi.data.DataSource;
import com.example.test.toernooi.model.Toernooi;

import java.util.Collections;
import java.util.List;

/**
 * Created by Melanie on 5-11-2017.
 */

public class ToernooiFragment extends Fragment {
    private static final String TITLE = "TABTOERNOOIFRAGMENT";
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private RecyclerView list;
    private ToernooiAdapter mToernooiAdapter;
    private List<Toernooi> mToernooien;

    public ToernooiFragment() {
        // Required empty public constructor
    }

    public View onCreate(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toernooi, container, false);

//        list = (RecyclerView) view.findViewById(R.id.list);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        list.setLayoutManager(mLayoutManager);
//        list.setHasFixedSize(true);

        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_toernooi, container, false);
        rootView.setTag(TAG);

        list = (RecyclerView) rootView.findViewById(R.id.toernooilist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
                Collections.swap(mToernooien, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                mToernooiAdapter.updateList(mToernooien);
                mToernooiAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // Create a DataSource object, and pass it the context of this activity
                DataSource dataSource = new DataSource(getActivity());
                // Delete the list of games from Database
                dataSource.deleteAllToernooien();
                for (Toernooi toernooi : mToernooien) {
                    dataSource.saveToernooi(toernooi);
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                // Create a DataSource object, and pass it the context of this activity
                DataSource dataSource = new DataSource(getActivity());
                // Delete the list of games from Database
                dataSource.deleteToernooi(mToernooien.get(viewHolder.getAdapterPosition()).getId());

                // Remove toernooi from temporary list
                mToernooien.remove(viewHolder.getAdapterPosition());

                mToernooiAdapter.updateList(mToernooien);
                mToernooiAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                mToernooiAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), mToernooien.size());

                // Display toast with Feedback
                //showToast(getString(R.string.swipe_delete));
                Context context = getActivity();
                String text = String.format(getString(R.string.swipe_delete));
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(list);
        //updateUI();

        return rootView;
    }

    public void onResume() {
        super.onResume();  // Always call the superclass method first
        updateUI();
    }

    private void updateUI() {
        DataSource dataSource = new DataSource(getActivity());
        // Get the list of games from Database
        mToernooien = dataSource.getToernooien();
        if (mToernooiAdapter == null) {
            mToernooiAdapter = new ToernooiAdapter(mToernooien, getActivity());
            list.setAdapter(mToernooiAdapter);
        } else {
            mToernooiAdapter.updateList(mToernooien);
            mToernooiAdapter.notifyDataSetChanged();
        }
    }
}
