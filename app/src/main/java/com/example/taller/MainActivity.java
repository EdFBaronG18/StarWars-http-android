package com.example.taller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.URL;

public class MainActivity extends Activity {

    private Button peopleButton;
    private Button planetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        peopleButton = findViewById(R.id.people_button);
        planetButton = findViewById(R.id.planet_button);

        peopleButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PeopleActivity.class);
            startActivity(intent);
        });

        planetButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, PlanetActivity.class);
            startActivity(intent);
        });
    }

}
