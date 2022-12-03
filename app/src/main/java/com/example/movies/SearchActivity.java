package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private String movieName;
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Поиск фильма");

        EditText editTextMovieName = findViewById(R.id.editTextMovieName);


        Button searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieName = editTextMovieName.getText().toString().trim();
                if (movieName != null) {
                    Intent intent = SearchingResultsActivity.newIntent(SearchActivity.this, movieName);
                    startActivity(intent);
                } else {
                    Toast.makeText(SearchActivity.this, R.string.enter_movie_name, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }
}