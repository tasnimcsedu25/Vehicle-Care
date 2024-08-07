package com.example.vehiclecare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleList extends AppCompatActivity {

    private ListView vehicleListView;
    private Button addVehicleButton;
    private VehicleAdapter vehicleAdapter;
    private List<Vehicle> vehicleList = new ArrayList<>();
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);

        vehicleListView = findViewById(R.id.vehicleListView);
        addVehicleButton = findViewById(R.id.addVehicleButton);

        vehicleAdapter = new VehicleAdapter(this, vehicleList);
        vehicleListView.setAdapter(vehicleAdapter);

        apiService = RetrofitClient.getClient().create(APIService.class);

        loadVehicles();

        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VehicleList.this, AddVehicle.class));
            }
        });
    }

    private void loadVehicles() {
        apiService.getVehicles().enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    vehicleList.clear();
                    vehicleList.addAll(response.body());
                    vehicleAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(VehicleList.this, "Failed to load vehicles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Toast.makeText(VehicleList.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
