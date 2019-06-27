package com.example.imtpmd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.List;

public class MedicationSettingsAdapter extends RecyclerView.Adapter<MedicationSettingsAdapter.MyViewHolder> {

    private List<SettingsData> data;
    private View v;

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
    public MedicationSettingsAdapter.MyViewHolder



    onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.settings_medication_item, viewGroup, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(data.get(i).getName());
        myViewHolder.time.setText(data.get(i).getTime());
        myViewHolder.medSwitch.setChecked(false);
    }

    @Override
    public int getItemCount() {
        Log.d("COUNT", "getItemCount: " + data.size());
        return data.size();
    }

}
