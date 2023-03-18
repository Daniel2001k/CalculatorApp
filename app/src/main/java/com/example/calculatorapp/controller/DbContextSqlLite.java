package com.example.calculatorapp.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.calculatorapp.model.MathOperation;
import com.example.calculatorapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class DbContextSqlLite extends SQLiteOpenHelper {

    public DbContextSqlLite(Context context) {
        super(context, "myDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + MathOperation.TABLE_NAME +
                " (" + MathOperation.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MathOperation.COLUMN_OPERATION + " TEXT, " +
                MathOperation.COLUMN_INSERTED_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + User.TABLE_NAME + "(" +
                User.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                User.COLUMN_NAME + " TEXT, " +
                User.COLUMN_EMAIL + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addData(MathOperation mathOperation) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MathOperation.COLUMN_OPERATION, mathOperation.getOperation());
        values.put(MathOperation.COLUMN_INSERTED_DATE, mathOperation.getInsertedDate());

        db.insert(MathOperation.TABLE_NAME, null, values);
        db.close();
    }

    public List<MathOperation> getData() {
        List<MathOperation> operationsList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MathOperation.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String operation = cursor.getString(cursor.getColumnIndex(MathOperation.COLUMN_OPERATION));
                @SuppressLint("Range") String insertedDateStr = cursor.getString(cursor.getColumnIndex(MathOperation.COLUMN_INSERTED_DATE));

                MathOperation op = new MathOperation(operation, insertedDateStr);
                operationsList.add(op);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return operationsList;
    }

    public User getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + User.TABLE_NAME + " ORDER BY " + User.COLUMN_ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME));
        @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(User.COLUMN_EMAIL));

        User user = new User(name, email);

        cursor.close();
        db.close();
        return user;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(User.TABLE_NAME, null, null);

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NAME, user.getName());
        values.put(User.COLUMN_EMAIL, user.getEmail());

        db.insert(User.TABLE_NAME, null, values);
        db.close();
    }
}
