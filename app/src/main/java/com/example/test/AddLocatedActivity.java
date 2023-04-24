package com.example.test;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddLocatedActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    ImageView btBack;
    EditText NameLocation;
    TextView SaveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_located);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        btBack = findViewById(R.id.BackActiv);
        NameLocation = findViewById(R.id.LocationET);
        SaveText= findViewById(R.id.saveBT);

        btBack.setOnClickListener(v-> Backctivity());
        SaveText.setOnClickListener(v-> SaveLocation());
    }
    void SaveLocation(){
        //TODO Сделать добавление новой локации,Cделать документ с логикой для хранения данных для каждого пользователя
        String LocationTitle = NameLocation.getText().toString();
        Map<String, Object> locationuser = new HashMap<>();
        locationuser.put("email", user.getEmail());
        locationuser.put("nameLocation", LocationTitle);

        db.collection("userLocated")
                .add(locationuser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddLocatedActivity.this, "Локация была успешно добавленна.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    void Backctivity(){
        finish();
    }
}