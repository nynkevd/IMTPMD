package com.example.imtpmd;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.work.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class PopAddActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    private static MedicineViewModel medicineViewModel;
    private static MedicationNameViewModel medicationNameViewModel;
    RelativeLayout layout1;

    private ArrayList<String> allMedicationNamesAL = new ArrayList<>();
    ArrayAdapter<String> allMedicationNamesAdapter;

    private String dateString;
    private String medName;
    private Long medDate;
    private int medHour;
    private int medMin;

    private AutoCompleteTextView medNameTV;
    private TextView timeTV;
    private TextView dateTV;
    private TextView dateTV2;

    private ImageButton timeBTN;
    private ImageButton dateBTN;
    private ImageButton dateBTN2;
    private Button confirmBTN;

    private Date date;
    private Date date2;

    private int i;
    private int i1;

    private int selectedBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_add);

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
        medicationNameViewModel = ViewModelProviders.of(this).get(MedicationNameViewModel.class);


        medNameTV = (AutoCompleteTextView) findViewById(R.id.medNameTV);
        medicationNameViewModel.getAllMedicationNames().observe(this, new Observer<List<MedicationName>>() {
            @Override
            public void onChanged(@Nullable final List<MedicationName> meds){
                for (MedicationName medicationName : meds){
                    allMedicationNamesAL.add(medicationName.getName());
                    Log.d("ARRAYLIST", "onChanged: " + medicationName.getName());
                    allMedicationNamesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, allMedicationNamesAL);
                    medNameTV.setAdapter(allMedicationNamesAdapter);
                }
            }
        });


        Calendar c = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        i = c.get(Calendar.HOUR_OF_DAY);
        i1 = c.get(Calendar.MINUTE);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        int y2 = c2.get(Calendar.YEAR);
        int m2 = c2.get(Calendar.MONTH);
        int d2 = c2.get(Calendar.DAY_OF_MONTH);
        c2.add(Calendar.DATE, 1);

        timeTV = findViewById(R.id.timeTV);
        dateTV = findViewById(R.id.dateTV);
        dateTV2 = findViewById(R.id.dateTV2);

        String currentDate = DateFormat.getDateInstance().format(c.getTime());
        String currentDate2 = DateFormat.getDateInstance().format(c2.getTime());

        date = c.getTime();
        date2 = c2.getTime();
        Log.d("tijdvan", "dateVan " + date.getTime());
        Log.d("tijdtot", "dateTot " + date2.getTime());

        dateTV.setText(currentDate);
        dateTV2.setText(currentDate2);
        setTime(i, i1);

        timeBTN = (ImageButton) findViewById(R.id.timeBTN);
        timeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "timepicker");
            }
        });

        dateBTN = (ImageButton) findViewById(R.id.dateBTN);
        dateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("DATE", date.getTime());

                selectedBTN = 0;

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setArguments(bundle);
                datePicker.show(getSupportFragmentManager(), "datepicker");
            }
        });

        dateBTN2 = (ImageButton) findViewById(R.id.dateBTN2);
        dateBTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("DATE", date2.getTime());

                selectedBTN = 1;

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setArguments(bundle);
                datePicker.show(getSupportFragmentManager(), "datepicker");
            }
        });

        confirmBTN = (Button) findViewById(R.id.confirmBTN);
        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDatabase();
            }
        });

        layout1 = new RelativeLayout(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        View popup = findViewById(R.id.activityPopAdd);
        View root = popup.getRootView();
        root.setBackgroundResource(R.drawable.card_overview);

        int width = dm.widthPixels ;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (height*.8));
    };

    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {
        setTime(i, i1);
    };


    public void setTime(int i, int i1){
        medHour = i;
        medMin = i1;
        if (i >= 10 && i1 >= 10){
            timeTV.setText(i + ":" + i1);
        }
        else if (i < 10 && i1 < 10) {
            timeTV.setText("0" + i + ":" + "0" + i1);
        }
        else if (i < 10 && i1 >= 10) {
            timeTV.setText("0" + i + ":" + i1);
        }
        else if (i >= 10 && i1 < 10) {
            timeTV.setText(i + ":0" + i1);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        Log.d("DATEPICKER", datePicker.toString());
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m);
        c.set(Calendar.DAY_OF_MONTH, d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        String currentDate = DateFormat.getDateInstance().format(c.getTime());

        if (selectedBTN == 0) {
            date = c.getTime();

            //DATUMVAN IN EEN LONG
            Log.d("TIJDVAN", "c.gettime    " + c.getTime().getTime());

            dateTV.setText(currentDate);

            if (date2.before(date)){
                c.add(Calendar.DATE, 1);
                dateTV2.setText(DateFormat.getDateInstance().format(c.getTime()));
                date2 = c.getTime();
            }
        } else if (selectedBTN == 1) {
            date2 = c.getTime();

            //DATUMTOT IN EEN LONG
            Log.d("TIJDTOT", "c.gettime    " + c.getTime().getTime());

            dateTV2.setText(currentDate);

            if (date2.before(date)){
                c.add(Calendar.DATE, -1);
                dateTV.setText(DateFormat.getDateInstance().format(c.getTime()));
                date = c.getTime();
            }
        }

    }

    private void insertIntoDatabase(){
        String time = timeTV.getText().toString();
        Log.d("TIME", time);


        String name =  medNameTV.getText().toString();

        if (!name.equals("")) {
            int milligram = 0;
            boolean isChecked = false;
            Long dateFrom = this.date.getTime();
            Long dateTo = this.date2.getTime();

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, medHour);
            c.set(Calendar.MINUTE, medMin);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);


            long diff = date2.getTime() - date.getTime();
            int dayCount = (int) diff / (24 * 60 * 60 * 1000);
            Log.d("DAYS", String.valueOf(dayCount));

            for (int i = 0; i <= dayCount; i++) {
                Log.d("CAL", DateFormat.getDateInstance().format(c.getTime()));
                medDate = c.getTime().getTime();

                Medicine m = new Medicine(name, milligram, medDate, isChecked, dateFrom, dateTo, time);
                Log.d("tijd", m.getDateFrom().toString());
                Log.d("tijd", m.getDateTo().toString());

                medicineViewModel.insert(m);
                c.add(Calendar.DATE, 1);

                // Voeg notificatie toe
                String tag = name + dateFrom + dateTo + time; // Misschien moet de tag uniek zijn, mar hierdoor kunnen we alle notificaties voor de medicijnen verwijderen
                scheduleNotification(medDate, tag, name);
            }

            Toast.makeText(getApplicationContext(), name + " toegevoegd!", Toast.LENGTH_SHORT).show();
            Log.d("Medicine", "Naar de database geschreven");
            finish();
        } else if (name.equals("")){
            Toast.makeText(getApplicationContext(), "Naam mag niet leeg zijn.", Toast.LENGTH_LONG).show();
        }

    }

    private void scheduleNotification(Long time, String tag, String name){
        long alertTime = time - System.currentTimeMillis();

        Log.d("notification", "Alert time: " + alertTime);

        Data data = new Data.Builder()
                .putString("title", "Medicijn")
                .putString("text", "Vergeet niet om " + name + " te nemen?!?!?!?!?!")
                .putInt("id", 0)
                .build();

        NotificationHandler.scheduleReminder(alertTime, data, tag);
    }
}
