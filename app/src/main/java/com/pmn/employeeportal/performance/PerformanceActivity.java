package com.pmn.employeeportal.performance;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pmn.employeeportal.R;
import com.pmn.employeeportal.model.PerformanceModel;
import com.pmn.employeeportal.utils.RealtimeDBManager;

import java.util.ArrayList;
import java.util.List;

public class PerformanceActivity extends AppCompatActivity {

    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        anyChartView = findViewById(R.id.anyChartView);

        getPerformanceData();

    }

    private void getPerformanceData() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        RealtimeDBManager.mDatabase.child(RealtimeDBManager.USERS).child(firebaseUser.getUid()).child(RealtimeDBManager.PERFORMANCE).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, "Something Went Wrong, Unable to fetch data.", Toast.LENGTH_SHORT).show();
            } else {
                if (task.getResult() != null) {
                    PerformanceModel model = task.getResult().getValue(PerformanceModel.class);

                    if (model != null) {

                        Pie pie = AnyChart.pie();

                        List<DataEntry> data = new ArrayList<>();
                        data.add(new ValueDataEntry("Consistency", model.getConsistency()));
                        data.add(new ValueDataEntry("Integrity", model.getIntegrity()));
                        data.add(new ValueDataEntry("Productivity", model.getProductivity()));

                        pie.data(data);

                        anyChartView.setChart(pie);

                    }
                }
            }
        });
    }
}