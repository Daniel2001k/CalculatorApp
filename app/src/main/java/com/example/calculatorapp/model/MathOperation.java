package com.example.calculatorapp.model;

import java.time.LocalDateTime;
import java.util.Date;

public class MathOperation {
    public static String TABLE_NAME = "math_operation";
    public static String COLUMN_ID = "id";
    public static String COLUMN_OPERATION = "operation";
    public static String COLUMN_INSERTED_DATE = "inserted_date";
    public int Id;
    public String Operation;
    public Date InsertedDate;

    public MathOperation(String operation, Date insertedDate) {
        this.Operation = operation;
        this.InsertedDate = insertedDate;
    }

    public MathOperation(int id, String operation, Date insertedDate) {

        this.Id = id;
        this.Operation = operation;
        this.InsertedDate = insertedDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public Date getInsertedDate() {
        return InsertedDate;
    }

    public void setInsertedDate(Date insertedDate) {
        InsertedDate = insertedDate;
    }
}
