package com.example.test.toernooi.activity.speler;

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
import com.example.test.toernooi.data.DataSource;
import com.example.test.toernooi.model.Speler;
import com.example.test.toernooi.utility.ConfirmDeleteDialog;

public class SpelerDetailsActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    private Speler speler;
    private TextView mSpelerNaam;
    private TextView mSpelerGeboortedatum;
    private TextView mSpelerClub;
    private TextView mSpelerSoortlid;
    private TextView mSpelerSpeelsterkte;
    private TextView mSpelerCompetitie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speler_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.speler = (Speler) getIntent().getSerializableExtra("selectedSpeler");
        setSpelerViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SpelerDetailsActivity.this, ModifySpelerActivity.class);
                intent.putExtra("selectedSpeler", speler);
                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Set the Speler Card with updated speler
        speler = (Speler) data.getSerializableExtra("selectedSpeler");
        setSpelerViews();
    }


    public void setSpelerViews() {
        mSpelerNaam = (TextView) findViewById(R.id.spelerNaamDetails);
        mSpelerGeboortedatum = (TextView) findViewById(R.id.spelerGeboortedatumDetails);
        mSpelerClub = (TextView) findViewById(R.id.SpelerClubDetails);
        mSpelerSoortlid = (TextView) findViewById(R.id.SpelerSoortLidDetails);
        mSpelerSpeelsterkte = (TextView) findViewById(R.id.spelerSpeelsterkteDetails);
        mSpelerCompetitie = (TextView) findViewById(R.id.SpelerCompetitieDetails);

        mSpelerNaam.setText(speler.getNaam().toString());
        mSpelerGeboortedatum.setText(speler.getGeboortedatum().toString());
        mSpelerClub.setText(speler.getClub().toString());
        mSpelerSoortlid.setText(speler.getSoortLid().toString());
        mSpelerSpeelsterkte.setText(speler.getSpeelsterkte().toString());
        mSpelerCompetitie.setText(speler.getCompetitie().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_toernooi_details, menu);
        return true;
    }

//    @Override
    public void onDialogPositiveClick(android.app.DialogFragment dialog)
    {
        //User clicked on the confirm button of the Dialog, delete the speler from Database
        DataSource datasource = new DataSource(this);
        //We only need the id of the speler to delete it
        datasource.deleteSpeler(speler.getId());
        //Game has been deleted, go back to the MainActivity
        showSpelerDeletedToast();
        finish();
    }

//    @Override
    public void onDialogNegativeClick(android.app.DialogFragment dialog)
    {
        //Do nothing, Dialog will disappear
    }

    /**
     * Als de speler is verwijderd dat wordt deze toast message laten zien
     */
    private void showSpelerDeletedToast()
    {
        Context context = getApplicationContext();
        String text = String.format("%s %s", speler.getNaam(), getString(R.string.spelers_deleted));
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_delete)
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
