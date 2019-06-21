package com.example.imtpmd;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MedicineListAdapter extends ArrayAdapter<Medicine> {
    private Context context;
    private List<Medicine> medicineList;

    public MedicineListAdapter(Context context, ArrayList<Medicine> list){
        super(context, 0, list);
        this.context = context;
        this.medicineList = list;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent){
        View listItem = convertView;

        // list_item.xml wordt heir aangeroepen
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        final Medicine currentItem = medicineList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.name);
        name.setText(currentItem.getName());

        CheckBox checkBox = (CheckBox) listItem.findViewById(R.id.checkBox);
        checkBox.setChecked(currentItem.getChecked()); //setChecked is een standaard functie van een ckeckbox

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verander de checked waarde in de database
                Log.d("tessst", currentItem.getName());
                HomeFragment.updateMedicineChecked(currentItem, view);
            }
        });

        return listItem;

    }

//    private void updateMedicineChecked(Medicine medicine, View view){
//
//        medicine.setChecked(!medicine.getChecked());
//
//        medicineRepository.update(medicine);
//
//        Log.d("Medicine", medicine.getName() + ": " + medicine.getChecked().toString());
//    }


}
