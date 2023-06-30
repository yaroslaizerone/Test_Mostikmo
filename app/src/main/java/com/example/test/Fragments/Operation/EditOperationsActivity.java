package com.example.test.Fragments.Operation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.Fragments.Card.EditCard;
import com.example.test.Fragments.MoreModels.Location.EditLocation;
import com.example.test.Fragments.MoreModels.Location.LocationAdapter;
import com.example.test.Fragments.MoreModels.Location.LocationClass;
import com.example.test.Fragments.MoreModels.Location.LocationListActivity;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditOperationsActivity extends AppCompatActivity {

    private int operationNum = 0;
    private int categoryNum = 0;
    private int day, mouth, year = 0;
    private List<String> locationUser = new ArrayList<String>();
    private List<String> scoreUser = new ArrayList<String>();
    String score, TypeOper, TypeCategory, comment, locationOperation, namemoney, category, typeOperation, date;
    TextView commentCard, scorevalut;
    Button editbt, deletebt;
    Spinner card, location;
    RadioGroup typeOperationR, categoryOperationR;
    DatePicker dateOperation;
    ImageView back;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_operations);

        back = findViewById(R.id.BackActiv);
        back.setOnClickListener(v -> {finish();});

        commentCard = findViewById(R.id.commentUser);
        card = findViewById(R.id.typeMoney);
        location = findViewById(R.id.typeUserLocation);
        typeOperationR = findViewById(R.id.radioGroup3);
        categoryOperationR = findViewById(R.id.radioGroup4);
        dateOperation = findViewById(R.id.datePickerOperation);
        scorevalut = findViewById(R.id.InputCoast);
        editbt = findViewById(R.id.EditBT);
        deletebt = findViewById(R.id.DeleteBT);
        Intent i = getIntent();
        score = i.getStringExtra("SUMMA");
        comment = i.getStringExtra("COMMENT");
        locationOperation = i.getStringExtra("LOCATION");
        namemoney = i.getStringExtra("NAMEMONEY");
        category = i.getStringExtra("CATEGORY");
        typeOperation = i.getStringExtra("TYPEOPERATION");
        date = i.getStringExtra("DATE");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        //Взять данные о его названиях карт
        db.collection("userLocated")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                locationUser.add(document.getString("nameLocation"));
                            }
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(EditOperationsActivity.this,
                                    android.R.layout.simple_spinner_item, locationUser);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            location.setAdapter(adapter1);
                            location.setSelection(locationUser.indexOf(locationOperation));
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
                                scoreUser.add(document.getString("namemoney"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditOperationsActivity.this,
                                    android.R.layout.simple_spinner_item, scoreUser);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            card.setAdapter(adapter);
                            card.setSelection(scoreUser.indexOf(namemoney));
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                    }
                });

        commentCard.setText(comment);
        scorevalut.setText(score);

        switch (typeOperation){
            case "-":
                operationNum = R.id.Rashod;
                break;
            case "+":
                operationNum = R.id.Dohod;
                break;
        }
        typeOperationR.check(operationNum);

        switch (category){
            case "Дети":
                categoryNum = R.id.r1;
                break;
            case "Забота о себе":
                categoryNum = R.id.r2;
                break;
            case "Зарплата":
                categoryNum = R.id.r3;
                break;
            case "Продукты":
                categoryNum = R.id.r4;
                break;
            case "Кафе и рестораны":
                categoryNum = R.id.r5;
                break;
            case "Корректировка":
                categoryNum = R.id.r6;
                break;
            case "Машина":
                categoryNum = R.id.r7;
                break;
            case "Образование":
                categoryNum = R.id.r8;
                break;
            case "Банк":
                categoryNum = R.id.r9;
                break;
            case "Отдых и развлечения":
                categoryNum = R.id.r10;
                break;
            case "Платежи и комиссии":
                categoryNum = R.id.r11;
                break;
            case "Подарки":
                categoryNum = R.id.r12;
                break;
            case "Покупки: одежда, техника":
                categoryNum = R.id.r13;
                break;
            case "Проезд":
                categoryNum = R.id.r14;
                break;
            case "Здоровье и фитнес":
                categoryNum = R.id.r15;
                break;
        }
        categoryOperationR.check(categoryNum);
        String[] separated = date.split("\\.");
        day = Integer.parseInt(separated[0]);
        mouth = Integer.parseInt(separated[1]);
        year = Integer.parseInt(separated[2]);
        dateOperation.updateDate(year,mouth-1,day);

        editbt.setOnClickListener(v -> UpdateRecord());
        deletebt.setOnClickListener(v -> DeleteRecord());
    }
    private void UpdateRecord(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        int day = dateOperation.getDayOfMonth();
        int month = dateOperation.getMonth() + 1;
        int year = dateOperation.getYear();
        switch(typeOperationR.getCheckedRadioButtonId()){
            case R.id.Rashod:
                TypeOper = "Расход";
                break;
            case R.id.Dohod:
                TypeOper = "Доход";
                break;
        }
        switch(categoryOperationR.getCheckedRadioButtonId()){
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
                TypeCategory = "Банк";
                break;
            case R.id.r10:
                TypeCategory = "Отдых и развлечения";
                break;
            case R.id.r11:
                TypeCategory = "Платежи и комиссии";
                break;
            case R.id.r12:
                TypeCategory = "Подарки";
                break;
            case R.id.r13:
                TypeCategory = "Покупки: одежда, техника";
                break;
            case R.id.r14:
                TypeCategory = "Проезд";
                break;
            case R.id.r15:
                TypeCategory = "Здоровье и фитнес";
                break;
        }
        int summaUp = Integer.parseInt(scorevalut.getText().toString());
        CollectionReference itemsRef = db.collection("userOperation");
        Query docRef = itemsRef.whereEqualTo("email", user.getEmail()).whereEqualTo("moneyoperation", namemoney)
                .whereEqualTo("summa", Integer.parseInt(score)).whereEqualTo("locationoperation", locationOperation);
        //Обновление записи
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        itemsRef.document(document.getId()).update(
                                "typeoperation",TypeOper,
                                "summa",summaUp,
                                "typecategoty", TypeCategory,
                                "moneyoperation", card.getSelectedItem().toString(),
                                "locationoperation", location.getSelectedItem().toString(),
                                "dateoperation", day,
                                "monthoperation", month,
                                "yearoperation", year,
                                "comment", commentCard.getText().toString());
                        Toast.makeText(EditOperationsActivity.this, "Запись обновлена!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Log.d("BAN", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    private void DeleteRecord(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        CollectionReference itemsRef = db.collection("userOperation");
        Query docRef = itemsRef.whereEqualTo("email", user.getEmail()).whereEqualTo("moneyoperation", namemoney)
                .whereEqualTo("summa", Integer.parseInt(score)).whereEqualTo("locationoperation", locationOperation);
        //Удаление записи
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        itemsRef.document(document.getId()).delete();
                        Toast.makeText(EditOperationsActivity.this, "Запись удалена!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Log.d("BAN", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}