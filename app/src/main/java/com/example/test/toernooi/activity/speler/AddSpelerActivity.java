package com.example.test.toernooi.activity.speler;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.toernooi.R;
import com.example.test.toernooi.data.DataSource;
import com.example.test.toernooi.model.Speler;

public class AddSpelerActivity extends AppCompatActivity {

    EditText mSpelerNaam;
    EditText mSpelerGeboortedatum;
    EditText mSpelerClub;
    EditText mSpelerSoortlid;
    EditText mSpelerSpeelsterkte;
    EditText mSpelerCompetitie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_speler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //velden initialiseren
        mSpelerNaam = (EditText)findViewById(R.id.spelerNaam);
        mSpelerGeboortedatum = (EditText)findViewById(R.id.spelerGeboortedatum);
        mSpelerClub = (EditText)findViewById(R.id.spelerClub);
        mSpelerSoortlid = (EditText)findViewById(R.id.spelerSoortLid);
        mSpelerSpeelsterkte = (EditText)findViewById(R.id.spelerSpeelsterkte);
        mSpelerCompetitie = (EditText)findViewById(R.id.spelerCompetite);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { saveSpeler(); }
        });
    }

    /**
     * Een speler wordt opgeslagen in de database en de velden worden gecontroleerd.
     */
    void saveSpeler() {
        // Retrieve the input from the user
        String naam = mSpelerNaam.getText().toString();
        String geboortedatum = mSpelerGeboortedatum.getText().toString();
        String club = mSpelerClub.getText().toString();
        String soortlid = mSpelerSoortlid.getText().toString();
        String speelsterkte = mSpelerSpeelsterkte.getText().toString();
        String competitie = mSpelerCompetitie.getText().toString();

        //controleren of elk veld is ingevuld.
//        if (ControleerVerplichteVelden(mSpelerNaam, naam, "Speler naam is verplicht.")) {
//        } else if (ControleerVerplichteVelden(mSpelerGeboortedatum, geboortedatum, "Speler geboortedatum is verplicht.")) {
//        } else if (ControleerVerplichteVelden(mSpelerClub, club, "Speler club is verplicht.")) {
//        } else if (ControleerVerplichteVelden(mSpelerSoortlid, soortlid, "Speler soort lid is verplicht.")) {
//        } else if (ControleerVerplichteVelden(mSpelerSpeelsterkte, speelsterkte, "Speler speelsterkte is verplicht.")) {
//        } else if (ControleerVerplichteVelden(mSpelerCompetitie, competitie, "Speler competitie is verplicht.")) {
//        } else {
            // Create a DBCRUD object, and pass it the context of this activity
            DataSource dataSource = new DataSource(this);
            // Make a speler object based on the input. The correct id will be set in DBCRUD.saveSpeler()
            Speler speler = new Speler(-1, naam, geboortedatum, club, soortlid, speelsterkte, competitie);
            // Save the toernooi to the Database
            dataSource.saveSpeler(speler);
            // Notify the user with a toast that the toernooi has been added
            showToast(getString(R.string.speler_has_been_added));

            // Go back to MainActivity
            finish();
//        }
    }

    /**
     * Velden controleren
     * @param text de text die uit de content komt
     * @param tekstveld
     * @param message de message die moet worden laten zien als er een foutmelding moet worden weergeven
     * @return true of false voor een foutmelding
     */
    private boolean ControleerVerplichteVelden(EditText text, String tekstveld, String message){
        if (text != null && tekstveld.isEmpty()) {
            AddSpelerActivity.setErrorText(text, message);
            showToast(getString(R.string.title_field_is_empty));
            return false;
        } else {
            return true;
        }
    }

    /**
     * message die moet worden laten zien in een toast
     * @param message
     */
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
}
