package com.pmn.employeeportal.home;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pmn.employeeportal.R;
import com.pmn.employeeportal.model.Birthday;
import com.pmn.employeeportal.model.Event;
import com.pmn.employeeportal.model.NewJoinee;
import com.pmn.employeeportal.model.Notice;
import com.pmn.employeeportal.utils.FeedMarker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FeedMarker> feedsList;

    public FeedAdapter(ArrayList<FeedMarker> feedsList) {
        this.feedsList = feedsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItem;
        switch (viewType) {
            case 1:
                listItem = layoutInflater.inflate(R.layout.item_notice, parent, false);
                return new NoticeViewHolder(listItem);

            case 2:
                listItem = layoutInflater.inflate(R.layout.item_event, parent, false);
                return new EventViewHolder(listItem);

            case 3:
                listItem = layoutInflater.inflate(R.layout.item_new_joinee, parent, false);
                return new NewJoineeViewHolder(listItem);

            case 4:
                listItem = layoutInflater.inflate(R.layout.item_birthday, parent, false);
                return new BirthdayViewHolder(listItem);

        }


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FeedMarker feedMarker = feedsList.get(position);

        switch (holder.getItemViewType()) {

            case 1:
                Notice notice = (Notice) feedMarker;
                NoticeViewHolder noticeViewHolder = (NoticeViewHolder) holder;
                noticeViewHolder.tvTitle.setText(notice.getTitle());
                noticeViewHolder.tvDesc.setText(notice.getDescription());
                setImage(noticeViewHolder.imgNotice, notice.getImage());
                break;

            case 2:
                Event event = (Event) feedMarker;
                EventViewHolder eventViewHolder = (EventViewHolder) holder;
                eventViewHolder.tvTitle.setText(event.getTitle());
                eventViewHolder.tvDesc.setText(event.getDescription());
                setImage(eventViewHolder.imgBackground, event.getBgImage());
                try {
                    Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).parse(event.getEventDateTime());
                    if (date != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        eventViewHolder.tvDay.setText("" + day);
                        eventViewHolder.tvMonth.setText(new SimpleDateFormat("MMM").format(calendar.getTime()));
                        eventViewHolder.tvTime.setText(new SimpleDateFormat("hh:mm aa").format(calendar.getTime()));

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                NewJoinee newJoinee = (NewJoinee) feedMarker;
                NewJoineeViewHolder newJoineeViewHolder = (NewJoineeViewHolder) holder;
                newJoineeViewHolder.tvName.setText(newJoinee.getName());
                newJoineeViewHolder.tvRole.setText(newJoinee.getDesignation() + " - " + newJoinee.getDepartment());
                setImage(newJoineeViewHolder.imgProfile, newJoinee.getUserImage());
                break;

            case 4:
                Birthday birthday = (Birthday) feedMarker;
                BirthdayViewHolder birthdayViewHolder = (BirthdayViewHolder) holder;
                birthdayViewHolder.tvDesc.setText(birthday.getName());
                setImage(birthdayViewHolder.imgProfile, birthday.getImage());
                break;
        }
    }

    private void setImage(ImageView imageView, String imgUrl) {

        if (!TextUtils.isEmpty(imgUrl)) {

            imageView.setVisibility(View.VISIBLE);

            Glide.with(imageView.getContext())
                    .load(imgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.place_holder)
                    .into(imageView);
        }

    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (feedsList.get(position) instanceof Notice) {
            return 1;
        } else if (feedsList.get(position) instanceof Event) {
            return 2;
        } else if (feedsList.get(position) instanceof NewJoinee) {
            return 3;
        } else if (feedsList.get(position) instanceof Birthday) {
            return 4;
        } else {
            return 1;
        }

    }

    static class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDesc;
        ImageView imgNotice;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            imgNotice = itemView.findViewById(R.id.imgNotice);
        }
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBackground;
        TextView tvMonth;
        TextView tvDay;
        TextView tvTime;
        TextView tvTitle;
        TextView tvDesc;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvTime = itemView.findViewById(R.id.tvTime);
            imgBackground = itemView.findViewById(R.id.imgBackground);
        }
    }

    static class NewJoineeViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvRole;
        ImageView imgProfile;

        public NewJoineeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvRole = itemView.findViewById(R.id.tvRole);
            imgProfile = itemView.findViewById(R.id.imgProfile);

        }
    }

    static class BirthdayViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProfile;
        TextView tvDesc;

        public BirthdayViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.imgProfile);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }


}
