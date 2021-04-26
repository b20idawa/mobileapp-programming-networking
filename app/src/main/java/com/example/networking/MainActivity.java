package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Mountain> mountainArrayList=new ArrayList<>();
    ArrayAdapter<Mountain> adapter=new ArrayAdapter<Mountain>(this,R.layout.listview_item,R.id.listview_item_xml,mountainArrayList);

    public static String
    convertStreamToString(InputStream is) throws Exception {
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

        try{
            InputStream is = getApplicationContext().getAssets().open("mountain.json");
            String s = convertStreamToString(is);
            Log.d("MainActivity","The following text was found in textfile:\n\n"+s);
        }catch (Exception e){
            Log.e("MainActivity","Something went wrong when reading textfile:\n\n"+ e.getMessage());
        }
    }
}
