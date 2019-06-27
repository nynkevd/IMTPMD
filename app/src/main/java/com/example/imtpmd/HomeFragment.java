package com.example.imtpmd;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static MedicineViewModel medicineViewModel;
//    private static MedicationNameViewModel medicationNameViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private HomeData[] homeData = new HomeData[4];

    WelcomeActivity welcomeActivity;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Vraag de medicijnen op per dagdeel
//        medicationNameViewModel = ViewModelProviders.of(this).get(MedicationNameViewModel.class);

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Recycler view variabelen
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_home);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DagdeelAdapter(homeData);
        recyclerView.setAdapter(mAdapter);

        // Vraag de medicijnen op per dagdeel
        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        final Observer<List<Medicine>> nachtObserver = new Observer<List<Medicine>>(){
            @Override
            public void onChanged(@Nullable final List<Medicine> newList){
                ArrayList<Medicine> list  = new ArrayList<>();
                homeData[0] = null;

                for(Medicine medicine : newList ){
                    list.add(medicine);
                }

                homeData[0] = new HomeData("Nacht", list);
                mAdapter.notifyItemChanged(3);
            }

        };

        medicineViewModel.getNachtList().observe(this, nachtObserver);

        // Observer ochtend
        final Observer<List<Medicine>> ochtendObserver = new Observer<List<Medicine>>(){
            @Override
            public void onChanged(@Nullable final List<Medicine> newList){
                ArrayList<Medicine> list  = new ArrayList<>();
                homeData[1] = null;

                for(Medicine medicine : newList ){
                    list.add(medicine);
                }

                homeData[1] = new HomeData("Ochtend", list);

                mAdapter.notifyItemChanged(0);

            }

        };

        medicineViewModel.getOchtendList().observe(this, ochtendObserver);

        // Observer middag
        final Observer<List<Medicine>> middagObserver = new Observer<List<Medicine>>(){
            @Override
            public void onChanged(@Nullable final List<Medicine> newList){
                ArrayList<Medicine> list  = new ArrayList<>();
                homeData[2] = null;

                for(Medicine medicine : newList ){
                    Log.d("teeeeest", medicine.getName());
                    list.add(medicine);
                }

                homeData[2] = new HomeData("Middag", list);

                mAdapter.notifyItemChanged(1);
            }

        };

        medicineViewModel.getMiddagList().observe(this, middagObserver);

        // Observer avond
        final Observer<List<Medicine>> avondObserver = new Observer<List<Medicine>>(){
            @Override
            public void onChanged(@Nullable final List<Medicine> newList){
                ArrayList<Medicine> list  = new ArrayList<>();
                homeData[3] = null;

                for(Medicine medicine : newList ){
                    list.add(medicine);
                }

                homeData[3] = new HomeData("Avond", list);
                mAdapter.notifyItemChanged(2);
            }

        };

        medicineViewModel.getAvondList().observe(this, avondObserver);




        return view;
    }


    public static void updateMedicineChecked(Medicine medicine, View view){
        medicine.setChecked(!medicine.getChecked());

        medicineViewModel.update(medicine);

        Log.d("Medicine", medicine.getName() + ": " + medicine.getChecked().toString());
    }

}
