package com.example.pr1_m03;

import java.time.LocalDate;

public class Earning {
    public LocalDate date;
    public String category;
    public double amount;
    public String description;

    public Earning(LocalDate date, String category, double amount, String description) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    // Getters y setters

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Método toString para representar el gasto como una cadena
    @Override
    public String toString() {
        return String.format("[%s] %s - %.2f USD: %s", date, category, amount, description);
    }
}
