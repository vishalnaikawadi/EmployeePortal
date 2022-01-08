package com.pmn.employeeportal.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pmn.employeeportal.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextInputLayout tilEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        bindings();

        btnLogin.setOnClickListener(v -> {
            login();
        });

    }

    private void bindings() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tilEmail = findViewById(R.id.tilEmail);
    }

    private void login() {
        String strEmail = etEmail.getText().toString();
        String strPassword = etPassword.getText().toString();

        if (TextUtils.isEmpty(strEmail)) {

            return;
        }

        if (TextUtils.isEmpty(strPassword)) {

            return;
        }

        mAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                //start home activity.
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }

        });

    }
}