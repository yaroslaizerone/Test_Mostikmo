package com.example.test.Fragments.Operation;

public class OperationModel {

    String comment;
    String dateoperation;
    String location;
    String namemoney;
    String summa;
    String category;
    String typeOperation;

    public OperationModel(String comment, String dateoperation, String location, String namemoney, String summa, String category, String typeOperation) {
        this.comment = comment;
        this.dateoperation = dateoperation;
        this.location = location;
        this.namemoney = namemoney;
        this.summa = summa;
        this.category = category;
        this.typeOperation = typeOperation;
    }
}
