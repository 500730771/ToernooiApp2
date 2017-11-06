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
import com.example.test.toernooi.model.Toernooi;
import com.example.test.toernooi.utility.ConfirmDeleteDialog;
import com.example.test.toernooi.data.DataSource;

/**
 * Created by Melanie on 15-10-2017.
 */
public class ModifyToernooiActivity extends AppCompatActivity
        implements ConfirmDeleteDialog.ConfirmDeleteDialogListener
{

    private Toernooi toernooi;
    private EditText mToernooiNaam;
    private EditText mToernooiDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_toernooi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the selected toernooi that we've sent from ToernooiDetailsActivity
        Intent intent = getIntent();
        toernooi = (Toernooi) intent.getSerializableExtra("selectedToernooi");
        setGameView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyToernooi();
            }
        });
    }

    private void setGameView() {
        //declare textfields
        mToernooiNaam = (EditText) findViewById(R.id.toernooiNaam);
        mToernooiDatum = (EditText) findViewById(R.id.toernooiDatum);

        mToernooiNaam.setText(toernooi.getNaam());
        mToernooiDatum.setText(toernooi.getDatum());
    }

    void modifyToernooi() {
        // Get the input from the Views
        String toernooiNaam = mToernooiNaam.getText().toString();
        String toernooiDatum = mToernooiDatum.getText().toString();

        if ((toernooiNaam != null) && toernooiNaam.isEmpty()) {
            // Make EditText titleInput display an error message, and display a toast
            // That the title field is empty
            ModifyToernooiActivity.setErrorText(mToernooiNaam, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if ((toernooiDatum != null) && toernooiDatum.isEmpty()) {
            // Make EditText platformInput display an error message, and display a toast
            // That the platform field is empty
            ModifyToernooiActivity.setErrorText(mToernooiDatum, getString(R.string.datum_is_required));
            showToast(getString(R.string.datum_field_is_empty));
        } else {
            // Update the toernooi with the new data
            toernooi.setNaam(toernooiNaam);
            toernooi.setDatum(toernooiDatum);

            // Create a DataSource object, and pass it the context of this activity
            DataSource datasource = new DataSource (this);
            datasource.modifyToernooi(toernooi);

            //Notify the user of the success
            showToast(getString(R.string.toernooi_has_been_modified));

            // Starting the previous Intent
            Intent previousActivity = new Intent(this, ToernooiDetailsActivity.class);
            // Sending the data to GameDetailsActivity
            previousActivity.putExtra("selectedToernooi", toernooi);
            setResult(1000, previousActivity);
            finish();
        }
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
        // Save toernooi and go back to MainActivity
        modifyToernooi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_cancel)
        {
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
    public void onDialogPositiveClick(DialogFragment dialog)
    {
        Intent previousActivity = new Intent(this, ToernooiDetailsActivity.class);
        //Sending the origional data to GameDetailActivity
        previousActivity.putExtra("selectedToernooi", toernooi);
        setResult(1000, previousActivity);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {

    }
}
