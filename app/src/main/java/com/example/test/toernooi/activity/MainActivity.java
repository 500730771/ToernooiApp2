package com.example.test.toernooi.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.test.toernooi.R;
import com.example.test.toernooi.adapter.SpelerAdapter;
import com.example.test.toernooi.adapter.ToernooiAdapter;
import com.example.test.toernooi.data.DataSource;
import com.example.test.toernooi.model.Speler;
import com.example.test.toernooi.model.Toernooi;
import com.example.test.toernooi.utility.ConfirmDeleteDialog;
import com.facebook.stetho.Stetho;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ConfirmDeleteDialog.ConfirmDeleteDialogListener
{
    private RecyclerView list;
    private ToernooiAdapter mToernooiAdapter;
    private SpelerAdapter mSpelerAdapter;
    private List<Toernooi> mToernooien;
    private List<Speler> mSpelers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get list from view
        list = (RecyclerView) findViewById(R.id.list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
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
                DataSource dataSource = new DataSource(MainActivity.this);
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
                DataSource dataSource = new DataSource(MainActivity.this);
                // Delete the list of games from Database
                dataSource.deleteToernooi(mToernooien.get(viewHolder.getAdapterPosition()).getId());

                // Remove game from temporary list
                mToernooien.remove(viewHolder.getAdapterPosition());

                mToernooiAdapter.updateList(mToernooien);
                mToernooiAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                mToernooiAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), mToernooien.size());

                // Display toast with Feedback
                //showToast(getString(R.string.swipe_delete));
                Context context = getApplicationContext();
                String text = String.format(getString(R.string.swipe_delete));
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(list);
        //updateUI();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddToernooiActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_delete_all) {
            // Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(R.string.dialog_toernooi_deletion_all));
            bundle.putString("positiveButton", getString(R.string.dialog_toernooi_deletion_positive));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();  // Always call the superclass method first
        updateUI();
    }

    private void updateUI() {
        DataSource dataSource = new DataSource(this);
        // Get the list of games from Database
        mToernooien = dataSource.getToernooien();
        if (mToernooiAdapter == null) {
            mToernooiAdapter = new ToernooiAdapter(mToernooien, this);
            list.setAdapter(mToernooiAdapter);
        } else {
            mToernooiAdapter.updateList(mToernooien);
            mToernooiAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {
        //User clicked on the confirm button of the Dialog, delete the game from Database
        DataSource dataSource = new DataSource(this);
        //Delete all games
        dataSource.deleteAllToernooien();
        //Games have been deleted, go back to the MainActivity
        showGameDeletedToast();
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {
        //Do nothing, Dialog will disappear
    }

    private void showGameDeletedToast()
    {
        Context context = getApplicationContext();
        String text = getString(R.string.toernooien_deleted);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
