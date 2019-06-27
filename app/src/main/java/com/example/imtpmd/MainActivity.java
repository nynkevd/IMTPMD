package com.example.imtpmd;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.lang.Integer.parseInt;


public class MainActivity extends AppCompatActivity {
    private static MedicineViewModel medicineViewModel;
    private static MedicationNameViewModel medicationNameViewModel;

    private ListView medNameListview;
    private SearchView medNameSearchview;
    private ArrayList<String> allMedicationNamesAL = new ArrayList<>();
    ArrayAdapter<String> allMedicationNamesAdapter;
   // private int firstId;
    private Intent in;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DEze database wordt ook in HomeFragment aangemaakt, dus ik weet niet of we het ergens 'globaal' kunnen doen?
//         db = Room
//                .databaseBuilder(getApplicationContext(), AppDatabase.class, "medicine")
//                .allowMainThreadQueries() // Dit moet nog weg!!!
//                .build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
        medicationNameViewModel = ViewModelProviders.of(this).get(MedicationNameViewModel.class);

        in = new Intent(MainActivity.this, PopActivity.class);

         medNameListview = findViewById(R.id.medslistview);
         medNameSearchview = (SearchView) findViewById(R.id.medssearchview);

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
//            public void onSuccess(String result) {
//                medicationCount = parseInt(result);
//            }
//        });

        // HE VOORDAT JE DEZE DOET NOG EVEN EEN TIJD TOEVOEGEN!!!!
//        insertIntoDatabase("testDubbel", 40, getDateTime(12, 0), false , getDate(6, 24), getDate(6, 30));
//        insertIntoDatabase("testDubbel", 40, getDateTime(16, 0), false , getDate(6, 22), getDate(6, 30));
//        insertIntoDatabase("testMiddag155", 40, getDateTime(15, 30), false, getDate(6, 18), getDate(6, 27));
//        insertIntoDatabase("testOchtend9", 40, getDateTime(9, 0), false);
//        insertIntoDatabase("testMiddag155", 40, getDateTime(15, 30), false);
//        insertIntoDatabase("testAvond20", 40, getDateTime(20, 18), false);
//        insertIntoDatabase("testNacht4", 40, getDateTime(4, 0), false);
//        insertIntoDatabase("testOchtend10", 40, getDateTime(10, 50), false);
//        insertIntoDatabase("testMiddag15", 40, getDateTime(15, 0), false);
//        insertIntoDatabase("testAvond22", 40, getDateTime(22, 7), false);
//        insertIntoDatabase("testNacht5", 40, getDateTime(5, 0), false);
//        insertIntoDatabase("testOchtend8", 40, getDateTime(8, 22), false);
//        insertIntoDatabase("testMiddag13", 40, getDateTime(13, 0), false);
//        insertIntoDatabase("testAvond23", 40, getDateTime(23, 31), false);
//        insertIntoDatabase("testNacht2", 40, getDateTime(2, 0), false);


        medicationNameViewModel.getAllMedicationNames().observe(this, new Observer<List<MedicationName>>() {
           @Override
           public void onChanged(@Nullable final List<MedicationName> meds){
                for (MedicationName medicationName : meds){
                    allMedicationNamesAL.add(medicationName.getName());
                    Log.d("ARRAYLIST", "onChanged: " + medicationName.getName());
                    Collections.sort(allMedicationNamesAL);
                    allMedicationNamesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, allMedicationNamesAL);
                    medNameListview.setAdapter(allMedicationNamesAdapter);
                }
           }
        });

        medNameSearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("SEARCHVIEW", "onQueryTextChange: " + s);
                try {
                    allMedicationNamesAdapter.getFilter().filter(s);
                } catch (Exception e) {
                    Log.d("CRASH", "Nog niets in de database!");
                }
                return false;
            }
        });

        medNameListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "Je hebt geklikt!" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT ).show();

                in.putExtra("givenMedName", adapterView.getItemAtPosition(i).toString());

                if (isNetworkAvailable()){
                    NetworkManager.getInstance(getApplicationContext()).getRequest("http://136.144.230.97:8090/api/medicationinfo/" + adapterView.getItemAtPosition(i).toString() + "?api_token=CilZjPDfkHDmb29qcJkqBS7bB2cup9T7Onqcmfaqt027QhvpqBhFvLinJ6Dp", new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new Gson();
                            MedicationInfo medicationInfo = gson.fromJson(result, MedicationInfo.class);
                            in.putExtra("givenMedSort" , medicationInfo.getSort());
                            in.putExtra("givenMedUse" , medicationInfo.getUse());

                            startActivity(in);

                        }
                    });
                } else {
                    Log.d("POP", "onItemClick: ANDERE POPUP");
                }



            }
        });



    }

    private Long getDateTime(int hour, int minute){
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.HOUR_OF_DAY, hour);
        currentTime.set(Calendar.MINUTE, minute);
        currentTime.set(Calendar.MILLISECOND, 0);

        Log.d("tijd", String.valueOf(currentTime.getTimeInMillis()));
        Log.d("tijd", String.valueOf(currentTime.getTime().getTime()));

        return currentTime.getTimeInMillis();
    }

    private Long getDate(int month, int day){
        Calendar currentTIme = Calendar.getInstance();
        currentTIme.set(Calendar.MONTH, month);
        currentTIme.set(Calendar.DAY_OF_MONTH, day);
        currentTIme.set(Calendar.HOUR_OF_DAY, 0);
        currentTIme.set(Calendar.MINUTE, 0);
        currentTIme.set(Calendar.SECOND, 0);
        currentTIme.set(Calendar.MILLISECOND, 0);

        return currentTIme.getTimeInMillis();
    }

    private void showStartScreen() {
        Intent myIntent = new Intent(getBaseContext(), WelcomeActivity.class);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        startActivity(myIntent);
    }

    private void insertIntoDatabase(String name, int milligram, Long date, Boolean isChecked, Long dateFrom, Long dateTo, String time){
        Medicine m = new Medicine(name, milligram, date, isChecked, dateFrom, dateTo, time);

        medicineViewModel.insert(m);

        Log.d("Medicine", "Naar de database geschreven");
    }

    private void handleApiCall(int id){
        int callid = id;
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
         return activeNetworkInfo != null;
    }


}
