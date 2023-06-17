package com.example.test.PinCodeRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    EditText email_input,password_input;
    Button signin;
    FirebaseAuth mAuth;
    TextView Regist, respass;
    ImageButton show_password;
    public static int j = 0;

    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin = findViewById(R.id.buttoninput);
        email_input =  findViewById(R.id.InputUserEmail);
        password_input = findViewById(R.id.editTextTextPassword);
        Regist = findViewById(R.id.registText);
        respass = findViewById(R.id.resetpassword);
        show_password = findViewById(R.id.ShowPassword);

        mAuth = FirebaseAuth.getInstance();

        Regist.setOnClickListener(view ->{
          startActivity(new Intent(this,RegisterActivity.class));
        });
        respass.setOnClickListener(view ->{
            String email = email_input.getText().toString();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if(email_input.length() != 0 & Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Utility.showToast(LoginActivity.this, "Письмо для изменения пароля было отправлено к вам на почту.");
                            }
                        });
            }
            else {
                email_input.setError("Неверный вид e-mail!");
            }
        });
        signin.setOnClickListener(view ->{
            loginUser();
        });

        show_password.setOnClickListener(view ->{
            ShowPasswordVisible();
        });
    }
    private void loginUser(){
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();
        if(TextUtils.isEmpty(email)){
            email_input.setError("Неверный формат почты");
            email_input.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            password_input.setError("Введите пароль!");
            password_input.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Аунтификация прошла успешно",Toast.LENGTH_SHORT).show();
                        j++;
                        startActivity(new Intent(LoginActivity.this, PinCode.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Ошибка Аунтификации:"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    // Метод для показа\скрытия пароля
    public void ShowPasswordVisible() {
        if (i >= 1){
            show_password.setImageResource(R.drawable.seepassword);
            password_input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            i--;
        } else {
            password_input.setInputType(129);
            show_password.setImageResource(R.drawable.notpassword);
            i++;
        }
    }
}