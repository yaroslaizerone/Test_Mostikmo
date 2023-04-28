package com.example.test;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddPlansActivity extends AppCompatActivity {

    ImageView Back;
    TextView saveBT;
    EditText costIp;
    CalendarView DataPlans;
    RadioGroup RGroup;
    String summa, typeplan, date, email;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plans);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        Back = findViewById(R.id.BackActiv);
        saveBT = findViewById(R.id.addPlanClick);
        costIp = findViewById(R.id.CostInput);
        DataPlans = findViewById(R.id.calendarView);
        RGroup = findViewById(R.id.radioGroup2);

        Back.setOnClickListener(v -> BackActivity());
        saveBT.setOnClickListener(v -> addPlan());
        DataPlans.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "." + month + "." + year;
            }
        });
    }
    void BackActivity(){
        finish();
    }
    void addPlan(){
        summa = costIp.getText().toString();
        switch(RGroup.getCheckedRadioButtonId()){
            case R.id.Rashod:
                typeplan = "Расход";
                break;
            case R.id.Dohod:
                typeplan = "Доход";
                break;
        }
        email = user.getEmail();

        Map<String, Object> planuser = new HashMap<>();
        planuser.put("email", email);
        planuser.put("typeplan", typeplan);
        planuser.put("dateplan", date);
        planuser.put("summa", summa);

        db.collection("UserPlans")
                .add(planuser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddPlansActivity.this, "Запись была успешно добавленна.", Toast.LENGTH_SHORT).show();
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