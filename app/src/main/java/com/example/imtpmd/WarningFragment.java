package com.example.imtpmd;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class WarningFragment extends DialogFragment {

    private static MedicineViewModel medicineViewModel;
    private String name;
    private Long dateFrom;
    private Long dateTo;
    private String time;
    private OverviewData overviewData;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            name = bundle.getString("name");
            dateFrom = bundle.getLong("dateFrom");
            dateTo = bundle.getLong("dateTo");
            time = bundle.getString("time");

            overviewData = new OverviewData(name, dateFrom, dateTo, time);
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Weet je zeker dat je " + name + " wilt verwijderen?")
                .setPositiveButton("ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        medicineViewModel.deleteMedicine(overviewData);
                    }
                })
                .setNegativeButton("nee", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
