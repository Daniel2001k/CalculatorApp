package com.example.calculatorapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MathOperation {
    public static String TABLE_NAME = "math_operation";
    public static String COLUMN_ID = "id";
    public static String COLUMN_OPERATION = "operation";
    public static String COLUMN_INSERTED_DATE = "inserted_date";
    private String Operation;
    private String InsertedDate;

    public MathOperation(String operation) {
        this.Operation = operation;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.InsertedDate = formatter.format(LocalDateTime.now());
    }

    public MathOperation(String operation, String insertedDateStr) {
        this.Operation = operation;
        this.InsertedDate = insertedDateStr;
    }


    public String getOperation() {
        return Operation;
    }

    public String getInsertedDate() {
        return InsertedDate;
    }


    @Override
    public String toString() {
        return "Operation: " + Operation +
                "\nDate: " + InsertedDate + '\n';
    }
}
