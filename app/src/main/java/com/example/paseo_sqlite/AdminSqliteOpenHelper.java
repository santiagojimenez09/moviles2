package com.example.paseo_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSqliteOpenHelper extends SQLiteOpenHelper {
    public AdminSqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table TblFinca(Codigo text primary key,Tipo text,Cantidad text,Transporte text)");
        sqLiteDatabase.execSQL("create table TblVehiculo(Placa text primary key,Modelo text,Marca text,Valor text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists TblFinca");
        sqLiteDatabase.execSQL("create table TblFinca(Codigo text primary key,Tipo text,Cantidad text,Transporte text)");
        sqLiteDatabase.execSQL("drop table if exists TblVehiculo");
        sqLiteDatabase.execSQL("create table TblVehiculo(Placa text primary key,Modelo text,Marca text,Valor text)");
    }
}
