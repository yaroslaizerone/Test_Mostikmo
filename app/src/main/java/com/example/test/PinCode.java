package com.example.test;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
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
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PinCode extends AppCompatActivity {

    String pincode = "";
    TextView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0;
    ImageView pon1, pon2, pon3, pon4;
    ImageButton clearbt, cheakbt;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    ArrayList<Type> mArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        num3 = (TextView) findViewById(R.id.num3);
        num4 = (TextView) findViewById(R.id.num4);
        num5 = (TextView) findViewById(R.id.num5);
        num6 = (TextView) findViewById(R.id.num6);
        num7 = (TextView) findViewById(R.id.num7);
        num8 = (TextView) findViewById(R.id.num8);
        num9 = (TextView) findViewById(R.id.num9);
        num0 = (TextView) findViewById(R.id.num0);

        pon1 = (ImageView) findViewById(R.id.point1);
        pon2 = (ImageView) findViewById(R.id.point2);
        pon3 = (ImageView) findViewById(R.id.point3);
        pon4 = (ImageView) findViewById(R.id.point4);

        clearbt = (ImageButton) findViewById(R.id.clearpin);
        cheakbt = (ImageButton) findViewById(R.id.acceptinput);

        clearbt.setOnClickListener(v->clearsumbolpincode());
        cheakbt.setOnClickListener(v->Cheakpincode());

        num1.setOnClickListener(v-> inputpincode("1"));
        num2.setOnClickListener(v-> inputpincode("2"));
        num3.setOnClickListener(v-> inputpincode("3"));
        num4.setOnClickListener(v-> inputpincode("4"));
        num5.setOnClickListener(v-> inputpincode("5"));
        num6.setOnClickListener(v-> inputpincode("6"));
        num7.setOnClickListener(v-> inputpincode("7"));
        num8.setOnClickListener(v-> inputpincode("8"));
        num9.setOnClickListener(v-> inputpincode("9"));
        num0.setOnClickListener(v-> inputpincode("0"));
    }

    void inputpincode(String num){
        if (pincode.length()<4){
            pincode+=num;
        }
        viewpincodecontrol();
    }

    void clearsumbolpincode(){
        if (pincode.length() > 0) {
            pincode = pincode.substring(0, pincode.length() - 1);
        }
        viewpincodecontrol();
    }

    void viewpincodecontrol(){
        switch (pincode.length()){
            case 0:
                pon1.setImageResource(R.drawable.roundbutton);
                pon2.setImageResource(R.drawable.roundbutton);
                pon3.setImageResource(R.drawable.roundbutton);
                pon4.setImageResource(R.drawable.roundbutton);
                break;
            case 1:
                pon1.setImageResource(R.drawable.roundbuttoninput);
                pon2.setImageResource(R.drawable.roundbutton);
                pon3.setImageResource(R.drawable.roundbutton);
                pon4.setImageResource(R.drawable.roundbutton);
                break;
            case 2:
                pon1.setImageResource(R.drawable.roundbuttoninput);
                pon2.setImageResource(R.drawable.roundbuttoninput);
                pon3.setImageResource(R.drawable.roundbutton);
                pon4.setImageResource(R.drawable.roundbutton);
                break;
            case 3:
                pon1.setImageResource(R.drawable.roundbuttoninput);
                pon2.setImageResource(R.drawable.roundbuttoninput);
                pon3.setImageResource(R.drawable.roundbuttoninput);
                pon4.setImageResource(R.drawable.roundbutton);
                break;
            case 4:
                pon1.setImageResource(R.drawable.roundbuttoninput);
                pon2.setImageResource(R.drawable.roundbuttoninput);
                pon3.setImageResource(R.drawable.roundbuttoninput);
                pon4.setImageResource(R.drawable.roundbuttoninput);
                break;
        }
    }
    //TODO Сделать проверку ввода пинкода и так ЧТОБЫ МОЖНО БЫЛО УЙТИ С ЭКРАНА ЕСЛИ НЕ ТВОЙ АККАУНТ
    //TODO СДЕЛАТЬ СБРОС ПИНКОДА
    void Cheakpincode(){
        CollectionReference pinRef = db.collection("pin");
        pinRef.whereEqualTo("email", user.getEmail()).whereEqualTo("pincode", pincode).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                startActivity(new Intent(PinCode.this,MainActivity.class));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        //startActivity(new Intent(PinCode.this,MainActivity.class));
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(PinCode.this, LoginActivity.class));
        }else{
            Log.i("User",user.getEmail());
            //startActivity(new Intent(PinCode.this, MainActivity.class));
        }
    }
}