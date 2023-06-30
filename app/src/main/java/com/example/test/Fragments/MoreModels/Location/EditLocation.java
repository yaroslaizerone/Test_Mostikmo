package com.example.test.Fragments.MoreModels.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.Fragments.Card.EditCard;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class EditLocation extends AppCompatActivity {

    private int typeNum = 0;
    TextView nameLocation, deleteRecord, updateRecord;
    Spinner typeLocation;
    ImageView back;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        back = findViewById(R.id.BackActiv);
        back.setOnClickListener(v -> {finish();});

        nameLocation = findViewById(R.id.LocationET);
        typeLocation = findViewById(R.id.typespin);
        Intent i = getIntent();
        String type = i.getStringExtra("TYPE");

        switch(type){
            case "Дети":
                typeNum = 0;
                break;
            case "Забота о себе":
                typeNum = 1;
                break;
            case "Зарплата":
                typeNum = 2;
                break;
            case "Продукты":
                typeNum = 3;
                break;
            case "Кафе и рестораны":
                typeNum = 4;
                break;
            case "Корректировка":
                typeNum = 5;
                break;
            case "Машина":
                typeNum = 6;
                break;
            case "Образование":
                typeNum = 7;
                break;
            case "Отдых и развлечения":
                typeNum = 8;
                break;
            case "Платежи и комиссии":
                typeNum = 9;
                break;
            case "Подарки":
                typeNum = 10;
                break;
            case "Покупки: одежда, техника":
                typeNum = 11;
                break;
            case "Проезд":
                typeNum = 12;
                break;
            case "Здоровье и фитнес":
                typeNum = 13;
                break;
        }
        typeLocation.setSelection(typeNum);
        nameLocation.setText(i.getStringExtra("NAME"));

        updateRecord = findViewById(R.id.savecl);
        deleteRecord = findViewById(R.id.savecl2);

        updateRecord.setOnClickListener(v -> UpdateRecord(i.getStringExtra("NAME")));
        deleteRecord.setOnClickListener(v -> DeleteRecord(i.getStringExtra("NAME")));
    }

    private void UpdateRecord(String name){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        CollectionReference itemsRef = db.collection("userLocated");
        Query docRef = itemsRef.whereEqualTo("email", user.getEmail()).whereEqualTo("nameLocation", name);
        //Обновление записи
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot document : task.getResult()) {
                        itemsRef.document(document.getId()).update(
                                "nameLocation",String.valueOf(nameLocation.getText()),
                                "typeLocation",typeLocation.getSelectedItem().toString());
                        Toast.makeText(EditLocation.this, "Запись обновлена!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Log.d("BAN", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    private void DeleteRecord(String name){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        CollectionReference itemsRef = db.collection("userLocated");
        Query docRef = itemsRef.whereEqualTo("email", user.getEmail()).whereEqualTo("nameLocation", name);
        //Удаление записи
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        itemsRef.document(document.getId()).delete();
                        Toast.makeText(EditLocation.this, "Запись удалена!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Log.d("BAN", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}