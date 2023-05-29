package com.example.test;

public class PlanModel {

    Long date;
    Long month;
    Long year;
    String TypePlan, Today , StartDate, Summa;

    public PlanModel(String StartDate, String summa, String typePlan, String today,Long date, Long month, Long year) {
        this.StartDate = StartDate;
        this.Summa = summa;
        this.TypePlan = typePlan;
        this.Today = today;
        this.date = date;
        this.month = month;
        this.year = year;
    }
}
