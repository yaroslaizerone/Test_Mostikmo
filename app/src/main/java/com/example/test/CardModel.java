package com.example.test;

import android.widget.ImageView;

public class CardModel {
    String namemoney;
    String typemoney;
    String valutmoney;
    String scoremoney;
    int bankimage;

    public CardModel(String namemoney, int bankimage, String typemoney, String valutmoney, String scoremoney) {
        this.namemoney = namemoney;
        this.bankimage = bankimage;
        this.typemoney = typemoney;
        this.valutmoney = valutmoney;
        this.scoremoney = scoremoney;
    }
}
