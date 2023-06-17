package com.example.test.Fragments.MoreModels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;

import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CurrencySelectionActivity extends AppCompatActivity {

    Document doc;
    private Thread secThread;
    private Runnable runnable;
    ImageView Back;
    TextView usdbuy, usdsell, eurbuy, eursell, cnybuy, cnysell, bynbuy, bynsell, gbpbuy, gbpsell, chfbuy, chfsell, aedbuy, aedsell;
    String usbuy, ussell, erbuy, ersell, cnbuy, cnsell, bybuy, bysell, gbbuy, gbsell, chbuy, chsell, aebuy, aesell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_selection);
        Back = findViewById(R.id.BackActiv);

        usdbuy = findViewById(R.id.dollarBuy);
        usdsell = findViewById(R.id.dollarSell);
        eurbuy = findViewById(R.id.eurBuy);
        eursell = findViewById(R.id.eurSell);
        cnybuy = findViewById(R.id.cnyBuy);
        cnysell = findViewById(R.id.cnySell);
        bynbuy = findViewById(R.id.bynBuy);
        bynsell = findViewById(R.id.bynSell);
        gbpbuy = findViewById(R.id.gbpBuy);
        gbpsell = findViewById(R.id.gbpSell);
        chfbuy = findViewById(R.id.chfBuy);
        chfsell = findViewById(R.id.chfSell);
        aedbuy = findViewById(R.id.aedBuy);
        aedsell = findViewById(R.id.aedSell);

        Back.setOnClickListener(v -> {
            Backctivity();
        });
        init();

        //ВСЁ ГАВНО ОСТАВИТЬ ТОЛЬКО РУБЛЬ, ХОТЯ ВСё ХУИСОСИНА ПО РОФЛУ ДЕЛАЕШЬ СТРАНИЦУ ГДЕ ЕСТЬ
        //АКТУАЛЬНАЯ ИНФОРМАЦИЯ О КУРСАХ ВАЛЮТ.
    }
    private void init(){
        runnable = new Runnable() {
            @Override
            public void run() {
                GetWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
    }
    private void GetWeb(){
        try {
            doc = Jsoup.connect("https://novosibirsk.bankiros.ru/currency").get();
            Elements tables = doc.getElementsByTag("tbody");
            //Выбор таблицы
            Element our_table = tables.get(0);
            Elements elements_from_tavble = our_table.children();
            //Выбор элемента строки
            for(int i = 2; i < 9; i++) {
                Element valut = elements_from_tavble.get(i);
                Elements valut_element = valut.children();
                String valutname = valut_element.get(0).text();
                Element coastbuy = valut_element.get(1);
                Elements coastbuy_element = coastbuy.children();
                Element coastsell = valut_element.get(2);
                Elements coastsell_element = coastsell.children();
                Element sell = coastsell_element.get(0);
                Elements co = sell.children();
                Element buy = coastbuy_element.get(0);
                Elements cos = buy.children();
                Pattern decimalNumPattern = Pattern.compile("[0-9]+(.[0-9]+)?");
                Matcher matcher;
                switch (valutname){
                    case "USD":
                        matcher = decimalNumPattern.matcher(coastbuy_element.get(0).text());
                        while (matcher.find()) {
                            usbuy = cos.get(0).text();
                        }
                        matcher = decimalNumPattern.matcher(coastsell_element.get(0).text());
                        while (matcher.find()) {
                            ussell = co.get(0).text();
                        }
                        ussell = coastsell_element.get(0).text().substring(0,5);
                        break;
                    case "EUR":
                        matcher = decimalNumPattern.matcher(coastbuy_element.get(0).text());
                        while (matcher.find()) {
                            erbuy = cos.get(0).text();
                        }
                        matcher = decimalNumPattern.matcher(coastsell_element.get(0).text());
                        while (matcher.find()) {
                            ersell = co.get(0).text();
                        }
                        break;
                    case "CNY":
                        matcher = decimalNumPattern.matcher(coastbuy_element.get(0).text());
                        while (matcher.find()) {
                            cnbuy = cos.get(0).text();
                        }
                        matcher = decimalNumPattern.matcher(coastsell_element.get(0).text());
                        while (matcher.find()) {
                            cnsell = co.get(0).text();
                        }
                        break;
                    case "BYN":
                        matcher = decimalNumPattern.matcher(coastbuy_element.get(0).text());
                        while (matcher.find()) {
                            bybuy = cos.get(0).text();
                        }
                        matcher = decimalNumPattern.matcher(coastsell_element.get(0).text());
                        while (matcher.find()) {
                            bysell = co.get(0).text();
                        }
                        break;
                    case "GBP":
                        matcher = decimalNumPattern.matcher(coastbuy_element.get(0).text());
                        while (matcher.find()) {
                            gbbuy = cos.get(0).text();
                        }
                        matcher = decimalNumPattern.matcher(coastsell_element.get(0).text());
                        while (matcher.find()) {
                            gbsell = co.get(0).text();
                        }
                        break;
                    case "CHF":
                        matcher = decimalNumPattern.matcher(coastbuy_element.get(0).text());
                        while (matcher.find()) {
                            chbuy = cos.get(0).text();
                        }
                        matcher = decimalNumPattern.matcher(coastsell_element.get(0).text());
                        while (matcher.find()) {
                            chsell = co.get(0).text();
                        }
                        break;
                    case "AED":
                        matcher = decimalNumPattern.matcher(coastbuy_element.get(0).text());
                        while (matcher.find()) {
                            aebuy = cos.get(0).text();
                        }
                        matcher = decimalNumPattern.matcher(coastsell_element.get(0).text());
                        while (matcher.find()) {
                            aesell = co.get(0).text();
                        }
                        break;
                }
            }
        }catch(IOException exception){
            exception.printStackTrace();
        }
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                usdbuy.setText(usbuy);
                usdsell.setText(ussell);
                eurbuy.setText(erbuy);
                eursell.setText(ersell);
                cnybuy.setText(cnbuy);
                cnysell.setText(cnsell);
                bynbuy.setText(bybuy);
                bynsell.setText(bysell);
                gbpbuy.setText(gbbuy);
                gbpsell.setText(gbsell);
                chfbuy.setText(chbuy);
                chfsell.setText(chsell);
                aedbuy.setText(aebuy);
                aedsell.setText(aesell);
            }
        });
    }
    void Backctivity(){
        finish();
    }
}
