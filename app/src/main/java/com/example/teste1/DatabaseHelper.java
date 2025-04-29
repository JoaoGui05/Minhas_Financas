package com.example.teste1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "financas.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_GASTOS = "gastos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_VALOR = "valor";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_DATA = "data";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_GASTOS_TABLE = "CREATE TABLE " + TABLE_GASTOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DESCRICAO + " TEXT,"
                + COLUMN_VALOR + " REAL,"
                + COLUMN_CATEGORIA + " TEXT,"
                + COLUMN_DATA + " TEXT"
                + ")";
        db.execSQL(CREATE_GASTOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GASTOS);
        onCreate(db);
    }
    public void addGastos(Gastos gastos) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_DESCRICAO, gastos.getDescricao());
            values.put(COLUMN_VALOR, gastos.getValor());
            values.put(COLUMN_CATEGORIA, gastos.getCategoria());
        values.put(COLUMN_DATA, gastos.getData());

        db.insert(TABLE_GASTOS, null, values);
        db.close();
    }

    public List<Gastos> getAllGastos() {
        List<Gastos> listaGastos = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_GASTOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Gastos gastos = new Gastos();
                gastos.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                gastos.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO)));
                gastos.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VALOR)));
                gastos.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA)));
                gastos.setData(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA)));

                listaGastos.add(gastos);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaGastos;
    }
    public void limparGastos() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GASTOS, null, null);
        db.close();
    }
}
