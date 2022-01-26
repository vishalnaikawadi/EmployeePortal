package com.pmn.employeeportal.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.pmn.employeeportal.R;
import com.pmn.employeeportal.home.HomeActivity;
import com.pmn.employeeportal.utils.RealtimeDBManager;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        bindings();
        listeners();
    }

    private void listeners() {
        btnLogin.setOnClickListener(v -> {
            login();
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    tilEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    tilPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void bindings() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        progressBar = findViewById(R.id.progressBar);
    }

    private void login() {

        String strEmail = etEmail.getText().toString();
        String strPassword = etPassword.getText().toString();

        if (!isAllFieldsValid(strEmail, strPassword)) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(task -> {

            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {

                if (mAuth.getCurrentUser() != null) {

                    RealtimeDBManager.insertUser(mAuth.getCurrentUser());
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private boolean isAllFieldsValid(String strEmail, String strPassword) {

        if (TextUtils.isEmpty(strEmail)) {
            tilEmail.setError("Email cannot be blank");
            return false;
        }

        if (TextUtils.isEmpty(strPassword)) {
            tilPassword.setError("Password cannot be blank");
            return false;
        }

        if (!isValidEmail(strEmail)) {
            tilEmail.setError("Invalid Email Address");
            return false;
        }

        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}