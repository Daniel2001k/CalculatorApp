package com.example.calculatorapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.calculatorapp.R;
import com.example.calculatorapp.controller.DbContextSqlLite;
import com.example.calculatorapp.model.MathOperation;

import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private LinearLayout dataLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DbContextSqlLite dbContextSqlLite = new DbContextSqlLite(this);
        SQLiteDatabase db = dbContextSqlLite.getWritableDatabase();

        //MathOperation mathOperation = new MathOperation("2 + 2");

        //dbContextSqlLite.addData(mathOperation);
        List<MathOperation> operations = dbContextSqlLite.getData();

        dataLinearLayout = findViewById(R.id.linear_layout_history);

        for (MathOperation operation : operations) {
            TextView textView = new TextView(this);
            textView.setText(operation.toString());
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            dataLinearLayout.addView(textView);
        }

        db.close();
    }
}