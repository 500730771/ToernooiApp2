package com.example.test.toernooi.activity.toernooi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.toernooi.R;
import com.example.test.toernooi.model.Toernooi;
import com.example.test.toernooi.utility.ConfirmDeleteDialog;
import com.example.test.toernooi.data.DataSource;

/**
 * Created by Melanie on 15-10-2017.
 */
public class ToernooiDetailsActivity extends AppCompatActivity
        implements ConfirmDeleteDialog.ConfirmDeleteDialogListener
{

    private Toernooi toernooi;
    private TextView mtoernooiNaam;
    private TextView mtoernooiDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toernooi_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Get the toernooi from the intent, which was passed as parameter
        this.toernooi = (Toernooi) getIntent().getSerializableExtra("selectedToernooi");
        setToernooiViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.action_modify_game);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ToernooiDetailsActivity.this, ModifyToernooiActivity.class);
                intent.putExtra("selectedToernooi", toernooi);
                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Set the Game Card with updated toernooi
        toernooi = (Toernooi) data.getSerializableExtra("selectedToernooi");
        setToernooiViews();
    }


    public void setToernooiViews() {
        //
        mtoernooiNaam = (TextView) findViewById(R.id.toernooiNaam);
        mtoernooiDatum = (TextView) findViewById(R.id.toernooiDatum);

        mtoernooiNaam.setText(toernooi.getNaam().toString());
        mtoernooiDatum.setText(toernooi.getDatum().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_toernooi_details, menu);
        return true;
    }


    @Override
    public void onDialogPositiveClick(android.app.DialogFragment dialog)
    {
        //User clicked on the confirm button of the Dialog, delete the toernooi from Database
        DataSource datasource = new DataSource(this);
        //We only need the id of the toernooi to delete it
        datasource.deleteToernooi(toernooi.getId());
        //Game has been deleted, go back to the MainActivity
        showGameDeletedToast();
        finish();
    }

    @Override
    public void onDialogNegativeClick(android.app.DialogFragment dialog)
    {
        //Do nothing, Dialog will disappear
    }

    private void showGameDeletedToast()
    {
        Context context = getApplicationContext();
        String text = String.format("%s %s", toernooi.getNaam(), getString(R.string.toernooi_deleted));
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_delete_game)
        {
            // Show the ConfirmDeleteDialog
            android.app.DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(R.string.dialog_toernooi_deletion_single));
            bundle.putString("positiveButton", getString(R.string.dialog_deletion_positive));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }
        return super.onOptionsItemSelected(item);
    }
}
