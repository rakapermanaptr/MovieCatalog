package com.example.rakapermanaputra.moviewcatalog.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rakapermanaputra.moviewcatalog.model.Result;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.rakapermanaputra.moviewcatalog.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.rakapermanaputra.moviewcatalog.database.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.example.rakapermanaputra.moviewcatalog.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.example.rakapermanaputra.moviewcatalog.database.DatabaseContract.MovieColumns.TITLE;
import static com.example.rakapermanaputra.moviewcatalog.database.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Result> query() {
        ArrayList<Result> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        Result items;
        if (cursor.getCount() > 0) {
            do {
                items = new Result();
                items.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                items.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                items.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                items.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                items.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                arrayList.add(items);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Result items) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, items.getTitle());
        initialValues.put(OVERVIEW, items.getOverview());
        initialValues.put(RELEASE_DATE, items.getReleaseDate());
        initialValues.put(POSTER_PATH, items.getPosterPath());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
