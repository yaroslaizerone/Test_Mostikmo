package com.example.test.Fragments.Card;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewMoneyCardActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    ImageView btBack;
    EditText namecard, ScoreCard;
    TextView SaveText;
    Spinner typeV, typeCard, typeBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_money_card);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();


        btBack = findViewById(R.id.BackActiv);
        namecard = findViewById(R.id.Inputnamecard);
        SaveText = findViewById(R.id.savecl);
        typeBank = findViewById(R.id.spinBank);
        typeV = findViewById(R.id.spinValut);
        typeCard = findViewById(R.id.typespin);
        ScoreCard = findViewById(R.id.scoreEdit);

        btBack.setOnClickListener(v-> Backctivity());
        SaveText.setOnClickListener(v-> SaveCard());
    }
    void SaveCard(){
        //TODO Сделать добавление нового счёта, сделать документ с логикой для хранения данных для каждого пользователя
        String nameTitle = namecard.getText().toString();
        Map<String, Object> userMoneySave = new HashMap<>();
        userMoneySave.put("email", user.getEmail());
        userMoneySave.put("namemoney", nameTitle);
        userMoneySave.put("typecard", typeCard.getSelectedItem().toString());
        userMoneySave.put("typevalut", typeV.getSelectedItem().toString());
        userMoneySave.put("typebank", typeBank.getSelectedItem().toString());
        userMoneySave.put("scoremoney",ScoreCard.getText().toString());

        db.collection("userScore")
                .add(userMoneySave)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddNewMoneyCardActivity.this, "Счёт был успешно добавленн.", Toast.LENGTH_SHORT).show();
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
    void Backctivity(){
        finish();
    }
}