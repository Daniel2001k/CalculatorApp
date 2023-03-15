package com.example.calculatorapp.controller;

import static com.example.calculatorapp.model.MathOperation.TABLE_NAME;
import static com.example.calculatorapp.model.MathOperation.COLUMN_ID;
import static com.example.calculatorapp.model.MathOperation.COLUMN_OPERATION;
import static com.example.calculatorapp.model.MathOperation.COLUMN_INSERTED_DATE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.calculatorapp.model.MathOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbContextSqlLite extends SQLiteOpenHelper {

    public DbContextSqlLite(Context context) {
        super(context, "myDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OPERATION + " TEXT, " +
                COLUMN_INSERTED_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addData(MathOperation mathOperation) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_OPERATION, mathOperation.getOperation());
        values.put(COLUMN_INSERTED_DATE, mathOperation.getInsertedDate().toString());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<MathOperation> getData() {
        List<MathOperation> operationsList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String operation = cursor.getString(cursor.getColumnIndex(COLUMN_OPERATION));
                @SuppressLint("Range") String insertedDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_INSERTED_DATE));

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date insertedDate = null;
                try {
                    insertedDate = sdf.parse(insertedDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                MathOperation op = new MathOperation(id, operation, insertedDate);
                operationsList.add(op);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return operationsList;
    }

    public MathOperation getDataById(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_OPERATION, COLUMN_INSERTED_DATE};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        MathOperation op = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String operation = cursor.getString(cursor.getColumnIndex(COLUMN_OPERATION));
            @SuppressLint("Range") String insertedDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_INSERTED_DATE));

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date insertedDate = null;
            try {
                insertedDate = sdf.parse(insertedDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            op = new MathOperation(id, operation, insertedDate);
        }

        cursor.close();
        db.close();

        return op;
    }
}
