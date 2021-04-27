package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

            Gson gson = new Gson();
            mountains = gson.fromJson(s,Mountain[].class);

            for (int i = 0; i < mountains.length; i++)  {
                Log.d("MainActivity", "Hittade berg " + mountains[i].getName());
            }

        }catch (Exception e){
            Log.e("MainActivity","Something went wrong when reading textfile:\n\n"+ e.getMessage());
        }
    }
}
