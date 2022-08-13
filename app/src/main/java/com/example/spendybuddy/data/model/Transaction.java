package com.example.spendybuddy.data.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.UUID;

public class Transaction implements Serializable {
    private String id;
    private double amount;
    private String account_id;
    private TransactionType transactionType;
    private String Date;
    private String description;
    private String imageUri;

    public double getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction(double amount, String account_id, TransactionType transactionType, String date, String description) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.account_id = account_id;
        this.transactionType = transactionType;
        Date = date;
        this.description = description;
    }

    public Transaction(double amount, String account_id, TransactionType transactionType, String date) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.account_id = account_id;
        this.transactionType = transactionType;
        Date = date;
        this.description = "";
    }

    // for firebase fetches
    public Transaction() {
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImage() {
        return imageUri;
    }

    public void setImage(String image) {
        this.imageUri = image;
    }

    public Transaction(double amount, String account_id, TransactionType transactionType, String date, String imageUri,String description) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.account_id = account_id;
        this.transactionType = transactionType;
        Date = date;
        this.imageUri = imageUri;
        this.description = description;
    }

    public Transaction(double amount, String id, String account_id, TransactionType transactionType, String date) {
        this.id = id;
        this.amount = amount;
        this.account_id = account_id;
        this.transactionType = transactionType;
        Date = date;
    }
}
