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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CreatePinCode extends AppCompatActivity {

    String pincode = "";
    TextView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0;
    ImageView pon1, pon2, pon3, pon4;
    ImageButton clearbt, createbt;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin_code);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
        num0 = findViewById(R.id.num0);

        pon1 = findViewById(R.id.point1);
        pon2 = findViewById(R.id.point2);
        pon3 = findViewById(R.id.point3);
        pon4 = findViewById(R.id.point4);

        clearbt = findViewById(R.id.clearpin);
        createbt = findViewById(R.id.acceptinput);

        clearbt.setOnClickListener(v->clearsumbolpincode());
        createbt.setOnClickListener(v->createpincode());

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

    void createpincode (){
        //TODO СДЕЛАТЬ ПРОВЕРКУ НА НАЛИЧИЕ У ДАННОГО ПОЛЬЗОВАТЕЛЯ ПИНКОДА И ЕСЛИ ЕСТЬ ТО ОБНОВЛЯТЬ ЕГО
        Map<String, Object> pincodeuser = new HashMap<>();
        pincodeuser.put("email", user.getEmail());
        pincodeuser.put("pincode", pincode);

        CollectionReference pinRef = db.collection("pin");
        //Если у данного пользователя ранее не было пин-кода
        pinRef.whereNotEqualTo("email", user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            db.collection("pin").document(user.getEmail()).set(pincodeuser);
                                /*db.collection("pin").add(pincodeuser)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                Toast.makeText(getApplicationContext(),"Готово", Toast.LENGTH_SHORT).show();
                                            }
                                        });*/
                            Toast.makeText(CreatePinCode.this, "Пин-код был привязан к вашему аккаунту", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreatePinCode.this,MainActivity.class));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Если у данного пользователя ранее был пин-код
        pinRef.whereEqualTo("email", user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            db.collection("cities").document(user.getEmail()).update(pincodeuser);
                            Toast.makeText(CreatePinCode.this, "Пин-код для " + user.getEmail() + " был изменён", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreatePinCode.this,MainActivity.class));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        startActivity(new Intent(this,MainActivity.class));
    }

}