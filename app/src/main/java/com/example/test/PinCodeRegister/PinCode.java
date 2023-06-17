package com.example.test.PinCodeRegister;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;

import android.content.Intent;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.test.MainActivity;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.test.PinCodeRegister.LoginActivity.j;

import java.util.concurrent.Executor;

public class PinCode extends AppCompatActivity {

    String pincode = "";
    TextView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0, respin, backLog;
    ImageView pon1, pon2, pon3, pon4;
    ImageButton clearbt, cheakbt;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    int o;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

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
        respin = findViewById(R.id.resretpincode);
        backLog = findViewById(R.id.backtologin);

        pon1 = findViewById(R.id.point1);
        pon2 = findViewById(R.id.point2);
        pon3 = findViewById(R.id.point3);
        pon4 = findViewById(R.id.point4);

        clearbt = findViewById(R.id.clearpin);
        cheakbt = findViewById(R.id.acceptinput);

        clearbt.setOnClickListener(v->clearsumbolpincode());
        cheakbt.setOnClickListener(v->BionetricInput());

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
        respin.setOnClickListener(v-> Resetpincode());
        backLog.setOnClickListener(v -> BackToLogin());

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                break;
        }
        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(PinCode.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PinCode.this, MainActivity.class));
                o++;
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Приложите палец к сканеру")
                .setDescription("чтобы разблокировать Mosticsmo").setDeviceCredentialAllowed(true)
                .build();
        biometricPrompt.authenticate(promptInfo);
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
                Cheakpincode();
                break;
        }
    }
    void BackToLogin(){
        startActivity(new Intent(this,LoginActivity.class));
    }
    void Resetpincode(){
        startActivity(new Intent(this, CreatePinCode.class));
    }
    void BionetricInput() {
        biometricPrompt.authenticate(promptInfo);
    }
    void Cheakpincode(){
        CollectionReference pinRef = db.collection("pin");
        pinRef.whereEqualTo("email", user.getEmail()).whereEqualTo("pincode", pincode).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                startActivity(new Intent(PinCode.this,MainActivity.class));
                                o++;
                                finish();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        if (o<1){
            pinRef.whereEqualTo("email", user.getEmail()).whereNotEqualTo("pincode", pincode).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                pincode = "";
                                viewpincodecontrol();
                            } else {
                                pincode = "";
                                viewpincodecontrol();
                            }
                        }
                    });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(PinCode.this, LoginActivity.class));
        }else{
            if (j == 0) {
                respin.setVisibility(View.GONE);
            }else{
                respin.setVisibility(View.VISIBLE);
            }
        }
    }
}