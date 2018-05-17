package com.example.nathanshumm.decided.model.api;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PlacesTask extends AsyncTask<String, Integer, String> {

    String data = null;
    private ArrayList<Place> placeList;
    ParserTask parserTask = new ParserTask();

    // Invoked by execute() method of this object
    @Override
    protected String doInBackground(String... url) {
        try {
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }

        // Start parsing the Google places in JSON format
        // Invokes the "doInBackground()" method of the class ParserTask
        return data;
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(String result) {
        parserTask.execute(result);

        this.placeList = new ArrayList<>(parserTask.getPlaceList());
        Log.e("result", result);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("URL Download Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public ArrayList<Place> getPlaceList(){
        return this.placeList;
    }
}
