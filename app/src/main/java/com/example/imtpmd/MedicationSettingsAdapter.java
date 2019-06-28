package com.example.imtpmd;

import android.app.NotificationManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

public class MedicationSettingsAdapter extends RecyclerView.Adapter<MedicationSettingsAdapter.MyViewHolder> {

    private List<SettingsData> data;
    private View v;
    private int j;

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

//        myViewHolder.medSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
////                if (isChecked) {
////                    Log.d("CHECK", "onCheckedChanged: TEOVOEGEN " + data.get(j).getMedicine().getName());
////                    SettingsFragment.updateMedicine(data.get(getItemViewType(j)).getMedicine());
////                    return;
////
////                } else {
////                    Log.d("CHECK" +
////                            "", "onCheckedChanged: VERWIDJEREN");
////
////                    SettingsFragment.updateMedicine(data.get(getItemViewType(j)).getMedicine());
////                    return;
////                }
////
////            }
////        });
        myViewHolder.medSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (myViewHolder.medSwitch.isChecked()){
                    Log.d("ah", "onClick: Hij si nu aan" + data.get(position).getMedicine().getName());

                    long diff = data.get(position).getMedicine().getDateTo() - data.get(position).getMedicine().getDateFrom();
                    int dayCount = (int) diff / (24 * 60 * 60 * 1000);
                    Log.d("DAYS", String.valueOf(dayCount));

                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(data.get(position).getMedicine().getDate());

                    String name = data.get(position).getMedicine().getName();
                    String tag = data.get(position).getMedicine().getTag();

                    SettingsFragment.updateMedicine(data.get(position).getMedicine());

                    for (int x = 0; x < dayCount; x++){
                        Long medDate = c.getTime().getTime();
                        scheduleNotification(medDate, tag, name);

                        c.add(Calendar.DATE, 1);
                    }
                } else {
                    Log.d("ah", "onClick: Hij is u uit");
                    SettingsFragment.updateMedicine(data.get(position).getMedicine());

                    String tag = data.get(position).getMedicine().getTag();

                    NotificationHandler.cancelReminder(tag);
                }
            }
        });
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
