package com.example.imtpmd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> pills = Arrays.asList("hallo", "hoi", "boe", "yoyo", "anouk", "nynke", "test", "goedemiddag", "lijst", "uitbreiden");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
