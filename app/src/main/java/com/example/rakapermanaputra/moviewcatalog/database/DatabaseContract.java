package com.example.rakapermanaputra.moviewcatalog.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns {

        // Movie title
        public static String TITLE = "title";
        // Movie release_date
        public static String RELEASE_DATE = "release_date";
        // Movie overview
        public static String OVERVIEW = "overview";
        // Movie Poster
        public static String POSTER_PATH = "poster_path";

    }

    public static final String AUTHORITY = "com.example.rakapermanaputra.moviewcatalog";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

}
