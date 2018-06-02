package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        List<String> ingredientsList = new ArrayList<>();
        List<String> alsoKnownList = new ArrayList<>();

        try {
            //Creating a base JSON Response from the String.
            JSONObject baseJsonResponse = new JSONObject(json);

            JSONObject name = baseJsonResponse.getJSONObject("name");
            String mainName = name.getString("mainName");

            JSONArray synonyms = name.getJSONArray("alsoKnownAs");
            for (int c = 0; c < synonyms.length(); c++) {
                String synonym = synonyms.getString(c);
                alsoKnownList.add(synonym);
            }

            String placeOfOrigin = baseJsonResponse.getString("placeOfOrigin");

            String description = baseJsonResponse.getString("description");

            String image = baseJsonResponse.getString("image");

            JSONArray ingredients = baseJsonResponse.getJSONArray("ingredients");
            for (int c = 0; c < ingredients.length(); c++) {
                String ingredient = ingredients.getString(c);
                ingredientsList.add(ingredient);
            }

            //Returns newly created sandwich object.
            return new Sandwich(mainName, alsoKnownList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
