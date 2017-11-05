package com.example.test.toernooi.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void modifyToernooi(Toernooi toernooi) {
        open();
        ContentValues values = new ContentValues();
        values.put(ToernooiContract.ToernooiEntry.COLUMN_NAME_NAAM, toernooi.getNaam());
        values.put(ToernooiContract.ToernooiEntry.COLUMN_NAME_DATUM, toernooi.getDatum());

        mDatabase.update(ToernooiContract.ToernooiEntry.TABLE_NAME, values, ToernooiContract.ToernooiEntry.COLUMN_NAME_ID + "= ?", new String[]{String.valueOf(toernooi.getId())});
        mDatabase.close(); // Closing database connection
    }

    public Cursor getAllToernooien() {
        return mDatabase.query(ToernooiContract.ToernooiEntry.TABLE_NAME, TOERNOOI_ALL_COLUMNS, null, null, null, null, null);
    }

    public List<Toernooi> getToernooien()
    {
        mDatabase = mDBHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                ToernooiContract.ToernooiEntry.COLUMN_NAME_ID + ',' +
                ToernooiContract.ToernooiEntry.COLUMN_NAME_NAAM + ',' +
                ToernooiContract.ToernooiEntry.COLUMN_NAME_DATUM +
                " FROM " + ToernooiContract.ToernooiEntry.TABLE_NAME;

        //User user = new User();
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

    public void deleteToernooi(long user_Id) {
        open();
        // It's a good practice to use parameter ?, instead of concatenate string
        mDatabase.delete(ToernooiContract.ToernooiEntry.TABLE_NAME, ToernooiContract.ToernooiEntry.COLUMN_NAME_ID + " =?",
                new String[]{Long.toString(user_Id)});
        close(); // Closing database connection
    }

    public void deleteAllToernooien()
    {
        open();
        mDatabase.delete(ToernooiContract.ToernooiEntry.TABLE_NAME, null,null);
        close();
    }

}