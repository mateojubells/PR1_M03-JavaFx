package com.example.pr1_m03;

import java.util.ArrayList;
import java.util.List;

public class TransactionList {
    private List<Transaction> allTransactions;

    public TransactionList() {
        this.allTransactions = new ArrayList<>();
    }

    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public void addTransaction(Transaction transaction) {
        allTransactions.add(transaction);
    }
}
