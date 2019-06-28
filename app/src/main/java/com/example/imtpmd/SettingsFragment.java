package com.example.imtpmd;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private static RecyclerView.Adapter settingsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<SettingsData> settingsData;
    private static MedicineViewModel medicineViewModel;
    private SharedPreferences prefs;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        settingsData = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_settings);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        settingsAdapter = new MedicationSettingsAdapter(settingsData);
        recyclerView.setAdapter(settingsAdapter);

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        final Observer<List<Medicine>> observerNames = new Observer<List<Medicine>>(){

            @Override
            public void onChanged(@Nullable List<Medicine> medicines) {
                //Als er nieuwe data is wordt de hele lijst eerst geleegd, zodat alle nieuwe data opnieuw toegevoegd kan worden
                settingsData.clear();

                for(Medicine medicine : medicines){

                    settingsData.add(new SettingsData(medicine));
                }

                settingsAdapter.notifyDataSetChanged();
            }
        };

        medicineViewModel.getOverviewData().observe(this, observerNames);

        return view;
    }

    public static void updateMedicine(Medicine medicineWithupdate){
        Log.d("UPDATE", "updateMedicine: " + medicineWithupdate.getName());
        medicineWithupdate.setHasNotifs(!medicineWithupdate.getHasNotifs());
        medicineViewModel.update(medicineWithupdate);
    }

}
