package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText email_input,password_input,repitpassword_input;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Button buttonsingup = findViewById(R.id.Singup);
        email_input = findViewById(R.id.editTextTextEmailAddress);
        password_input = findViewById(R.id.editTextTextPassword);
        repitpassword_input = findViewById(R.id.Repitpassword);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();

        buttonsingup.setOnClickListener(view ->{
            createUser();
        });
    }
    private void createUser(){
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();

        if(TextUtils.isEmpty(email)){
            email_input.setError("А ГдУ почта?");
            email_input.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            password_input.setError("А ГдУ пароль?");
            password_input.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User registered successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error:"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}