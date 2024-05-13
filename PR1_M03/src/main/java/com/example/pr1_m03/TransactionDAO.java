package com.example.pr1_m03;

import java.util.List;

public interface TransactionDAO {
    void saveTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();

    void updateTransaction(Transaction transaction); // Nuevo método
}
