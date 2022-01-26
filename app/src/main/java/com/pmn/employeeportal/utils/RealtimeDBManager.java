package com.pmn.employeeportal.utils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pmn.employeeportal.model.FeedModel;
import com.pmn.employeeportal.model.User;

public class RealtimeDBManager {

    public final static String USERS = "users";
    public final static String FEEDS = "feeds";

    public static final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void insertUser(FirebaseUser firebaseUser) {

        User user = new User("", firebaseUser.getEmail(), "", "");
        mDatabase.child(USERS).child(firebaseUser.getUid()).setValue(user);
    }


}
