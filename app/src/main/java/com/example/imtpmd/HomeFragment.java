package com.example.imtpmd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


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
        ArrayList<String> ochtendList = new ArrayList<>();
        ochtendList.add("Leeg");
        ochtendList.add("Leeg");
        ochtendList.add("Leeg");

        ArrayAdapter<String> listAdapterOchtend = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ochtendList);

        ListView ochtendListView = view.findViewById(R.id.pil_list_ochtend);

        ochtendListView.setAdapter(listAdapterOchtend);

        //Middag
        ArrayList<String> middagList = new ArrayList<>();
        middagList.add("Leeg");

        ArrayAdapter<String> listAdapterMiddag = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,middagList);

        ListView middagListView = view.findViewById(R.id.pil_list_middag);

        middagListView.setAdapter(listAdapterMiddag);

        //Avond
        ArrayList<String> avondList = new ArrayList<>();
        avondList.add("Leeg");

        ArrayAdapter<String> listAdapterAvond= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,avondList);

        ListView avondListView = view.findViewById(R.id.pil_list_avond);

        avondListView.setAdapter(listAdapterAvond);

        return view;
    }

}
