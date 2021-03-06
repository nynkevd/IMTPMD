package com.example.imtpmd;

import android.arch.lifecycle.ViewModelProviders;
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

        medicationNameViewModel = ViewModelProviders.of(this).get(MedicationNameViewModel.class);

        //verwijderen van database!
//        MedicationName mn = new MedicationName("first");
//        medicationNameViewModel.insert(mn);

//        Log.d("DLETE", "DELETE DATABASE");
//        medicationNameViewModel.deleteAll();

        insertMedicationNamesFromApiIntoDatabase();

        Button verderBtn = (Button)findViewById(R.id.verder_btn);

        verderBtn.setOnClickListener(new View.OnClickListener(){
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

    public void insertMedicationNamesFromApiIntoDatabase(){
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
                            medicationNameViewModel.insert(mn);
                        }
                    });
                }
            }
        });
    }
}
