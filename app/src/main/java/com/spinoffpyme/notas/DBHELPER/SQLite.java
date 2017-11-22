package com.spinoffpyme.notas.DBHELPER;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tomás on 16/11/2017.
 */

public class SQLite extends SQLiteOpenHelper {
    private static final String usuarios = "CREATE TABLE usuarios (idusuario INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT, email TEXT,password TEXT);";
    private static final String notas= "CREATE TABLE notas (idnota INTEGER PRIMARY KEY AUTOINCREMENT,titulo TEXT,descripcion TEXT,idusuario INTEGER);";

    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(usuarios);
        db.execSQL(notas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
