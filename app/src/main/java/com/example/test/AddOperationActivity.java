package com.example.test;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOperationActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    ImageView btBack;
    DatePicker DataOperations;
    RadioGroup TypeoperationGroup, TypeCategoryGroup;
    EditText coment, summa;
    Spinner typeMoney, typeLocation;
    Button saveOperation;
    List<String> locationList = new ArrayList<String>();
    List<String> moneyList = new ArrayList<String>();
    String TypeOper, TypeCategory;

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
        db.collection("userScore")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("User", document.getId() + " => " + document.getData());
                                moneyList.add(document.getString("namemoney"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddOperationActivity.this,
                                    android.R.layout.simple_spinner_item, moneyList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            typeMoney.setAdapter(adapter);
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                    }
                });

        TypeoperationGroup = findViewById(R.id.radioGroup3);
        TypeCategoryGroup = findViewById(R.id.radioGroup4);
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
        switch(TypeoperationGroup.getCheckedRadioButtonId()){
            case R.id.Rashod:
                TypeOper = "Расход";
                break;
            case R.id.Dohod:
                TypeOper = "Доход";
                break;
        }
        switch(TypeCategoryGroup.getCheckedRadioButtonId()){
            case R.id.r1:
                TypeCategory = "Дети";
                break;
            case R.id.r2:
                TypeCategory = "Забота о себе";
                break;
            case R.id.r3:
                TypeCategory = "Зарплата";
                break;
            case R.id.r4:
                TypeCategory = "Продукты";
                break;
            case R.id.r5:
                TypeCategory = "Кафе и рестораны";
                break;
            case R.id.r6:
                TypeCategory = "Корректировка";
                break;
            case R.id.r7:
                TypeCategory = "Машина";
                break;
            case R.id.r8:
                TypeCategory = "Образование";
                break;
            case R.id.r9:
                TypeCategory = "Отдых и развлечения";
                break;
            case R.id.r10:
                TypeCategory = "Платежи и комиссии";
                break;
            case R.id.r11:
                TypeCategory = "Подарки";
                break;
            case R.id.r12:
                TypeCategory = "Покупки: одежда, техника";
                break;
            case R.id.r13:
                TypeCategory = "Проезд";
                break;
            case R.id.r14:
                TypeCategory = "Здоровье и фитнес";
                break;
        }
        int day = DataOperations.getDayOfMonth();
        int month = DataOperations.getMonth() + 1;
        int year = DataOperations.getYear();
        String Opercomment = coment.getText().toString();
        int summaoperation = Integer.parseInt(summa.getText().toString());
        Map<String, Object> userOperation = new HashMap<>();
        userOperation.put("email", user.getEmail());
        userOperation.put("comment", Opercomment);
        userOperation.put("typeoperation",TypeOper);
        userOperation.put("summa",summaoperation);
        userOperation.put("typecategoty", TypeCategory);
        userOperation.put("moneyoperation", typeMoney.getSelectedItem().toString());
        userOperation.put("locationoperation", typeLocation.getSelectedItem().toString());
        userOperation.put("dateoperation", day);
        userOperation.put("monthoperation", month);
        userOperation.put("yearoperation", year);

        db.collection("userOperation")
                .add(userOperation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddOperationActivity.this, "Операция была успешно добавленна.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}