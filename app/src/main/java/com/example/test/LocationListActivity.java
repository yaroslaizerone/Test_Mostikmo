package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationListActivity extends AppCompatActivity {
    TextView IntentLocation;
    ImageView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        IntentLocation = findViewById(R.id.GoToAddLocation);
        Back = findViewById(R.id.BackToMainActiv);

        Back.setOnClickListener(v -> {finish();});
        IntentLocation.setOnClickListener(v -> {
            startActivity(new Intent(this,AddLocatedActivity.class));
        });
    }
    //TODO Реализовать удаление локаций
    //TODO Реализовать отображение Локаций + Разметку сделать)
}