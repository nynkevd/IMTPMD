package com.example.imtpmd;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {
    private static MedicineViewModel medicineViewModel;
    private FloatingActionButton AddButton;
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter medAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //private OverviewData[] overviewData;
    private List<OverviewData> overviewData;
    private Context context;

    private static FragmentActivity fa;

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        overviewData = new ArrayList<>();

        context = this.getContext();

        fa = getActivity();

        AddButton = (FloatingActionButton) view.findViewById(R.id.add_button);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "addddd");

                // Naar toevoegen pagina
            }
        });

        // Recycler view variabelen
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_overview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        medAdapter = new MedicationOverviewAdapter(overviewData);
        recyclerView.setAdapter(medAdapter);

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        final Observer<List<Medicine>> observerNames = new Observer<List<Medicine>>(){

            @Override
            public void onChanged(@Nullable List<Medicine> medicines) {
                //Als er nieuwe data is wordt de hele lijst eerst geleegd, zodat alle nieuwe data opnieuw toegevoegd kan worden
                overviewData.clear();

                for(Medicine medicine : medicines){
                    Log.d("teeeest", "dateVan: " + medicine.getDateFrom().toString() + ", date:" + medicine.getDate().toString());
                    overviewData.add(new OverviewData(medicine.getName(), medicine.getDateFrom(), medicine.getDateTo()));
                }

                medAdapter.notifyDataSetChanged();
            }
        };

        medicineViewModel.getOverviewData().observe(this, observerNames);


        return view;

    }

    // Functie om de medicijenen met een bepaalde naam te kunnen verwijderen
    // Deze functie wordt aanggeroepen vanui MedicationOverviewAdapter
    public static void deleteMedicine(OverviewData medicine){
        Bundle bundle = new Bundle();
        bundle.putString("name", medicine.getName());
        bundle.putLong("dateFrom", medicine.getDateVan());
        bundle.putLong("dateTo", medicine.getDateTot());
        DialogFragment warningFragment = new WarningFragment();
        warningFragment.setArguments(bundle);
        warningFragment.show(fa.getSupportFragmentManager(), "warningfragment");

        }

}
