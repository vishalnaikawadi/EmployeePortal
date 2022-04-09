package com.pmn.employeeportal.leave;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pmn.employeeportal.R;
import com.pmn.employeeportal.model.LeaveModel;
import com.pmn.employeeportal.utils.RealtimeDBManager;

import java.util.ArrayList;
import java.util.Locale;

public class LeavesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<LeaveModel> leavesList;

    public LeavesAdapter(ArrayList<LeaveModel> leavesList) {
        this.leavesList = leavesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_leave, parent, false);
        return new LeaveViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        LeaveModel leave = leavesList.get(position);

        LeaveViewHolder leaveViewHolder = (LeaveViewHolder) holder;

        leaveViewHolder.tvFromToDate.setText(leave.fromDate + " - " + leave.toDate);
        leaveViewHolder.tvReason.setText(leave.reason);

        RealtimeDBManager.mDatabase.child(RealtimeDBManager.USER_LEAVES).child(leave.id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LeaveModel leave = snapshot.getValue(LeaveModel.class);
                if (leave != null) {
                    leaveViewHolder.tvRemark.setText(leave.remark);
                    leaveViewHolder.tvStatus.setText(leave.status);
                    switch (leave.status.toUpperCase(Locale.ROOT)) {
                        case "APPROVED":
                            leaveViewHolder.container.setBackgroundColor(
                                    leaveViewHolder.container.getContext().getColor(R.color.color_approved)
                            );
                            break;

                        case "UNAPPROVED":
                            leaveViewHolder.container.setBackgroundColor(
                                    leaveViewHolder.container.getContext().getColor(R.color.color_unapproved)
                            );
                            break;

                        case "DECLINED":
                            leaveViewHolder.container.setBackgroundColor(
                                    leaveViewHolder.container.getContext().getColor(R.color.color_decline)
                            );
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return leavesList.size();
    }

    static class LeaveViewHolder extends RecyclerView.ViewHolder {

        TextView tvFromToDate;
        TextView tvReason;
        TextView tvRemark;
        TextView tvStatus;
        ConstraintLayout container;


        public LeaveViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFromToDate = itemView.findViewById(R.id.tvFromToDate);
            tvReason = itemView.findViewById(R.id.tvReason);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            container = itemView.findViewById(R.id.container);
        }
    }
}
