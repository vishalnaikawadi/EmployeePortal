package com.pmn.employeeportal.canteen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pmn.employeeportal.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MenuInterface> list;

    public MenuAdapter(ArrayList<MenuInterface> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItem;
        switch (viewType) {
            case 1:
                listItem = layoutInflater.inflate(R.layout.header_menu, parent, false);
                return new MenuHeaderViewHolder(listItem);

            case 2:
                listItem = layoutInflater.inflate(R.layout.item_menu, parent, false);
                return new MenuItemViewHolder(listItem);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MenuInterface menuInterface = list.get(position);

        switch (holder.getItemViewType()) {

            case 1:
                MenuHeaderModel menuHeaderModel = (MenuHeaderModel) menuInterface;
                MenuHeaderViewHolder menuHeaderViewHolder = (MenuHeaderViewHolder) holder;
                menuHeaderViewHolder.tvHeader.setText(menuHeaderModel.getHeader());
                break;

            case 2:
                MenuItemModel menuItemModel = (MenuItemModel) menuInterface;
                MenuItemViewHolder menuItemViewHolder = (MenuItemViewHolder) holder;
                menuItemViewHolder.tvMenuItem.setText(menuItemModel.getItemName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof MenuHeaderModel) {
            return 1;
        } else {
            return 2;
        }
    }

    static class MenuHeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeader;

        public MenuHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tvHeader);
        }
    }

    static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvMenuItem;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuItem = itemView.findViewById(R.id.tvMenuItem);
        }
    }
}
