package com.pmn.employeeportal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.pmn.employeeportal.auth.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startLoginActivityAfterCertainTime();

    }


    /**
     * Method which will start login activity after given millis.
     */
    private void startLoginActivityAfterCertainTime() {

        new Handler().postDelayed(() -> {

            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }, 2000);
    }
}