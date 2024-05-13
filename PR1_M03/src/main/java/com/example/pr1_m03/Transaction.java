package com.example.pr1_m03;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private String category;
    private double amount;
    private String description;
    private LocalDate date;

    public Transaction(int id, String category, double amount, String description, LocalDate date) {
        this.id = id;  // Establecer el ID
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }



    // Getters y setters
    public int getId() {  // Getter para el ID
        return id;
    }

    public void setId(int id) {  // Setter para el ID (si es necesario)
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Date: [%s], Category: %s, Amount: %.2f, Description: %s",
                id, date, category, amount, description);
    }
}
