package com.example.calculatorapp.view;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculatorapp.R;
import com.example.calculatorapp.controller.DbContextSqlLite;
import com.example.calculatorapp.model.MathOperation;

import java.util.Stack;

public class CalculatorActivity extends AppCompatActivity {
    private TextView resultField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        resultField = findViewById(R.id.resultField);
    }

    public void onButtonClear(View view) {
        resultField.setText("");
    }

    public void onButtonAddCharacter(View view) {
        String buttonText = ((Button) view).getText().toString();

        resultField.setText(resultField.getText() + buttonText);
    }

    public void onButtonEqual(View view) {
        String mathOperationS = resultField.getText().toString();

        char[] mathChars = resultField.getText().toString().toCharArray();

        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < mathChars.length; i++) {
            if (mathChars[i] == ' ')
                continue;

            if (mathChars[i] >= '0' && mathChars[i] <= '9') {
                StringBuilder sb = new StringBuilder();
                while (i < mathChars.length && (mathChars[i] >= '0' && mathChars[i] <= '9' || mathChars[i] == '.')) {
                    sb.append(mathChars[i++]);
                }
                values.push(Integer.parseInt(sb.toString()));
                i--;
            }

            else if (mathChars[i] == '(')
                operators.push(mathChars[i]);

            else if (mathChars[i] == ')') {
                while (operators.peek() != '(')
                    values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                operators.pop();
            }

            else if (mathChars[i] == '+' || mathChars[i] == '-' || mathChars[i] == '*' || mathChars[i] == '/') {
                while (!operators.empty() && hasPrecedence(mathChars[i], operators.peek()))
                    values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                operators.push(mathChars[i]);
            }
        }

        while (!operators.empty())
            values.push(applyOp(operators.pop(), values.pop(), values.pop()));

        String result = values.pop().toString();

        resultField.setText(result);

        DbContextSqlLite dbContextSqlLite = new DbContextSqlLite(this);
        SQLiteDatabase db = dbContextSqlLite.getWritableDatabase();

        MathOperation mathOperation = new MathOperation(mathOperationS + " = " + result);

        dbContextSqlLite.addData(mathOperation);
        db.close();
    }

    public static boolean hasPrecedence(char operator1, char operator2) {
        if (operator2 == '(' || operator2 == ')')
            return false;
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-'))
            return false;
        else
            return true;
    }

    public static Integer applyOp(char operator, Integer b, Integer a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Nie można dzielić przez zero");
                return a / b;
        }
        return 0;
    }

    public void onButtonRemoveLast(View view) {
        if (!resultField.getText().toString().isEmpty()) {
            resultField.setText(resultField.getText().toString().substring(0, resultField.getText().toString().length() - 1));
        }
    }
}