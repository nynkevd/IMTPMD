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
    private List<OverviewData> overviewData;
    private static MedicineViewModel medicineViewModel;


    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        overviewData = new ArrayList<>();

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

        final Observer<List<String>> observerNames = new Observer<List<String>>(){

            @Override
            public void onChanged(@Nullable List<String> medicines) {
            for(String name : medicines){
                overviewData.add(new OverviewData(name));
            }


            medAdapter.notifyDataSetChanged();
            }
        };

        medicineViewModel.getDistinctNames().observe(this, observerNames);

//        final Observer<List<Medicine>> observerOverviewData = new Observer<List<Medicine>>(){
//
//            @Override
//            public void onChanged(@Nullable List<Medicine> overviewDatas) { // overviewDatas is een mooie naam
//
//                for(Medicine overviewData : overviewDatas){
//                    Log.d("daaaataaaa", overviewData.getName() + " " + overviewData.getDate());
//                }
//
//
//
//                medAdapter.notifyDataSetChanged();
//            }
//        };
//
//        medicineViewModel.getOverviewData().observe(this, observerOverviewData);
//
//        final Observer<List<String>> observerDateTot = new Observer<List<String>>(){
//
//            @Override
//            public void onChanged(@Nullable List<String> medicines) {
//                for(String name : medicines){
//                    overviewData.add(new OverviewData(name));
//                }
//
//
//                medAdapter.notifyDataSetChanged();
//            }
//        };
//
//        medicineViewModel.getDistinctNames().observe(this, observerDateTot);

        return view;

    }

}
