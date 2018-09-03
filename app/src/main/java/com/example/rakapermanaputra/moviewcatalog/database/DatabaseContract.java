package com.example.rakapermanaputra.moviewcatalog.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_MOVIE = "movie";

    static final class MovieColumns implements BaseColumns {

        // Movie title
        static String TITLE = "title";
        // Movie release_date
        static String RELEASE_DATE = "release_date";
        // Movie overview
        static String OVERVIEW = "overview";
        // Movie Poster
        static String POSTER_PATH = "poster_path";

    }

}
