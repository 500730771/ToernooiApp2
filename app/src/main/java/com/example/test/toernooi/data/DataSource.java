package com.example.test.toernooi.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.test.toernooi.model.Speler;
import com.example.test.toernooi.model.Toernooi;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Melanie on 15-10-2017.
 */

public class DataSource {

    //Local variables and constants
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private String[] TOERNOOI_ALL_COLUMNS = {
            ToernooiContract.ToernooiEntry.COLUMN_NAME_ID,
            ToernooiContract.ToernooiEntry.COLUMN_NAME_NAAM,
            ToernooiContract.ToernooiEntry.COLUMN_NAME_DATUM};

    private String[] SPELER_ALL_COLUMNS = {
            SpelerContract.SpelerEntry.COLUMN_NAME_ID,
            SpelerContract.SpelerEntry.COLUMN_NAME_NAAM,
            SpelerContract.SpelerEntry.COLUMN_NAME_GEBOORTEDATUM,
            SpelerContract.SpelerEntry.COLUMN_NAME_CLUB,
            SpelerContract.SpelerEntry.COLUMN_NAME_SOORT_LID,
            SpelerContract.SpelerEntry.COLUMN_NAME_SPEELSTERKTE,
            SpelerContract.SpelerEntry.COLUMN_NAME_GEBOORTEDATUM};

    public DataSource(Context context) {
        mDBHelper = new DBHelper(context);
    }
    // Opens the database to use it
    public void open()  {
        mDatabase = mDBHelper.getWritableDatabase();
    }
    // Closes the database when you no longer need it
    public void close() {
        mDBHelper.close();
    }


    //TOERNOOI
    /**
     * Hier wordt er een toernooi opgeslagen in de database
     * @param toernooi = het toernooi dat moet worden opgeslagen
     */
    public void saveToernooi(Toernooi toernooi) {
        // Open connection to write data
        open();
        ContentValues values = new ContentValues();
        values.put(ToernooiContract.ToernooiEntry.COLUMN_NAME_NAAM, toernooi.getNaam());
        values.put(ToernooiContract.ToernooiEntry.COLUMN_NAME_DATUM, toernooi.getDatum());
        // Inserting Row
        mDatabase.insert(ToernooiContract.ToernooiEntry.TABLE_NAME, null, values);
        close(); // Closing database connection
    }

    /**
     * Hier wordt er een toernooi geupdated
     * @param toernooi = het toernooi dat moet worden bewerkt/geupdated
     */
    public void modifyToernooi(Toernooi toernooi) {
        open();
        ContentValues values = new ContentValues();
        values.put(ToernooiContract.ToernooiEntry.COLUMN_NAME_NAAM, toernooi.getNaam());
        values.put(ToernooiContract.ToernooiEntry.COLUMN_NAME_DATUM, toernooi.getDatum());

        mDatabase.update(ToernooiContract.ToernooiEntry.TABLE_NAME, values, ToernooiContract.ToernooiEntry.COLUMN_NAME_ID + "= ?", new String[]{String.valueOf(toernooi.getId())});
        mDatabase.close(); // Closing database connection
    }

    /**
     * Haalt alle toernooien op uit de database
     * @return alle toernooien die bestaan in de datbase
     */
    public List<Toernooi> getToernooien()
    {
        mDatabase = mDBHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                ToernooiContract.ToernooiEntry.COLUMN_NAME_ID + ',' +
                ToernooiContract.ToernooiEntry.COLUMN_NAME_NAAM + ',' +
                ToernooiContract.ToernooiEntry.COLUMN_NAME_DATUM +
                " FROM " + ToernooiContract.ToernooiEntry.TABLE_NAME;

        List<Toernooi> toernooiList = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Toernooi toernooi = new Toernooi();
                toernooi.setId(cursor.getInt(cursor.getColumnIndex(ToernooiContract.ToernooiEntry.COLUMN_NAME_ID)));
                toernooi.setNaam(cursor.getString(cursor.getColumnIndex(ToernooiContract.ToernooiEntry.COLUMN_NAME_NAAM)));
                toernooi.setDatum(cursor.getString(cursor.getColumnIndex(ToernooiContract.ToernooiEntry.COLUMN_NAME_DATUM)));
                toernooiList.add(toernooi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return toernooiList;
    }

    /**
     * Er wordt een bepaald toernooi verwijderd
     * @param user_Id = het id van het toernooi dat moet worden verwijderd.
     */
    public void deleteToernooi(long user_Id) {
        open();
        // It's a good practice to use parameter ?, instead of concatenate string
        mDatabase.delete(ToernooiContract.ToernooiEntry.TABLE_NAME, ToernooiContract.ToernooiEntry.COLUMN_NAME_ID + " =?",
                new String[]{Long.toString(user_Id)});
        close(); // Closing database connection
    }

    /**
     * Alle toernooien worden verwijderd.
     */
    public void deleteAllToernooien()
    {
        open();
        mDatabase.delete(ToernooiContract.ToernooiEntry.TABLE_NAME, null,null);
        close();
    }

    //SPELER

    /**
     * Als er een speler wordt opgeslagen in de database wordt deze methode aangeroepen
     * @param speler = de speler die moet worden opgeslagen
     */
    public void saveSpeler(Speler speler) {
        // Open connection to write data
        open();
        ContentValues values = new ContentValues();
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_NAAM, speler.getNaam());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_GEBOORTEDATUM, speler.getGeboortedatum());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_CLUB, speler.getClub());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_SOORT_LID, speler.getSoortLid());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_SPEELSTERKTE, speler.getSpeelsterkte());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_COMPETITIE, speler.getCompetitie());
        // Inserting Row
        mDatabase.insert(SpelerContract.SpelerEntry.TABLE_NAME, null, values);
        close(); // Closing database connection
    }

    /**
     * Een speler bewerken
     * @param speler = de speler die moet worden bewerkt, geupdated
     */
    public void modifySpeler(Speler speler) {
        open();
        ContentValues values = new ContentValues();
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_NAAM, speler.getNaam());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_GEBOORTEDATUM, speler.getGeboortedatum());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_CLUB, speler.getClub());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_SOORT_LID, speler.getSoortLid());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_SPEELSTERKTE, speler.getSpeelsterkte());
        values.put(SpelerContract.SpelerEntry.COLUMN_NAME_COMPETITIE, speler.getCompetitie());

        mDatabase.update(SpelerContract.SpelerEntry.TABLE_NAME, values, SpelerContract.SpelerEntry.COLUMN_NAME_ID + "= ?", new String[]{String.valueOf(speler.getId())});
        mDatabase.close(); // Closing database connection
    }

    /**
     * Een lijst van alle spelers ophalen
     * @return een lijst van alle spelers
     */
    public List<Speler> getSpelers()
    {
        mDatabase = mDBHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                SpelerContract.SpelerEntry.COLUMN_NAME_ID + ',' +
                SpelerContract.SpelerEntry.COLUMN_NAME_NAAM + ',' +
                SpelerContract.SpelerEntry.COLUMN_NAME_GEBOORTEDATUM + ',' +
                SpelerContract.SpelerEntry.COLUMN_NAME_CLUB + ',' +
                SpelerContract.SpelerEntry.COLUMN_NAME_SOORT_LID + ',' +
                SpelerContract.SpelerEntry.COLUMN_NAME_SPEELSTERKTE + ',' +
                SpelerContract.SpelerEntry.COLUMN_NAME_COMPETITIE +
                " FROM " + SpelerContract.SpelerEntry.TABLE_NAME;

        List<Speler> spelerlist = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Speler speler = new Speler();
                speler.setId(cursor.getInt(cursor.getColumnIndex(SpelerContract.SpelerEntry.COLUMN_NAME_ID)));
                speler.setNaam(cursor.getString(cursor.getColumnIndex(SpelerContract.SpelerEntry.COLUMN_NAME_NAAM)));
                speler.setGeboortedatum(cursor.getString(cursor.getColumnIndex(SpelerContract.SpelerEntry.COLUMN_NAME_GEBOORTEDATUM)));
                speler.setClub(cursor.getString(cursor.getColumnIndex(SpelerContract.SpelerEntry.COLUMN_NAME_CLUB)));
                speler.setSoortLid(cursor.getString(cursor.getColumnIndex(SpelerContract.SpelerEntry.COLUMN_NAME_SOORT_LID)));
                speler.setSpeelsterkte(cursor.getString(cursor.getColumnIndex(SpelerContract.SpelerEntry.COLUMN_NAME_SPEELSTERKTE)));
                speler.setCompetitie(cursor.getString(cursor.getColumnIndex(SpelerContract.SpelerEntry.COLUMN_NAME_COMPETITIE)));

                spelerlist.add(speler);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return spelerlist;
    }

    /**
     * Een bepaalde speler verwijderen
     * @param user_Id = het id van de speler die wordt verwijderd.
     */
    public void deleteSpeler(long user_Id) {
        open();
        // It's a good practice to use parameter ?, instead of concatenate string
        mDatabase.delete(SpelerContract.SpelerEntry.TABLE_NAME, SpelerContract.SpelerEntry.COLUMN_NAME_ID + " =?",
                new String[]{Long.toString(user_Id)});
        close(); // Closing database connection
    }

    /**
     *  Alle spelers verwijderen
     */
    public void deleteAllSpelers()
    {
        open();
        mDatabase.delete(SpelerContract.SpelerEntry.TABLE_NAME, null,null);
        close();
    }
}