package com.example.imtpmd;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopActivity extends AppCompatActivity {

    RelativeLayout layout1;
    private String medName;
    private TextView medNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        medNameTV = findViewById(R.id.medNameTV);

        medName = getIntent().getStringExtra("givenMedName");
        medNameTV.setText(medName);


        layout1 = new RelativeLayout(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels ;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (height*.7));
    }

}
