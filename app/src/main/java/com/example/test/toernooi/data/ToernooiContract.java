package com.example.test.toernooi.data;

import android.provider.BaseColumns;

/**
 * Created by Melanie on 15-10-2017.
 */

public final class ToernooiContract {

    private ToernooiContract() {}

    /* Inner class that defines the table contents */
    public static class ToernooiEntry implements BaseColumns {
        // Labels table name
        public static final String TABLE_NAME = "Toernooi";
        // Labels Table Columns names
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAAM = "naam";
        public static final String COLUMN_NAME_DATUM = "datum";
    }
}
