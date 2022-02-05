package com.pmn.employeeportal.canteen;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pmn.employeeportal.R;
import com.pmn.employeeportal.model.CanteenModel;
import com.pmn.employeeportal.utils.RealtimeDBManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CanteenActivity extends AppCompatActivity {

    private RecyclerView rvMenu;
    private MenuAdapter menuAdapter;
    private ArrayList<MenuInterface> menuList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen);

        rvMenu = findViewById(R.id.rvMenu);
        getMenu();
    }

    private void getMenu() {
        RealtimeDBManager.mDatabase.child(RealtimeDBManager.CANTEEN).get().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Toast.makeText(this, "Something Went Wrong, Unable to fetch data.", Toast.LENGTH_SHORT).show();
            } else {

                if (task.getResult() != null) {

                    CanteenModel canteenModel = task.getResult().getValue(CanteenModel.class);

                    if (canteenModel != null) {

                        if (!canteenModel.getBreakFast().isEmpty()) {
                            List<String> items = Arrays.asList(canteenModel.getBreakFast().split("\\s*,\\s*"));
                            menuList.add(new MenuHeaderModel("Breakfast"));
                            for (int i = 0; i < items.size(); i++) {
                                MenuItemModel menuItemModel = new MenuItemModel(items.get(i));
                                menuList.add(menuItemModel);
                            }
                        }

                        if (!canteenModel.getLunch().isEmpty()) {
                            menuList.add(new MenuHeaderModel("Lunch"));
                            List<String> items = Arrays.asList(canteenModel.getLunch().split("\\s*,\\s*"));
                            for (int i = 0; i < items.size(); i++) {
                                MenuItemModel menuItemModel = new MenuItemModel(items.get(i));
                                menuList.add(menuItemModel);
                            }
                        }

                        if (!canteenModel.getDinner().isEmpty()) {
                            menuList.add(new MenuHeaderModel("Dinner"));
                            List<String> items = Arrays.asList(canteenModel.getDinner().split("\\s*,\\s*"));
                            for (int i = 0; i < items.size(); i++) {
                                MenuItemModel menuItemModel = new MenuItemModel(items.get(i));
                                menuList.add(menuItemModel);
                            }
                        }

                        menuAdapter = new MenuAdapter(menuList);
                        rvMenu.setAdapter(menuAdapter);

                    }

                }

            }

        });
    }
}