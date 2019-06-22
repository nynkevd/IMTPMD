package com.example.imtpmd;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import static java.lang.Integer.parseInt;

public class WelcomeActivity extends AppCompatActivity {
    private static MedicationNameViewModel medicationNameViewModel;

    private int medicationCount;
   // private AppDatabase db;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        db = Room
//            .databaseBuilder(getApplicationContext(), AppDatabase.class, "medicine")
//            .allowMainThreadQueries() // Dit moet nog weg!!!
//            .build();

//        db.medicationNameDAO().deleteAll();
//        db.medicationCountDAO().deleteAll();
        MedicationName mn = new MedicationName("first");
        medicationNameViewModel.insert(mn);
//        db.medicationNameDAO().insertAll(mn);

        NetworkManager.getInstance(this).getRequest("http://136.144.230.97:8090/api/medicationcount?api_token=CilZjPDfkHDmb29qcJkqBS7bB2cup9T7Onqcmfaqt027QhvpqBhFvLinJ6Dp", new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                medicationCount = parseInt(result);
                MedicationCount c = new MedicationCount(medicationCount);
//                db.medicationCountDAO().insertAll(c);

                for (int i = 1; i <= medicationCount; i++) {
                    NetworkManager.getInstance(getApplicationContext()).getRequest("http://136.144.230.97:8090/api/medicationname/" + String.valueOf(i) + "?api_token=CilZjPDfkHDmb29qcJkqBS7bB2cup9T7Onqcmfaqt027QhvpqBhFvLinJ6Dp", new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) { //de anonieme klasse gaat nu dingen doen
                            MedicationName apicall = gson.fromJson(result, MedicationName.class);
                            MedicationName mn = new MedicationName(apicall.getName());
//                            db.medicationNameDAO().insertAll(mn);
                        }
                    });
                }
            }
        });

        Button verder_btn = (Button)findViewById(R.id.verder_btn);

        verder_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        });

        TextView overslaan_txt = (TextView)findViewById(R.id.overslaan_txt);

        overslaan_txt.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               startActivity(new Intent(WelcomeActivity.this, DashboardActivity.class));
           }
        });




    }
}
