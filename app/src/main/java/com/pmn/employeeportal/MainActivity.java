package com.pmn.employeeportal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pmn.employeeportal.auth.LoginActivity;
import com.pmn.employeeportal.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        startLoginActivityAfterCertainTime();

    }


    /**
     * Method which will start login activity after given millis.
     */
    private void startLoginActivityAfterCertainTime() {

        new Handler().postDelayed(() -> {

            FirebaseUser firebaseUser = auth.getCurrentUser();

            Intent intent;

            if (firebaseUser != null) {
                intent = new Intent(this, HomeActivity.class);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
            startActivity(intent);
            finish();

        }, 2000);
    }
}