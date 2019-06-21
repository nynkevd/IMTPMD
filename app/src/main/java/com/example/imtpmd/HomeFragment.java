package com.example.imtpmd;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static MedicineViewModel medicineViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        final Observer<List<Medicine>> ochtendObserver = new Observer<List<Medicine>>(){
            @Override
            public void onChanged(@Nullable final List<Medicine> newList){
                ArrayList<Medicine> list  = new ArrayList<>();

                for(Medicine medicine : newList ){
                    list.add(medicine);
                }

                vulList("pil_list_ochtend", list, view);
                Log.d("tessst", "OBSERVVVEEEEEEEE" + newList.get(0).getName());
            }

        };

        medicineViewModel.getOchtendList().observe(this, ochtendObserver);

        final Observer<List<Medicine>> middagObserver = new Observer<List<Medicine>>(){
            @Override
            public void onChanged(@Nullable final List<Medicine> newList){
                ArrayList<Medicine> list  = new ArrayList<>();

                for(Medicine medicine : newList ){
                    list.add(medicine);
                }

                vulList("pil_list_middag", list, view);
                Log.d("tessst", "OBSERVVVEEEEEEEE" + newList.get(0).getName());
            }

        };

        medicineViewModel.getMiddagList().observe(this, middagObserver);

        final Observer<List<Medicine>> avondObserver = new Observer<List<Medicine>>(){
            @Override
            public void onChanged(@Nullable final List<Medicine> newList){
                ArrayList<Medicine> list  = new ArrayList<>();

                for(Medicine medicine : newList ){
                    list.add(medicine);
                }

                vulList("pil_list_avond", list, view);
                Log.d("tessst", "OBSERVVVEEEEEEEE" + newList.get(0).getName());
            }

        };

        medicineViewModel.getAvondList().observe(this, avondObserver);

        //Er moet nog een optie voor nacht gemaakt worden:

//        final Observer<List<Medicine>> nachtObserver = new Observer<List<Medicine>>(){
//            @Override
//            public void onChanged(@Nullable final List<Medicine> newList){
//                ArrayList<Medicine> list  = new ArrayList<>();
//
//                for(Medicine medicine : newList ){
//                    list.add(medicine);
//                }
//
//                vulList("pil_list_nacht", list, view);
//                Log.d("tessst", "OBSERVVVEEEEEEEE" + newList.get(0).getName());
//            }
//
//        };
//
//        medicineViewModel.getNachtList().observe(this, nachtObserver);


        return view;
    }

    // Vult een lijst met de namen van de medicijnen om deze te laten zien in het overzicht
    private void vulList(String id, ArrayList list, View view){
        int listId = getResources().getIdentifier(id, "id", getContext().getPackageName());

        MedicineListAdapter medicineAdapter = new MedicineListAdapter(getActivity(), list);

        ListView listView = view.findViewById(listId);

        listView.setAdapter(medicineAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Je hebt geklikt op iets, wat een feest", Toast.LENGTH_LONG).show();
            }
        });


    }

    public static void updateMedicineChecked(Medicine medicine, View view){
        medicine.setChecked(!medicine.getChecked());

        medicineViewModel.update(medicine);

        Log.d("Medicine", medicine.getName() + ": " + medicine.getChecked().toString());
    }

}
