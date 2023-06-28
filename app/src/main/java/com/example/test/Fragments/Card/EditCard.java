package com.example.test.Fragments.Card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;

public class EditCard extends AppCompatActivity {

    private int typeNum = 0;
    private int valutNum = 0;
    private int bankNum = 0;
    private int sber = R.drawable.sber;
    private int alfa = R.drawable.alfa;
    private int tin = R.drawable.tintkoff;
    private int vtb = R.drawable.vtb;
    TextView nameCard, scorevalut;
    Spinner typeCard, valutCard, bankCard;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        back = findViewById(R.id.BackActiv);
        back.setOnClickListener(v -> {finish();});
        Intent i = getIntent();

        String type = i.getStringExtra("TYPE");
        String valut = i.getStringExtra("VALUT");
        int bank = i.getIntExtra("BANK",0);

        nameCard = findViewById(R.id.Inputnamecard);
        scorevalut = findViewById(R.id.scoreEdit);

        typeCard = findViewById(R.id.typespin);
        valutCard = findViewById(R.id.spinValut);
        bankCard = findViewById(R.id.spinBank);



        nameCard.setText(i.getStringExtra("NAME"));
        scorevalut.setText(i.getStringExtra("SCORE"));

        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, valut, Toast.LENGTH_SHORT).show();
        switch(type){
            case "Карта":
                typeNum = 0;
                break;
            case "Наличные":
                typeNum = 1;
                break;
            case "Счёт":
                typeNum = 2;
                break;
        }

        switch(valut){
            case "₽":
                valutNum = 0;
                break;
            case "$":
                valutNum = 1;
                break;
            case "£":
                valutNum = 2;
                break;
            case "¥":
                valutNum = 3;
                break;
            case "€":
                valutNum = 4;
                break;
            case "Br":
                valutNum = 5;
                break;
            case "₣":
                valutNum = 6;
                break;
            case "Dh":
                valutNum = 7;
                break;
        }

        switch (bank){
            case R.drawable.sber:
                bankNum = 0;
                break;
            case R.drawable.alfa:
                bankNum = 2;
                break;
            case R.drawable.tintkoff:
                bankNum = 3;
                break;
            case R.drawable.vtb:
                bankNum = 1;
                break;
        }
        typeCard.setSelection(typeNum);
        valutCard.setSelection(valutNum);
        bankCard.setSelection(bankNum);
    }
}