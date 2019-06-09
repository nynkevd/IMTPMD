package com.example.imtpmd;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Ochtend
        ArrayList<String> ochtendList = getMedicine(6, 12);

        vulList("pil_list_ochtend", ochtendList, view);

        //Middag
        ArrayList<String> middagList = getMedicine(12, 18);

        vulList("pil_list_middag", middagList, view);

        //Avond
        ArrayList<String> avondList = getMedicine(18, 24);

        vulList("pil_list_avond", avondList, view);

        return view;
    }

    // Vult een lijst met de namen van de medicijnen om deze te laten zien in het overzicht
    private void vulList(String id, ArrayList list, View view){
        int listId = getResources().getIdentifier(id, "id", getContext().getPackageName());

        ArrayAdapter<String> listAdapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);

        ListView listView = view.findViewById(listId);

        listView.setAdapter(listAdapter);
    }

    // Geeft een lijst van de namen van de medicijnen terug voor het tijdstip dat is opgegeven
    private ArrayList<String> getMedicine(int timeStart, int timeEnd){
        AppDatabase db = Room
                .databaseBuilder(getActivity(), AppDatabase.class, "medicine")
                .allowMainThreadQueries() // Dit moet nog weg!!!
                .build();

        List<Medicine> medicineList = db.medicineDAO().loadByTime(timeStart, timeEnd);

        ArrayList<String> namen = new ArrayList<>();

        for(Medicine item : medicineList ){
            namen.add(item.getName());
        }

        return namen;
    }

}
