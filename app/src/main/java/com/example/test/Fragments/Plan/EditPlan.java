package com.example.test.Fragments.Plan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.Fragments.Operation.EditOperationsActivity;
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
import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditPlan extends AppCompatActivity {
    String TypeOper, TP, tPlan, Today, summa;
    CalendarView Dateplan;
    Calendar calndr = Calendar.getInstance();
    Long day = Long.valueOf(calndr.get(Calendar.DATE));
    Long mouth = Long.valueOf(calndr.get(Calendar.MONTH));
    Long year = Long.valueOf(calndr.get(Calendar.YEAR));
    private int tPlanNum;
    ImageView back;
    TextView summaPlan, EditTX, DeleteTX;
    RadioGroup typePlanR;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);

        typePlanR = findViewById(R.id.radioGroup2);
        EditTX = findViewById(R.id.savecl);
        DeleteTX = findViewById(R.id.savecl2);
        summaPlan = findViewById(R.id.CostInput);
        Dateplan = findViewById(R.id.calendarView);

        back = findViewById(R.id.BackActiv);
        back.setOnClickListener(v -> {finish();});

        Intent i = getIntent();

        Today = i.getStringExtra("TODAY");
        tPlan = i.getStringExtra("TYPEPLAN");
        summa = i.getStringExtra("SUMMA");

        summaPlan.setText(summa);
        switch (tPlan){
            case "-":
                tPlanNum = R.id.Rashod;
                TP = "Расход";
                break;
            case "+":
                tPlanNum = R.id.Dohod;
                TP = "Доход";
                break;
        }
        typePlanR.check(tPlanNum);
        Dateplan.setDate(Calendar.getInstance().getTimeInMillis(),false,true);
        EditTX.setOnClickListener(v -> UpdateRecord());
        DeleteTX.setOnClickListener(v -> DeleteRecord());
        Dateplan.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int yearD, int month, int dayOfMonth) {
                day = Long.valueOf(dayOfMonth);
                mouth = Long.valueOf(month+1);
                year = Long.valueOf(yearD);
            }
        });
    }
    private void UpdateRecord(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        switch(typePlanR.getCheckedRadioButtonId()){
            case R.id.Rashod:
                TypeOper = "Расход";
                break;
            case R.id.Dohod:
                TypeOper = "Доход";
                break;
        }

        CollectionReference itemsRef = db.collection("UserPlans");
        Query docRef = itemsRef.whereEqualTo("email", user.getEmail()).whereEqualTo("today", Today)
                .whereEqualTo("summa", Integer.parseInt(summa)).whereEqualTo("typeplan", TP);
        //Обновление записи
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        itemsRef.document(document.getId()).update(
                                "typeplan",TypeOper,
                                "summa",Integer.parseInt(String.valueOf(summaPlan.getText())),
                                "dateplan", day,
                                "mouthplan", mouth,
                                "yearplan", year);
                        Toast.makeText(EditPlan.this, "Запись обновлена!", Toast.LENGTH_SHORT).show();
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
        CollectionReference itemsRef = db.collection("UserPlans");
        Query docRef = itemsRef.whereEqualTo("email", user.getEmail()).whereEqualTo("today", Today)
                .whereEqualTo("summa", Integer.parseInt(summa)).whereEqualTo("typeplan", TypeOper);
        //Удаление записи
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        itemsRef.document(document.getId()).delete();
                        Toast.makeText(EditPlan.this, "Запись удалена!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Log.d("BAN", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}