package com.example.taller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PlanetActivity extends Activity {
    private String URLService = "https://swapi.co/api/planets/";
    private TextView[] views;
    private int currentIndex;
    private Button buttonNext;
    private Button buttonBack;
    private int n = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);

        getN();
        views = new TextView[9];
        views[0] = findViewById(R.id.name_planet);
        views[1] = findViewById(R.id.rotation_planet);
        views[2] = findViewById(R.id.orbital_planet);
        views[3] = findViewById(R.id.diameter_planet);
        views[4] = findViewById(R.id.climate_planet);
        views[5] = findViewById(R.id.gravity_planet);
        views[6] = findViewById(R.id.terrain_planet);
        views[7] = findViewById(R.id.surface_planet);
        views[8] = findViewById(R.id.population_planet);
        currentIndex = 1;
        callService(currentIndex);

        buttonNext = findViewById(R.id.button_next_planet);
        buttonBack = findViewById(R.id.button_back_planet1);

        buttonBack.setOnClickListener(view -> {
            if(currentIndex == 1) return;
            currentIndex--;
            callService(currentIndex);
        });


        buttonNext.setOnClickListener(view -> {
            if(currentIndex == n) return;
            currentIndex++;
            callService(currentIndex);
        });


    }

    private void callService(int index) {
        AsyncTask.execute(() -> {

            try {
                URL urlService = new URL (URLService + index + "/");
                HttpsURLConnection connection =  (HttpsURLConnection) urlService.openConnection();
                connection.setRequestMethod("GET");
                InputStream responseBody = connection.getInputStream();

                if (connection.getResponseCode() == 200) {
                    // Success


                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginObject(); // Start processing the JSON object

                    for(int i = 0; i < views.length; i++){
                        String name = jsonReader.nextName();
                        String value = jsonReader.nextString();
                        int j = i;
                        views[i].post(() -> views[j].setText(value));
                        Log.v("INFO",value);
                    }

                } else {
                    // Error handling code goes here
                    Log.v("ERROR", "ERROR");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void getN() {
        AsyncTask.execute(() -> {

            try {
                URL urlService = new URL (URLService);
                HttpsURLConnection connection =  (HttpsURLConnection) urlService.openConnection();
                connection.setRequestMethod("GET");
                InputStream responseBody = connection.getInputStream();

                if (connection.getResponseCode() == 200) {
                    // Success


                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginObject(); // Start processing the JSON object

                    for(int i = 0; i < views.length; i++){
                        String name = jsonReader.nextName();
                        String value = jsonReader.nextString();
                        n = Integer.parseInt(value.trim());
                        return;
                    }

                } else {
                    // Error handling code goes here
                    Log.v("ERROR", "ERROR");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
