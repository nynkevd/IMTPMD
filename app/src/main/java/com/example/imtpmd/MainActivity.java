package com.example.imtpmd;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
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

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart){
            showStartScreen();
        }

        // HE VOORDAT JE DEZE DOET NOG EVEN EEN TIJD TOEVOEGEN!!!!
//        insertIntoDatabase("testDubbel", 40, getDateTime(12, 0), false , getDate(6, 24), getDate(6, 30));
//        insertIntoDatabase("testDubbel", 40, getDateTime(16, 0), false , getDate(6, 22), getDate(6, 30));

        String channelID = "1";
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//De activity komt bovenop de andere actieve activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_action_pil) // het klopt nu niet dat er action in de naam staat denk ik...
                .setContentTitle("HALLOOOOOO")
                .setContentText("Je moet niet je medicijnen vergeten!")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);


        //Vanaf android oreo en verder, gebruik deze code
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    channelID, "Test", NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(0, builder.build());
        }



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
                allMedicationNamesAdapter.getFilter().filter(s);
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
