package com.azamat.jsondemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        task.execute("http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=84455d709fd781ad630d96172e812c88");
        //84455d709fd781ad630d96172e812c88
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        String content ="";
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuilder stringBuilder = new StringBuilder();

        @Override
        protected String doInBackground(String... urls) {

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    stringBuilder.append(current);
                    data = reader.read();
                }
                content = stringBuilder.toString();

                return content;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("JSON" + s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String line = jsonObject.getString("list");
                System.out.println("JSON OBJECT" + line);

                JSONArray arr = new JSONArray(line);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject part = arr.getJSONObject(i);
                    System.out.println("main" + part.getString("main"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}