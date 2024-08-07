package com.example.vehiclecare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginButton;
    TextView mCreateBtn;
    ProgressBar progressBar;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        mLoginButton = findViewById(R.id.LoginButton);
        mCreateBtn = findViewById(R.id.createText);

        // Initialize API service
        apiService = RetrofitClient.getClient().create(APIService.class);

        // Set click listener for 'Sign Up' link
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        // Set click listener for 'Sign In' button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                // Validate inputs
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                loginUser(email, password);
            }
        });
    }

    // Method to handle user login
    private void loginUser(String email, String password) {
        // Perform login operation
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    for (User u : users) {
                        if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                            // Successful login
                            Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            // Navigate to VehicleList activity
                            Intent intent = new Intent(Login.this, VehicleList.class);
                            startActivity(intent);
                            finish(); // Optional: Close the Login activity
                            return;
                        }
                    }
                    // Login failed
                    Toast.makeText(Login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
