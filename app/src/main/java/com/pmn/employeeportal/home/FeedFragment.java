package com.pmn.employeeportal.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.pmn.employeeportal.R;
import com.pmn.employeeportal.model.FeedModel;
import com.pmn.employeeportal.utils.FeedMarker;
import com.pmn.employeeportal.utils.RealtimeDBManager;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment {

    private RecyclerView rvFeed;

    private ArrayList<FeedMarker> feedsList = new ArrayList<>();
    private FeedAdapter feedAdapter;


    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        rvFeed = view.findViewById(R.id.rvFeed);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFeeds();
    }

    private void getFeeds() {
        RealtimeDBManager.mDatabase.child(RealtimeDBManager.FEEDS).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(getContext(), "Something Went Wrong, Unable to fetch data.", Toast.LENGTH_SHORT).show();
            } else {
                if (task.getResult() != null) {
                    FeedModel feedModel = task.getResult().getValue(FeedModel.class);

                    if (feedModel != null) {
                        if (feedModel.getNotices() != null) {
                            feedsList.addAll(feedModel.getNotices());
                        }
                        if (feedModel.getBirthdays() != null) {
                            feedsList.addAll(feedModel.getBirthdays());
                        }
                        if (feedModel.getEvents() != null) {
                            feedsList.addAll(feedModel.getEvents());
                        }
                        if (feedModel.getNewJoinees() != null) {
                            feedsList.addAll(feedModel.getNewJoinees());
                        }

                        feedAdapter = new FeedAdapter(feedsList);
                        rvFeed.setAdapter(feedAdapter);
                    }


                }
            }
        });
    }
}