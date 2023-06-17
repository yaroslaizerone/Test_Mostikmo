package com.example.test.Fragments.MoreModels.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.test.R;

public class CategoryListActivity extends AppCompatActivity {
    ImageView Backbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Backbt = findViewById(R.id.imageView);
        Backbt.setOnClickListener(v -> finish());
    }
}