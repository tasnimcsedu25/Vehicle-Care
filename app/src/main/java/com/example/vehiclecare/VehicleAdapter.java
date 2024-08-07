package com.example.vehiclecare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class VehicleAdapter extends ArrayAdapter<Vehicle> {

    private final Context context;
    private final List<Vehicle> vehicles;

    public VehicleAdapter(Context context, List<Vehicle> vehicles) {
        super(context, R.layout.vehicle_list_item, vehicles);
        this.context = context;
        this.vehicles = vehicles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.vehicle_list_item, parent, false);
        }

        Vehicle vehicle = vehicles.get(position);

        TextView modelTextView = convertView.findViewById(R.id.modelTextView);
        modelTextView.setText(vehicle.getModel());

        return convertView;
    }
}
