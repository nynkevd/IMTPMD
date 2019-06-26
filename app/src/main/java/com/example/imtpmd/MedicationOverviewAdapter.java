package com.example.imtpmd;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MedicationOverviewAdapter extends RecyclerView.Adapter<MedicationOverviewAdapter.MyViewHolder> {

    private List<OverviewData> data;
    private View v;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView dateVan;
        public TextView dateTot;
        public TextView tijd;
        public Button delete;

        public MyViewHolder(View v){
            super(v);
            name = v.findViewById(R.id.name_text);
            //dateVan = v.findViewById(R.id.date_van_text);
            //dateTot = v.findViewById(R.id.date_tot_text);
            delete = v.findViewById(R.id.delete_button);

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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(data.get(i).getName());

        final int position = i;

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("teeeest", "Klik op delete" + data.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
