package com.pmn.employeeportal.feedback;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pmn.employeeportal.R;
import com.pmn.employeeportal.model.FeedbackModel;
import com.pmn.employeeportal.utils.RealtimeDBManager;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    private AutoCompleteTextView actReasons;
    private EditText etReason;
    private Button btnSubmit;
    private TextInputLayout tilReason;
    private TextInputLayout tilComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //binding
        actReasons = findViewById(R.id.actReason);
        etReason = findViewById(R.id.etReason);
        btnSubmit = findViewById(R.id.btnSubmit);
        tilReason = findViewById(R.id.tilReason);
        tilComment = findViewById(R.id.tilComment);

        actReasons.setAdapter(new ArrayAdapter(this, R.layout.item_reason, makeReasonsList()));

        btnSubmit.setOnClickListener(view -> {
            sendFeedBack();
        });
    }

    private void sendFeedBack() {
        String reason = actReasons.getText().toString();
        String comment = etReason.getText().toString();

        if (!TextUtils.isEmpty(reason) && !TextUtils.isEmpty(comment)) {
            tilReason.setError(null);
            tilComment.setError(null);
            etReason.setText("");
            actReasons.setText("");

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FeedbackModel feedbackModel = new FeedbackModel(reason, comment, user.getDisplayName(), user.getEmail(), user.getUid());
            RealtimeDBManager.insertFeedBack(feedbackModel);
            Toast.makeText(this, "Feedback submitted successfully!!", Toast.LENGTH_SHORT).show();

        } else {
            tilReason.setError("This Field is mandatory");
            tilComment.setError("This Field is mandatory");
            Toast.makeText(this, "Please select reason and add comment", Toast.LENGTH_SHORT).show();
        }

    }

    private List<String> makeReasonsList() {
        List<String> reasons = new ArrayList<>();
        reasons.add("Issue with Attendance");
        reasons.add("Issue with Leaves");
        reasons.add("Showing wrong performance analysis");
        reasons.add("Facing issue with App");
        reasons.add("Other");

        return reasons;
    }
}