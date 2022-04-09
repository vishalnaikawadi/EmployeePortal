package com.pmn.employeeportal.leave;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pmn.employeeportal.R;
import com.pmn.employeeportal.model.LeaveModel;
import com.pmn.employeeportal.utils.RealtimeDBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LeavesActivity extends AppCompatActivity {

    private EditText etFrom;
    private EditText etTo;
    private Button btnApply;
    private TextInputLayout tilFrom;
    private TextInputLayout tilTo;
    private EditText etReason;
    private RecyclerView rvLeaves;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<LeaveModel> leavesList = new ArrayList<>();
    private LeavesAdapter leavesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);

        //binding
        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        btnApply = findViewById(R.id.btnApply);
        tilTo = findViewById(R.id.tilTo);
        tilFrom = findViewById(R.id.tilFrom);
        etReason = findViewById(R.id.etReason);
        rvLeaves = findViewById((R.id.rvLeaves));

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


        btnApply.setOnClickListener(v -> {

            String fromDate = etFrom.getText().toString();
            String toDate = etTo.getText().toString();
            String reason = etReason.getText().toString();

            tilFrom.setError(null);
            tilTo.setError(null);

            if (TextUtils.isEmpty(fromDate)) {
                tilFrom.setError("Please Enter FROM date");
                return;
            }

            if (TextUtils.isEmpty(toDate)) {
                tilTo.setError("Please Enter TO date");
                return;
            }

            String uniqueId = String.valueOf(System.currentTimeMillis());


            if (user != null) {

                LeaveModel userLeaveData = new LeaveModel(uniqueId, fromDate, toDate, reason);
                LeaveModel leaveData = new LeaveModel(uniqueId, fromDate, toDate, reason, "UNAPPROVED", "", user.getEmail());
                RealtimeDBManager.insertUserLeaves(user, userLeaveData);
                RealtimeDBManager.insertLeaves(leaveData);
                Toast.makeText(this, "Leave submitted successfully!!", Toast.LENGTH_SHORT).show();
            }


        });

        getLeavesData();

    }

    private void getLeavesData() {
        RealtimeDBManager.mDatabase.child(RealtimeDBManager.USERS).child(user.getUid()).child(RealtimeDBManager.USER_LEAVES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                leavesList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    LeaveModel leaveModel = postSnapshot.getValue(LeaveModel.class);
                    if (leaveModel != null) {
                        leavesList.add(leaveModel);
                    }
                }
                leavesAdapter = new LeavesAdapter(leavesList);
                rvLeaves.setAdapter(leavesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LeavesActivity.this, "Something Went Wrong, Unable to fetch data.", Toast.LENGTH_SHORT).show();
            }
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