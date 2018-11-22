package com.example.edu.restapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String weather = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.buttonLondon)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonLondon) {
            OpenWeatherAPITask task = new OpenWeatherAPITask();
            try {
                weather = task.execute("London","Seoul").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ((TextView)findViewById(R.id.textView)).setText(weather);
        }
    }


    class OpenWeatherAPITask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String weather = null;
            String id = params[0];
            String urlString = "http://api.openweathermap.org/data/2.5/weather"
                    + "?q="+id+"q=Seoul"+"&appid=04bb4cada1eaf80679b5339609879080";
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                byte[] buffer = new byte[1000];
                int result = in.read(buffer);
                weather = new String(buffer);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return weather;
        }
    }
}
