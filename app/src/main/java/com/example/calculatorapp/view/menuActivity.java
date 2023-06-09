package com.example.calculatorapp.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.calculatorapp.R;
import com.example.calculatorapp.controller.CameraPreview;
import com.example.calculatorapp.controller.DbContextSqlLite;
import com.example.calculatorapp.databinding.ActivityMenuBinding;
import com.example.calculatorapp.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class menuActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        binding.appBarMenu.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_calculator, R.id.nav_history, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        /*mPreview = new CameraPreview(this);

        FrameLayout previewLayout = findViewById(R.id.camera_preview);
        previewLayout.addView(mPreview);*/

        DbContextSqlLite dbContextSqlLite = new DbContextSqlLite(this);
        SQLiteDatabase db = dbContextSqlLite.getWritableDatabase();

        User user = dbContextSqlLite.getUser();
        if (user != null) {
            NavigationView nav = findViewById(R.id.nav_view);
            View headerView = nav.getHeaderView(0);

            TextView pName = headerView.findViewById(R.id.profileName);
            TextView pEmail = headerView.findViewById(R.id.profileEmail);

            pName.setText(user.getName());
            pEmail.setText(user.getEmail());
        }

        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void goToHistoryActivity(MenuItem item) {
        Intent intent = new Intent(menuActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    public void goToCalculatorActivity(MenuItem item) {
        Intent intent = new Intent(menuActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void goToSettingsActivity(MenuItem item) {
        Intent intent = new Intent(menuActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}