package com.example.imtpmd;


import android.arch.persistence.room.Room;
import android.os.Bundle;
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

    AppDatabase db = Room
            .databaseBuilder(getActivity(), AppDatabase.class, "medicine")
            .allowMainThreadQueries() // Dit moet nog weg!!!
            .build();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Ochtend
        ArrayList<Medicine> ochtendList = getMedicine(6, 12);

        vulList("pil_list_ochtend", ochtendList, view);

        //Middag
        ArrayList<Medicine> middagList = getMedicine(12, 18);

        vulList("pil_list_middag", middagList, view);

        //Avond
        ArrayList<Medicine> avondList = getMedicine(18, 24);

        vulList("pil_list_avond", avondList, view);

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

    // Geeft een lijst van de namen van de medicijnen terug voor het tijdstip dat is opgegeven
    private ArrayList<Medicine> getMedicine(int timeStart, int timeEnd){


        List<Medicine> medicineList = db.medicineDAO().loadByTime(timeStart, timeEnd);

        ArrayList<Medicine> namen = new ArrayList<>();

        for(Medicine medicine : medicineList ){
            namen.add(medicine);

        }

        return namen;
    }

}
