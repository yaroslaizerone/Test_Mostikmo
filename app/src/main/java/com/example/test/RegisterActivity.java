package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
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
    ImageButton show_password, show_rpassword;
    int i,j = 1;
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
        show_password = (ImageButton) findViewById(R.id.ShowPassword);
        show_rpassword = (ImageButton) findViewById(R.id.ShowRpassword);

        mAuth = FirebaseAuth.getInstance();

        buttonsingup.setOnClickListener(view ->{
            createUser();
        });
        show_password.setOnClickListener(view ->{
            ShowPasswordVisible();
        });
        show_rpassword.setOnClickListener(view ->{
            ShowRpasswordVisible();
        });
    }
    private void createUser(){
        String email = email_input.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_input.setError("Неверный вид e-mail!");
        }else {
           email = email_input.getText().toString();
        }

        String password = password_input.getText().toString();
        String rpassword = repitpassword_input.getText().toString();

        if (password.length()<6){
            password_input.setError("Пароль не менее 6 символов!");
        }else{
            password = password_input.getText().toString();
        }
        if (!password.equals(rpassword) & password.length() != 0 & rpassword.length() != 0){
            repitpassword_input.setError("Пароли не совпадают!");
        }else{
            password = password_input.getText().toString();
        }

        if(TextUtils.isEmpty(email)){
            email_input.setError("Неверный формат почты.");
            email_input.requestFocus();
        }else if(!password.equals(rpassword) & password.length() != 0 & rpassword.length() != 0){
            repitpassword_input.setError("Пароли не совпадают.");
            repitpassword_input.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            password_input.setError("Введите пероль.");
            password_input.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Пользователь был зарегистрирован. Подтвердите пожалуйста вашу почту.",Toast.LENGTH_SHORT).show();
                        mAuth.getCurrentUser().sendEmailVerification();
                        startActivity(new Intent(RegisterActivity.this, CreatePinCode.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Произошла ошибка:"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
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
    // Метод для показа\скрытия повтора пароля
    public void ShowRpasswordVisible() {
        if (j == 1){
            repitpassword_input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            show_rpassword.setImageResource(R.drawable.seepassword);
            j--;
        } else {
            repitpassword_input.setInputType(129);
            show_rpassword.setImageResource(R.drawable.notpassword);
            j++;
        }
    }
}