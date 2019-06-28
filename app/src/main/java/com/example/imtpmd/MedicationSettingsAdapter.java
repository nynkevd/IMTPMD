package com.example.imtpmd;

import android.app.NotificationManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.work.Data;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MedicationSettingsAdapter extends RecyclerView.Adapter<MedicationSettingsAdapter.MyViewHolder> {

    private List<SettingsData> data;
    private View v;
    private int j;
    private SharedPreferences prefs;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView time;
        public Switch medSwitch;

        public MyViewHolder(View v){
            super(v);
            name = v.findViewById(R.id.name_text);
            time = v.findViewById(R.id.time_text);
            medSwitch = v.findViewById(R.id.switch1);

        }
    }


    public MedicationSettingsAdapter(List<SettingsData> settingsData){
        this.data = settingsData;
    }

    @NonNull
    @Override
    public MedicationSettingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.settings_medication_item, viewGroup, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final int position = i;
        myViewHolder.name.setText(data.get(i).getMedicine().getName());
        myViewHolder.time.setText(data.get(i).getMedicine().getTime());
        myViewHolder.medSwitch.setChecked(data.get(i).getMedicine().getHasNotifs());

        prefs = v.getContext().getSharedPreferences("notifications", MODE_PRIVATE);

        myViewHolder.medSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (myViewHolder.medSwitch.isChecked()){

                    String name = data.get(position).getMedicine().getName();
                    String tag = data.get(position).getMedicine().getTag();

                    SettingsFragment.updateMedicine(data.get(position).getMedicine());

                    //Notificaties aan zetten
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(tag, true);
                    editor.apply();

                    Toast.makeText(v.getContext(), "Notificaties voor " + name + " aangezet", Toast.LENGTH_LONG).show();

                } else {
                    SettingsFragment.updateMedicine(data.get(position).getMedicine());

                    String name = data.get(position).getMedicine().getName();
                    String tag = data.get(position).getMedicine().getTag();

                    //Notificatiet uit zetten
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(tag, false);
                    editor.apply();

                    Toast.makeText(v.getContext(), "Notificaties voor " + name + " uitgezet", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("COUNT", "getItemCount: " + data.size());
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
