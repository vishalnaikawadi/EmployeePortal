package com.pmn.employeeportal.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.pmn.employeeportal.R;
import com.pmn.employeeportal.attendance.AttendanceActivity;
import com.pmn.employeeportal.auth.LoginActivity;
import com.pmn.employeeportal.canteen.CanteenActivity;
import com.pmn.employeeportal.feedback.FeedbackActivity;
import com.pmn.employeeportal.leave.LeavesActivity;
import com.pmn.employeeportal.performance.PerformanceActivity;

public class MenuFragment extends Fragment {

    private MaterialCardView cvLogOut;
    private MaterialCardView cvFeedback;
    private MaterialCardView cvCanteen;
    private MaterialCardView cvLeaves;
    private MaterialCardView cvAttendance;
    private MaterialCardView cvPerformance;


    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        //bindings
        cvLogOut = view.findViewById(R.id.cvLogout);
        cvFeedback = view.findViewById(R.id.cvFeedback);
        cvCanteen = view.findViewById(R.id.cvCanteen);
        cvLeaves = view.findViewById(R.id.cvLeaves);
        cvAttendance = view.findViewById(R.id.cvAttendance);
        cvPerformance = view.findViewById(R.id.cvPerformance);
        //click listeners
        cvLogOut.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        cvFeedback.setOnClickListener(view12 -> {
            startActivity(new Intent(getActivity(), FeedbackActivity.class));
        });

        cvCanteen.setOnClickListener(view12 -> {
            startActivity(new Intent(getActivity(), CanteenActivity.class));
        });

        cvLeaves.setOnClickListener(view13 -> {
            startActivity(new Intent(getActivity(), LeavesActivity.class));
        });

        cvAttendance.setOnClickListener(view14 -> {
            startActivity(new Intent(getActivity(), AttendanceActivity.class));
        });

        cvPerformance.setOnClickListener(view14 -> {
            startActivity(new Intent(getActivity(), PerformanceActivity.class));
        });

        return view;
    }
}