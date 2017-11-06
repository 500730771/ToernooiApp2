package com.example.test.toernooi.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.toernooi.R;
import com.example.test.toernooi.data.DataSource;
import com.example.test.toernooi.model.Speler;
import com.example.test.toernooi.utility.ConfirmDeleteDialog;

public class ModifySpelerActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    private Speler speler;
    private EditText mSpelerNaam;
    private EditText mSpelerGeboortedatum;
    private EditText mSpelerClub;
    private EditText mSpelerSoortlid;
    private EditText mSpelerSpeelsterkte;
    private EditText mSpelerCompetitie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_speler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the selected toernooi that we've sent from SpelerDetailsActivity
        Intent intent = getIntent();
        speler = (Speler) intent.getSerializableExtra("selectedSpeler");
        setSpelerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifySpeler();
            }
        });
    }

    private void setSpelerView() {
        mSpelerNaam = (EditText) findViewById(R.id.spelerNaam);
        mSpelerGeboortedatum = (EditText) findViewById(R.id.spelerGeboortedatum);
        mSpelerClub = (EditText) findViewById(R.id.spelerClub);
        mSpelerSoortlid = (EditText) findViewById(R.id.spelerSoortLid);
        mSpelerSpeelsterkte = (EditText) findViewById(R.id.spelerSpeelsterkte);
        mSpelerCompetitie = (EditText) findViewById(R.id.spelerCompetite);

        mSpelerNaam.setText(speler.getNaam().toString());
        mSpelerGeboortedatum.setText(speler.getGeboortedatum().toString());
        mSpelerClub.setText(speler.getClub().toString());
        mSpelerSoortlid.setText(speler.getSoortLid().toString());
        mSpelerSpeelsterkte.setText(speler.getSpeelsterkte().toString());
        mSpelerCompetitie.setText(speler.getCompetitie().toString());
    }

    void modifySpeler() {
        // Retrieve the input from the user
        String naam = mSpelerNaam.getText().toString();
        String geboortedatum = mSpelerGeboortedatum.getText().toString();
        String club = mSpelerClub.getText().toString();
        String soortlid = mSpelerSoortlid.getText().toString();
        String speelsterkte = mSpelerSpeelsterkte.getText().toString();
        String competitie = mSpelerCompetitie.getText().toString();

//        if ((toernooiNaam != null) && toernooiNaam.isEmpty()) {
//            // Make EditText titleInput display an error message, and display a toast
//            // That the title field is empty
//            ModifyToernooiActivity.setErrorText(mToernooiNaam, getString(R.string.title_is_required));
//            showToast(getString(R.string.title_field_is_empty));
//        } else if ((toernooiDatum != null) && toernooiDatum.isEmpty()) {
//            // Make EditText platformInput display an error message, and display a toast
//            // That the platform field is empty
//            ModifyToernooiActivity.setErrorText(mToernooiDatum, getString(R.string.datum_is_required));
//            showToast(getString(R.string.datum_field_is_empty));
//        } else {
            // Update the speler with the new data
            speler.setNaam(naam);
            speler.setGeboortedatum(geboortedatum);
            speler.setClub(club);
            speler.setSoortLid(soortlid);
            speler.setSpeelsterkte(speelsterkte);
            speler.setCompetitie(competitie);

            // Create a DataSource object, and pass it the context of this activity
            DataSource datasource = new DataSource(this);
            datasource.modifySpeler(speler);

            //Notify the user of the success
            showToast("Speler is bewerkt.");

            // Starting the previous Intent
            Intent previousActivity = new Intent(this, SpelerDetailsActivity.class);
            // Sending the data to SpelerDetailsActivity
//            previousActivity.putExtra("speler", speler);
            setResult(100, previousActivity);
            finish();
//        }
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private static void setErrorText(EditText editText, String message) {
        // Get the color white in integer form
        int RGB = Color.argb(255, 255, 0, 0);
// Object that contains the color white
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(RGB);
// Object that will hold the message, and makes it possible to change the color of the text
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
// Give the message from the first till the last character a white color.
// The last '0' means that the message should not display additional behaviour
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
// Make the EditText display the error message
        editText.setError(ssbuilder);
    }

    @Override
    public void onBackPressed() {
        super.onResume();  // Always call the superclass method first
        // Save speler and go back to MainActivity
        modifySpeler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cancel) {
            // Show the ConfirmDiscardDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(R.string.dialog_toernooi_discard));
            bundle.putString("positiveButton", getString(R.string.dialog_toernooi_modify_positive));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent previousActivity = new Intent(this, SpelerDetailsActivity.class);
        //Sending the origional data to SpelerDetailActivity
//        previousActivity.putExtra("speler", speler);
        setResult(100, previousActivity);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
