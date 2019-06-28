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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.List;

public class MedicationOverviewAdapter extends RecyclerView.Adapter<MedicationOverviewAdapter.MyViewHolder> {

    private List<OverviewData> data;
    private View v;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView dateVan;
        public TextView dateTot;
        public ImageButton delete;
        public TextView time;

        public MyViewHolder(View v){
            super(v);
            name = v.findViewById(R.id.name_text);
            dateVan = v.findViewById(R.id.date_van_text);
            dateTot = v.findViewById(R.id.date_tot_text);
            delete = v.findViewById(R.id.delete_button);
            time = v.findViewById(R.id.tijd_text);

        }
    }

    public MedicationOverviewAdapter(List<OverviewData> overviewData){
        this.data = overviewData;
    }

    @NonNull
    @Override
    public MedicationOverviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.overview_medication_item, viewGroup, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(data.get(i).getName());
        myViewHolder.dateVan.setText(getStringFromDate(data.get(i).getDateVan()));
        myViewHolder.dateTot.setText(getStringFromDate(data.get(i).getDateTot()));
        myViewHolder.time.setText(data.get(i).getTime());

        final int position = i;

        // Als er op de delete knop geklikt wordt worden alle medicijnen met dezelfde naam uit de database verwijderd
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            OverviewFragment.deleteMedicine(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private String getStringFromDate(Long date){
        String dateString = DateFormat.getDateInstance().format(date);

        return  dateString;
    }

}
