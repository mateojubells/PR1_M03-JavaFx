package com.example.pr1_m03;

import java.util.ArrayList;

public class Expenses {
    private ArrayList<Expense> expenses;

    public Expenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    private void addExpense(Expense e){
        expenses.add(e);
    }
}
