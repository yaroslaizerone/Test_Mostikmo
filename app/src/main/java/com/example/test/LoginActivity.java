package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email_input,password_input;
    Button signin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin = findViewById(R.id.buttoninput);
        email_input =  findViewById(R.id.InputUserEmail);
        password_input = findViewById(R.id.editTextTextPassword);

        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(view ->{
            loginUser();
        });
    }
    private void loginUser(){
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();

        if(TextUtils.isEmpty(email)){
            email_input.setError("А ГдУ почта?");
            email_input.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            password_input.setError("А ГдУ пароль?");
            password_input.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged in successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Registration Error:"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}