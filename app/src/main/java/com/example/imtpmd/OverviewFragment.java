package com.example.imtpmd;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    private FloatingActionButton AddButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter medAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //private OverviewData[] overviewData;
    private List<OverviewData> overviewData = new ArrayList<>();
    private static MedicineViewModel medicineViewModel;


    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        AddButton = (FloatingActionButton) view.findViewById(R.id.add_button);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "addddd");
                // Naar toevoegen pagina
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_overview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        medAdapter = new MedicationOverviewAdapter(overviewData);
        recyclerView.setAdapter(medAdapter);

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        final Observer<List<Medicine>> observer = new Observer<List<Medicine>>(){

            @Override
            public void onChanged(@Nullable List<Medicine> medicines) {
                Boolean inArray = false;

                // dit is niet zo netjes
                for(Medicine medicine : medicines){
                    Log.d("aaaaaa", "Medicine name: " + medicine.getName());
                    for(OverviewData oldData : overviewData){
                        Log.d("aaaaaa", "OldData name: " + oldData.getName());
                        if(oldData.getName().equals(medicine.getName())){
                            inArray = true;
                            Log.d("aaaaaaa", "Deze waarde komt al in de lijst voor: " + oldData.getName());
                        }
                    }

                    if(!inArray){
                        overviewData.add(new OverviewData(medicine.getName()));
                    }
                }

                medAdapter.notifyDataSetChanged();
            }
        };

        medicineViewModel.getAllMedication().observe(this, observer);

        return view;

    }

}
