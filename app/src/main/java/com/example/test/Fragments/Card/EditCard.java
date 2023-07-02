package com.example.test.Fragments.Card;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class EditCard extends AppCompatActivity {

    private int typeNum = 0;
    private int valutNum = 0;
    private int bankNum = 0;
    TextView nameCard, scorevalut, updateRecord, deleteRecord;
    Spinner typeCard, valutCard, bankCard;
    ImageView back;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        back = findViewById(R.id.BackActiv);
        back.setOnClickListener(v -> {finish();});
        Intent i = getIntent();

        String type = i.getStringExtra("TYPE");
        String valut = i.getStringExtra("VALUT");
        int bank = i.getIntExtra("BANK",0);

        nameCard = findViewById(R.id.Inputnamecard);
        scorevalut = findViewById(R.id.scoreEdit);
        updateRecord = findViewById(R.id.savecl);
        deleteRecord = findViewById(R.id.savecl2);

        typeCard = findViewById(R.id.typespin);
        valutCard = findViewById(R.id.spinValut);
        bankCard = findViewById(R.id.spinBank);

        nameCard.setText(i.getStringExtra("NAME"));
        scorevalut.setText(i.getStringExtra("SCORE"));

        switch(type){
            case "Карта":
                typeNum = 0;
                break;
            case "Наличные":
                typeNum = 1;
                break;
            case "Счёт":
                typeNum = 2;
                break;
        }

        switch(valut){
            case "₽":
                valutNum = 0;
                break;
            case "$":
                valutNum = 1;
                break;
            case "£":
                valutNum = 2;
                break;
            case "¥":
                valutNum = 3;
                break;
            case "€":
                valutNum = 4;
                break;
            case "Br":
                valutNum = 5;
                break;
            case "₣":
                valutNum = 6;
                break;
            case "Dh":
                valutNum = 7;
                break;
        }

        switch (bank){
            case R.drawable.sber:
                bankNum = 0;
                break;
            case R.drawable.alfa:
                bankNum = 2;
                break;
            case R.drawable.tintkoff:
                bankNum = 3;
                break;
            case R.drawable.vtb:
                bankNum = 1;
                break;
        }
        typeCard.setSelection(typeNum);
        valutCard.setSelection(valutNum);
        bankCard.setSelection(bankNum);

        updateRecord.setOnClickListener(v -> UpdateRecord(i.getStringExtra("NAME")));
        deleteRecord.setOnClickListener(v -> DeleteRecord(i.getStringExtra("NAME")));
    }
    private void UpdateRecord(String name){
        if(scorevalut.getText().length() == 0 || nameCard.getText().length() == 0){
            Toast.makeText(this,"Заполните необходимые поля", Toast.LENGTH_SHORT).show();
        }
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        CollectionReference itemsRef = db.collection("userScore");
        Query docRef = itemsRef.whereEqualTo("email", user.getEmail()).whereEqualTo("namemoney", name);
        //Обновление записи
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        itemsRef.document(document.getId()).update(
                                "namemoney",String.valueOf(nameCard.getText()),
                                "scoremoney",String.valueOf(scorevalut.getText()) ,
                                "typebank", bankCard.getSelectedItem().toString(),
                                "typecard",typeCard.getSelectedItem().toString(),
                                "typevalut",valutCard.getSelectedItem().toString()
                        );
                        Toast.makeText(EditCard.this, "Запись обновлена!", Toast.LENGTH_SHORT).show();
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
        CollectionReference itemsRef = db.collection("userScore");
        Query docRef = itemsRef.whereEqualTo("email", user.getEmail()).whereEqualTo("namemoney", name);
        //Удаление записи
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        itemsRef.document(document.getId()).delete();
                        Toast.makeText(EditCard.this, "Запись удалена!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Log.d("BAN", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}