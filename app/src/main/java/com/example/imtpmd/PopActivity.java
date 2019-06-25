package com.example.imtpmd;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class PopActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    RelativeLayout layout1;
    private String medName;
    private String medSort;
    private String medUse;
    private String time;
    private TextView medNameTV;
    private TextView medSortTV;
    private TextView medUseTV;
    private TextView timeTV;

    private Button timeBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        Calendar c = Calendar.getInstance();
        int i = c.get(Calendar.HOUR_OF_DAY);
        int i1 = c.get(Calendar.MINUTE);

        medNameTV = findViewById(R.id.medNameTV);
        medSortTV = findViewById(R.id.medSortTV);
        medUseTV = findViewById(R.id.medUseTV);
        timeTV = findViewById(R.id.timeTV);

        setTime(i, i1);

        timeBTN = (Button) findViewById(R.id.timeBTN);
        timeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "timepicker");
            }
        });

        medName = getIntent().getStringExtra("givenMedName");
        medNameTV.setText(medName);

        medSort = getIntent().getStringExtra("givenMedSort");
        medSortTV.setText(medSort);

        medUse = getIntent().getStringExtra("givenMedUse");
        medUseTV.setText(medUse);



        layout1 = new RelativeLayout(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels ;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (height*.8));
    }

    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {
        setTime(i, i1);
    }

    public void setTime(int i, int i1){
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
}
