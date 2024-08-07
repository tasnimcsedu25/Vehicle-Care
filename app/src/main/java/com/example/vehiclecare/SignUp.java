package com.example.vehiclecare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    EditText mName, mEmail, mPassword;
    Button mSignUpButton;
    TextView nLoginButton;
    ProgressBar ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mSignUpButton = findViewById(R.id.SignUpButton);
        nLoginButton = findViewById(R.id.createText);
        ProgressBar = findViewById(R.id.progressBar);

        nLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String name = mName.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password must be more than 6 characters");
                    return;
                }

                ProgressBar.setVisibility(View.VISIBLE);

                // Create user object
                User user = new User();
                user.setUserName(name);
                user.setEmail(email);
                user.setPassword(password);

                // Call API to create user
                APIService apiService = RetrofitClient.getClient().create(APIService.class);
                apiService.createUser(user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        ProgressBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            // Registration successful, navigate to login
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        } else {
                            // Handle failure
                            // Show error message
                            Log.e("SignUp", "Error: " + response.message());
                            Toast.makeText(SignUp.this, "Failed to sign up: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        ProgressBar.setVisibility(View.GONE);
                        // Handle error
                        // Show error message
                        Log.e("SignUp", "Failure: " + t.getMessage());
                        Toast.makeText(SignUp.this, "Sign up failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
