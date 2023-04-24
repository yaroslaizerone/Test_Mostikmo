package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class CurrencySelectionActivity extends AppCompatActivity {

    Button saveButton;
    RadioButton rbRussRub, rbDollar, rbEvro, rbTenge, rbYuan, rbFunt;
    TextView Rub, Dollar, Evro, Tenge, Yuan, Funt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_selection);
    //TODO сделать обработчик для изменения цвета текста выбранной валюты
        // TODO Сделать добавление и проверку на наличие прошлых записей валют
        // TODO Придумать, что делать с валютами
    }
}