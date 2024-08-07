package com.example.vehiclecare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVehicle extends AppCompatActivity {

    EditText mModel, mEngineNumber, mChesisNumber;
    Button mSaveButton;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        // Initialize UI elements
        mModel = findViewById(R.id.model);
        mEngineNumber = findViewById(R.id.engineNumber);
        mChesisNumber = findViewById(R.id.chesisNumber);
        mSaveButton = findViewById(R.id.saveButton);

        // Initialize API service
        apiService = RetrofitClient.getClient().create(APIService.class);

        // Set click listener for 'Save' button
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String model = mModel.getText().toString().trim();
                String engineNumber = mEngineNumber.getText().toString().trim();
                String chesisNumber = mChesisNumber.getText().toString().trim();

                // Validate inputs
                if (model.isEmpty() || engineNumber.isEmpty() || chesisNumber.isEmpty()) {
                    Toast.makeText(AddVehicle.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a Vehicle object
                Vehicle vehicle = new Vehicle();
                vehicle.setModel(model);
                vehicle.setEngineNumber(engineNumber);
                vehicle.setChesisNumber(chesisNumber);

                // Perform vehicle addition operation
                apiService.createVehicle(vehicle).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Successful addition
                            Toast.makeText(AddVehicle.this, "Vehicle Added Successfully!", Toast.LENGTH_SHORT).show();
                            // Navigate back to the previous screen
                            finish();
                        } else {
                            Toast.makeText(AddVehicle.this, "Failed to Add Vehicle", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddVehicle.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
