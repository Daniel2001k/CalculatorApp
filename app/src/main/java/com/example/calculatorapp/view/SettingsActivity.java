package com.example.calculatorapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.calculatorapp.R;
import com.example.calculatorapp.controller.DbContextSqlLite;
import com.example.calculatorapp.model.User;

public class SettingsActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail;
    private ImageView imageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        imageViewProfile = findViewById(R.id.imageViewProfile);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
    }
    public void onSaveProfileButtonClick(View view) {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Imię i email są wymagane", Toast.LENGTH_SHORT).show();
            return;
        }

        DbContextSqlLite dbContextSqlLite = new DbContextSqlLite(this);
        SQLiteDatabase db = dbContextSqlLite.getWritableDatabase();

        User user = new User(name, email);

        dbContextSqlLite.addUser(user);

        db.close();

        Toast.makeText(this, "Profil zapisany", Toast.LENGTH_SHORT).show();
    }
}