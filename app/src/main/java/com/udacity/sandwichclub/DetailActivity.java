package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Toolbar mToolbar;
    private TextView mDescription, mIngredients, mOrigin, mAlsoKnown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Adding a toolbar with an "Up" button to the activity.
        mToolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(sandwich.getMainName());
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Referencing views from XML.
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mOrigin = findViewById(R.id.origin_tv);
        mAlsoKnown = findViewById(R.id.also_known_tv);

        String noData = "No info found.";

        //Binding the data to the views.
        if (!sandwich.getDescription().isEmpty()) {
            mDescription.setText(sandwich.getDescription());
        } else {
            mDescription.setText(noData);
        }

        if (!sandwich.getIngredients().isEmpty()) {
            StringBuilder ingredients = new StringBuilder();
            for (String ingredient : sandwich.getIngredients()) {
                ingredients.append(" + ").append(ingredient).append("\n");
            }
            mIngredients.setText(ingredients);
        } else {
            mIngredients.setText(noData);
        }

        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            mOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            mOrigin.setText(noData);
        }

        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            StringBuilder synonyms = new StringBuilder();
            for (String synonym : sandwich.getAlsoKnownAs()) {
                synonyms.append(" + ").append(synonym).append("\n");
            }
            mAlsoKnown.setText(synonyms);
        } else {
            mAlsoKnown.setText(noData);
        }
    }
}
