package com.example.calculatorapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.calculatorapp.R;
import com.example.calculatorapp.controller.DbContextSqlLite;
import com.example.calculatorapp.model.MathOperation;

import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DbContextSqlLite dbContextSqlLite = new DbContextSqlLite(this);
        SQLiteDatabase db = dbContextSqlLite.getWritableDatabase();

        MathOperation mathOperation = new MathOperation("2 + 2", new Date());

        dbContextSqlLite.addData(mathOperation);
        MathOperation operation = dbContextSqlLite.getDataById(1);

        db.close();
    }
}