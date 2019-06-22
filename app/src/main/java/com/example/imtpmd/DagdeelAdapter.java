package com.example.imtpmd;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class DagdeelAdapter extends RecyclerView.Adapter<DagdeelAdapter.MyViewHolder> {
    private HomeData[] data;
    private View v;
    private RecyclerView.Adapter medicationAdapter;
    private static RecyclerView.LayoutManager layoutManager;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTextView;
        public RecyclerView medicineListView;
        public MyViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.title_text_view);
            medicineListView = v.findViewById(R.id.recycler_view_medication_list);

            layoutManager = new LinearLayoutManager(v.getContext());
            medicineListView.setLayoutManager(layoutManager);
        }
    }

    public DagdeelAdapter(HomeData[]homeData){
        this.data = homeData;
    }

    @NonNull
    @Override
    public DagdeelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // De view van de xml
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dagdeel_home_view, viewGroup, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DagdeelAdapter.MyViewHolder myViewHolder, int i) {
        if(data[i] != null){
            myViewHolder.titleTextView.setText(data[i].getTitle());

            //vulList(myViewHolder.medicineListView, i);
            //roep de nieuwe Recyclerview aan

            medicationAdapter = new MedicationAdapter(data[i].getMedicineList());
            myViewHolder.medicineListView.setAdapter(medicationAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    private void vulList(ListView listView, int i){
        MedicineListAdapter medicineAdapter = new MedicineListAdapter(v.getContext(), data[i].getMedicineList());

        listView.setAdapter(medicineAdapter);
    }
}
