package com.example.imtpmd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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
