package com.example.test.toernooi.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Melanie on 15-10-2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "badmintontoernooi.db";
    private static final int DATABASE_VERSION = 8;

    // Creating the table
    private static final String DATABASE_CREATE_TABLE_TOERNOOI =
            "CREATE TABLE " + ToernooiContract.ToernooiEntry.TABLE_NAME +
                    "(" +
                    ToernooiContract.ToernooiEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + ToernooiContract.ToernooiEntry.COLUMN_NAME_NAAM + " TEXT, "
                    + ToernooiContract.ToernooiEntry.COLUMN_NAME_DATUM + " TEXT )";

    private static final String DATABASE_CREATE_TABLE_SPELER =
            "CREATE TABLE " + SpelerContract.SpelerEntry.TABLE_NAME +
                    "(" +
                    SpelerContract.SpelerEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + SpelerContract.SpelerEntry.COLUMN_NAME_NAAM + " TEXT, "
                    + SpelerContract.SpelerEntry.COLUMN_NAME_GEBOORTEDATUM + " TEXT, "
                    + SpelerContract.SpelerEntry.COLUMN_NAME_CLUB + " TEXT, "
                    + SpelerContract.SpelerEntry.COLUMN_NAME_SOORT_LID + " TEXT, "
                    + SpelerContract.SpelerEntry.COLUMN_NAME_SPEELSTERKTE + " TEXT, "
                    + SpelerContract.SpelerEntry.COLUMN_NAME_COMPETITIE + " TEXT )";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_TABLE_TOERNOOI);
        sqLiteDatabase.execSQL(DATABASE_CREATE_TABLE_SPELER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ToernooiContract.ToernooiEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SpelerContract.SpelerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
