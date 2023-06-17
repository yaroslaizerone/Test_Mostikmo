package com.example.test.Fragments.Analystic;

public class AnalysticModel {
    public AnalysticModel(Long date, Long month, Long year, Long summa, String category) {
        this.date = date;
        this.month = month;
        this.year = year;
        this.summa = summa;
        this.category = category;
    }

    Long date, month, year, summa;
    String category;
}
