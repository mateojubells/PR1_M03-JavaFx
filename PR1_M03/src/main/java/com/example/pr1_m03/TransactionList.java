package com.example.pr1_m03;

import com.example.pr1_m03.Transaction;

import javax.json.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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
        saveTransactionsToJson();
    }

    public void saveTransactionsToJson() {
        Path path = Paths.get("Transactions.json");
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (Transaction transaction : allTransactions) {
            jsonArrayBuilder.add(convertToJson(transaction));
        }

        try (OutputStream os = new FileOutputStream(path.toFile());
             JsonWriter jsonWriter = Json.createWriter(os)) {
            JsonArray jsonArray = jsonArrayBuilder.build();
            jsonWriter.writeArray(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTransactionsFromJson() {
        Path path = Paths.get("transactions.json");

        try (InputStream is = new FileInputStream(path.toFile());
             JsonReader jsonReader = Json.createReader(is)) {
            JsonArray jsonArray = jsonReader.readArray();
            for (JsonValue jsonValue : jsonArray) {
                JsonObject jsonObject = jsonValue.asJsonObject();
                LocalDate date = LocalDate.parse(jsonObject.getString("date"));
                String category = jsonObject.getString("category");
                double amount = jsonObject.getJsonNumber("amount").doubleValue();
                String description = jsonObject.getString("description");

                Transaction transaction = new Transaction(date, category, amount, description);
                allTransactions.add(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject convertToJson(Transaction transaction) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("date", transaction.getDate().toString());
        jsonObjectBuilder.add("category", transaction.getCategory());
        jsonObjectBuilder.add("amount", transaction.getAmount());
        jsonObjectBuilder.add("description", transaction.getDescription());
        return jsonObjectBuilder.build();
    }
}
