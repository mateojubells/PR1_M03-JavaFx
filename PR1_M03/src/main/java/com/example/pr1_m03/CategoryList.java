package com.example.pr1_m03;

import javax.json.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class CategoryList {
    private List<Category> allCategories = new ArrayList<>();

    private static final String JSON_FILE_PATH = "categories.json";
    public CategoryList() {
        Path jsonFilePath = Paths.get(JSON_FILE_PATH);
        if (Files.exists(jsonFilePath)) {
            loadCategoriesFromJson(jsonFilePath);
        } else {
            allCategories = new ArrayList<>();
        }
    }

    public void addCategories(Category category) {
        allCategories.add(category);
        saveCategoryToJson(Paths.get(JSON_FILE_PATH));
    }
    private void saveCategoryToJson(Path path)  {
        OutputStream os = null;
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonWriter jsonWriter = null;
        InputStream is = null;

        try {
            if (Files.exists(path)) {
                is = new FileInputStream(path.toFile());
                JsonReader existingJsonReader = Json.createReader(is);
                JsonArray existingJsonArray = existingJsonReader.readArray();

                for (JsonValue jsonValue : existingJsonArray) {
                    jsonArrayBuilder.add(jsonValue);
                }
            }

            // Agregar solo la última categoría al archivo JSON
            Category lastCategory = allCategories.get(allCategories.size() - 1);
            jsonArrayBuilder.add(convertToJson(lastCategory));

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


    private void loadCategoriesFromJson(Path path) {
        InputStream is = null;
        JsonReader jsonReader = null;
        try {
            is = new FileInputStream(path.toFile());
            jsonReader = Json.createReader(is);
            JsonArray jsonArray = jsonReader.readArray();

            for (JsonValue jsonValue : jsonArray) {
                JsonObject jsonObject = jsonValue.asJsonObject();
                String name = jsonObject.getString("name");

                Category category = new Category(name);
                allCategories.add(category);
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


    public JsonObject convertToJson(Category category) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("name", category.getName());
        return jsonObjectBuilder.build();
    }
}
