package com.example.test.toernooi.data;

import android.provider.BaseColumns;

/**
 * Created by Melanie on 15-10-2017.
 */

public class SpelerContract {
    private SpelerContract() {}

    /* Inner class that defines the table contents */
    public static class SpelerEntry implements BaseColumns {
        // Labels table name
        public static final String TABLE_NAME = "Speler";
        // Labels Table Columns names
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAAM = "naam";
        public static final String COLUMN_NAME_GEBOORTEDATUM = "geboortedatum";
        public static final String COLUMN_NAME_CLUB = "club";
        public static final String COLUMN_NAME_SOORT_LID = "soort_lid";
        public static final String COLUMN_NAME_SPEELSTERKTE = "speelsterkte";
        public static final String COLUMN_NAME_COMPETITIE = "competitie";
    }
}
