package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddOperationActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    ImageView btBack;
    DatePicker DataOperations;
    RadioGroup TypeoperationGroup, TypeCategory;
    EditText coment, summa;
    Spinner typeMoney, typeLocation;
    Button saveOperation;
    List<String> locationList = new ArrayList<String>();
    List<String> moneyList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operation);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        typeMoney = findViewById(R.id.typeMoney);
        typeLocation = findViewById(R.id.typeUserLocation);
        db.collection("userLocated")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("User", document.getId() + " => " + document.getData());
                                locationList.add(document.getString("nameLocation"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddOperationActivity.this,
                                    android.R.layout.simple_spinner_item, locationList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            typeLocation.setAdapter(adapter);
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection("userLocated")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("User", document.getId() + " => " + document.getData());
                                locationList.add(document.getString("nameLocation"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddOperationActivity.this,
                                    android.R.layout.simple_spinner_item, locationList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            typeLocation.setAdapter(adapter);
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                    }
                });

        TypeoperationGroup = findViewById(R.id.radioGroup3);
        TypeCategory = findViewById(R.id.radioGroup4);
        coment = findViewById(R.id.commentUser);
        summa = findViewById(R.id.InputCoast);
        DataOperations = findViewById(R.id.datePickerOperation);
        saveOperation = findViewById(R.id.saveBT);
        btBack = findViewById(R.id.BackActiv);

        btBack.setOnClickListener(v-> Backctivity());
        saveOperation.setOnClickListener(v -> SaveOperationUser());
    }
    void Backctivity(){
        finish();
    }
    //TODO Сделать запись всех локаций, счетов и дт при первой загрузки приложения и испоьзовать его сдесь для создания новой операции
    void SaveOperationUser(){

    }
}