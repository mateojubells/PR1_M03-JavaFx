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


    public void addTransaction(Transaction transaction) {
        Path filePath = Paths.get("Transactions.json");

        allTransactions.add(transaction);
        saveTransactionsToJson(filePath);
    }

    public void saveTransactionsToJson(Path path) {
        OutputStream os = null;
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonWriter jsonWriter = null;

        try {
            for (Transaction transaction : allTransactions) {
                jsonArrayBuilder.add(convertToJson(transaction));
            }

            os = new FileOutputStream(path.toFile());
            jsonWriter = Json.createWriter(os);
            jsonWriter.writeArray(jsonArrayBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jsonWriter != null) {
                jsonWriter.close();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadTransactionsFromJson(Path path) {
        InputStream is = null;
        JsonReader jsonReader = null;
        try {
            is = new FileInputStream(path.toFile());
            jsonReader = Json.createReader(is);
            JsonArray jsonArray = jsonReader.readArray();

            for (JsonValue jsonValue : jsonArray) {
                JsonObject jsonObject = jsonValue.asJsonObject();
                LocalDate date = LocalDate.parse(jsonObject.getString("date"));
                String category = jsonObject.getString("category");
                double amount = jsonObject.getJsonNumber("amount").doubleValue();
                String description = jsonObject.getString("description");

                Transaction transaction = new Transaction(category, amount, description, date);
                allTransactions.add(transaction);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (jsonReader != null) {
                jsonReader.close();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ... Otros métodos de la clase

    public JsonObject convertToJson(Transaction transaction) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("date", transaction.getDate().toString());
        jsonObjectBuilder.add("category", transaction.getCategory());
        jsonObjectBuilder.add("amount", transaction.getAmount());
        jsonObjectBuilder.add("description", transaction.getDescription());
        return jsonObjectBuilder.build();
    }
}