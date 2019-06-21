package com.example.imtpmd;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MedicineViewModel medicineViewModel;

    private List<String> pills = Arrays.asList("hallo", "hoi", "boe", "yoyo", "anouk", "nynke", "test", "goedemiddag", "lijst", "uitbreiden");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button naarhome = (Button) findViewById(R.id.naarhome);

        naarhome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            }
        });

        TextView searchmatches = (TextView)findViewById(R.id.matchPills);
        searchmatches.setMovementMethod(new ScrollingMovementMethod());
        for(int i = 0; i < pills.size(); i++){
            searchmatches.append(pills.get(i) + "\n");
        }

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart){
            showStartScreen();
        }


        SearchView searchView = (SearchView) findViewById(R.id.searchview);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        Log.d("searchsubmit", s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        Log.d("searchchange", s);
                        findMatches(s);
                        return false;
                    }
                }
        );

        Log.d("tessst", "tessst");

//        NetworkManager.getInstance(this).getRequest("http://136.144.230.97:8090/api/medication?api_token=CilZjPDfkHDmb29qcJkqBS7bB2cup9T7Onqcmfaqt027QhvpqBhFvLinJ6Dp", new VolleyCallback(){
//            @Override
//            public void onSuccess(String result) { //de anonieme klasse gaat nu dingen doen
////                Log.d("tessst", "HET WERKT");
//            Gson gson = new Gson();
//            testApi testpi = gson.fromJson(result, testApi.class);
//
//            Log.d("tessst", result);
//
//            }
//        });

//        NetworkManager.getInstance(getApplicationContext()).getRequest("http://136.144.230.97:8080/api/userinfo/anouk?api_token=rx7Mi675A1WDEvZPsGnrgvwkCEeOKlrX7rIPoXocluBKnupp9A02OLz7QcSL", new VolleyCallback(){
//            @Override
//            public void onSuccess(String result) { //de anonieme klasse gaat nu dingen doen
//                Gson gson = new Gson();
//
//                testApi testpi = gson.fromJson(result, testApi.class);
//
//                Log.d("tessst", testpi.getName());
//                Log.d("tessst", "tessst");
//
//           }
//        });

    }

    private void showStartScreen() {
        Intent myIntent = new Intent(getBaseContext(), WelcomeActivity.class);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        startActivity(myIntent);
    }

    private void findMatches(String search){
        TextView searchmatches = (TextView)findViewById(R.id.matchPills);
        searchmatches.setText("");

        for(int i = 0; i < pills.size() ; i++){
            if (pills.get(i).startsWith(search)){
                searchmatches.append(pills.get(i) + "\n");
                Log.d("pills",pills.get(i));
            }
        }
    }

    private void insertIntoDatabase(String name, int milligram, int time, Boolean isChecked){
        Medicine m = new Medicine(name, milligram, time, isChecked);

        medicineViewModel.insert(m);

        Log.d("Medicine", "Naar de database geschreven");
    }
}
