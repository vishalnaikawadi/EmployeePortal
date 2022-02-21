package com.pmn.employeeportal.leave;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.pmn.employeeportal.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LeavesActivity extends AppCompatActivity {

    private EditText etFrom;
    private EditText etTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);

        //binding
        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);

        //listener
        etFrom.setOnClickListener(view -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("From date")
                    .build();
            datePicker.show(getSupportFragmentManager(), "");

            datePicker.addOnPositiveButtonClickListener(selection -> {

                if (datePicker.getSelection() != null) {
                    etFrom.setText(getDate(datePicker.getSelection(), "dd/MM/yyyy"));
                }

            });
        });

        etTo.setOnClickListener(view -> {

            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("To date")
                    .build();
            datePicker.show(getSupportFragmentManager(), "");

            datePicker.addOnPositiveButtonClickListener(selection -> {

                if (datePicker.getSelection() != null) {
                    etTo.setText(getDate(datePicker.getSelection(), "dd/MM/yyyy"));
                }

            });

        });

    }

    /**
     * Return date in specified format.
     *
     * @param milliSeconds Date in milliseconds
     * @param dateFormat   Date format
     * @return String representing date in specified format
     */
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}