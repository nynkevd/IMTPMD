package com.example.imtpmd;

import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MyViewHolder> {
    private View v;
    private ArrayList<Medicine> medicationList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView medicationNameView;
        public CheckBox checkBoxView;
        public TextView timeView;
        public MyViewHolder(View v){
            super(v);
            medicationNameView = v.findViewById(R.id.name);
            checkBoxView = v.findViewById(R.id.checkBox);
            timeView = v.findViewById(R.id.time);
        }
    }

    public MedicationAdapter(final ArrayList<Medicine> medicationList){

        // Sorteer de lijst op basis van tijdstip
        Collections.sort(medicationList, new Comparator<Medicine>() {
            @Override
            public int compare(Medicine medicine, Medicine t1) {
                if(medicine.getDate() > t1.getDate()){
                    return 1;
                }else if (medicine.getDate() < t1.getDate()){
                    return -1;
                }else{
                    return 0;
                }
            }
        });

        this.medicationList = medicationList;
    }

    @NonNull
    @Override
    public MedicationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.medicationNameView.setText(medicationList.get(i).getName());
        myViewHolder.checkBoxView.setChecked(medicationList.get(i).getChecked());

//        Calendar time = Calendar.getInstance();
//        time.setTimeInMillis(medicationList.get(i).getDate());

        SimpleDateFormat test = new SimpleDateFormat("HH:mm");
        String time = test.format(medicationList.get(i).getDate());

        myViewHolder.timeView.setText(time);

        final int position = i;

        myViewHolder.checkBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verander de checked waarde in de database
                Log.d("tessst", medicationList.get(position).getName());
                HomeFragment.updateMedicineChecked(medicationList.get(position), view);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("count", String.valueOf(medicationList.size()));
        return medicationList.size();
    }
}
