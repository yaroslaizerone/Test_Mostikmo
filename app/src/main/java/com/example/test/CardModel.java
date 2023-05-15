package com.example.test;

import android.widget.ImageView;

public class CardModel {
    String namemoney;
    int bankimage;
    String typemoney;
    String valutmoney;
    String scoremoney;
    public String getNamemoney() {
        return namemoney;
    }

    public int getBankimage() {
        return bankimage;
    }

    public String getTypemoney() {
        return typemoney;
    }

    public String getValutmoney() {
        return valutmoney;
    }

    public String getScoremoney() {
        return scoremoney;
    }


    public CardModel(String namemoney, int bankimage, String typemoney, String valutmoney, String scoremoney) {
        this.namemoney = namemoney;
        this.bankimage = bankimage;
        this.typemoney = typemoney;
        this.valutmoney = valutmoney;
        this.scoremoney = scoremoney;
    }
}
