package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "concierto.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Crear tabla Recinto
        val CREATE_RECINTO_TABLE = ("CREATE TABLE Recinto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "capacidad INTEGER," +
                "direccion TEXT)")
        db?.execSQL(CREATE_RECINTO_TABLE)

        // Crear tabla Concierto
        val CREATE_CONCIERTO_TABLE = ("CREATE TABLE Concierto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "recinto_id INTEGER," +
                "artista TEXT," +
                "fecha_concierto TEXT," +
                "FOREIGN KEY(recinto_id) REFERENCES Recinto(id))")
        db?.execSQL(CREATE_CONCIERTO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Concierto")
        db?.execSQL("DROP TABLE IF EXISTS Recinto")
        onCreate(db)
    }
}
