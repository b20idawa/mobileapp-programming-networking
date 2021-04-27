package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Mountain[] mountains;
    ArrayAdapter<Mountain> adapter;
    private ArrayList<Mountain> mountainArrayList;



    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mountainArrayList=new ArrayList<Mountain>();
        adapter=new ArrayAdapter<>(this,R.layout.listview_item,R.id.listview_item_xml,mountainArrayList);

        ListView listview  = findViewById(R.id.my_listview);
        listview.setAdapter(adapter);

        try{
            InputStream is = getApplicationContext().getAssets().open("json/mountain.json");
            String s = convertStreamToString(is);
            Log.d("MainActivity","The following text was found in textfile:\n\n"+s);

            new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");

            Gson gson = new Gson();
            mountains = gson.fromJson(s,Mountain[].class);

            for (int i = 0; i < mountains.length; i++)  {
                Log.d("MainActivity", "Hittade berg " + mountains[i].getName());
                mountainArrayList.add(mountains[i]);
            }
        }catch (Exception e){
            Log.e("MainActivity","Something went wrong when reading textfile:\n\n"+ e.getMessage());
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection = null;
        private BufferedReader reader = null;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            Log.d("TAG", json);
            Gson gson = new Gson();
            mountains = gson.fromJson(json,Mountain[].class);
            adapter=new ArrayAdapter<Mountain>(MainActivity.this,R.layout.listview_item,mountains);

            ListView listview  = findViewById(R.id.my_listview);
            listview.setAdapter(adapter);

        }
    }
}