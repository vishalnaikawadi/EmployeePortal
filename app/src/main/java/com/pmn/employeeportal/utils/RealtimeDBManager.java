package com.pmn.employeeportal.utils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pmn.employeeportal.model.FeedbackModel;
import com.pmn.employeeportal.model.LeaveModel;
import com.pmn.employeeportal.model.User;

public class RealtimeDBManager {

    public final static String USERS = "users";
    public final static String FEEDS = "feeds";
    public final static String FEEDBACK = "feedback";
    public final static String USER_LEAVES = "leaves";
    public final static String LEAVES = "leaves";
    public final static String CANTEEN = "canteen";
    public final static String ATTENDANCE = "attendance";
    public final static String PERFORMANCE = "performance";

    public static final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void insertUser(FirebaseUser firebaseUser) {

        User user = new User("", firebaseUser.getEmail(), "", "");
        mDatabase.child(USERS).child(firebaseUser.getUid()).push().setValue(user);
    }

    public static void insertFeedBack(FeedbackModel feedbackModel) {
        mDatabase.child(FEEDBACK).child(feedbackModel.reason).child(feedbackModel.userId).setValue(feedbackModel);
    }

    public static void insertUserLeaves(FirebaseUser firebaseUser, LeaveModel model) {
        mDatabase.child(USERS).child(firebaseUser.getUid()).child(USER_LEAVES).child(model.id).setValue(model);
    }

    public static void insertLeaves(LeaveModel model) {
        mDatabase.child(LEAVES).child(model.id).setValue(model);
    }


}
