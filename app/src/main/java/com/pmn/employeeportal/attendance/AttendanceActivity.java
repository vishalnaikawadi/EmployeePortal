package com.pmn.employeeportal.attendance;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder;
import com.kizitonwose.calendarview.ui.ViewContainer;
import com.pmn.employeeportal.R;
import com.pmn.employeeportal.model.AttendanceModel;
import com.pmn.employeeportal.utils.RealtimeDBManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

import kotlin.Unit;

public class AttendanceActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView exYearText;
    ImageView exNextMonthImage;
    ImageView exPreviousMonthImage;

    private ArrayList<String> attendancePresentList = new ArrayList<>();
    private ArrayList<String> attendanceAbsentList = new ArrayList<>();

    private DateTimeFormatter monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        calendarView = findViewById(R.id.calenderView);
        exYearText = findViewById(R.id.exYearText);
        exNextMonthImage = findViewById(R.id.exNextMonthImage);
        exPreviousMonthImage = findViewById(R.id.exPreviousMonthImage);
        getAttendanceData();

        exNextMonthImage.setOnClickListener(v -> {
            CalendarMonth calendarMonth = calendarView.findFirstVisibleMonth();
            if (calendarMonth != null) {
                calendarView.smoothScrollToMonth(calendarMonth.getYearMonth().plusMonths(1));
            }
        });

        exPreviousMonthImage.setOnClickListener(v -> {
            CalendarMonth calendarMonth = calendarView.findFirstVisibleMonth();
            if (calendarMonth != null) {
                calendarView.smoothScrollToMonth(calendarMonth.getYearMonth().minusMonths(1));
            }
        });


    }

    private void getAttendanceData() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        RealtimeDBManager.mDatabase.child(RealtimeDBManager.USERS).child(firebaseUser.getUid()).child(RealtimeDBManager.ATTENDANCE).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, "Something Went Wrong, Unable to fetch data.", Toast.LENGTH_SHORT).show();
            } else {
                if (task.getResult() != null) {
                    AttendanceModel model = task.getResult().getValue(AttendanceModel.class);

                    if (model != null) {
                        if (model.getPresent() != null) {
                            attendancePresentList.addAll(model.getPresent());
                        }
                        if (model.getAbsent() != null) {
                            attendanceAbsentList.addAll(model.getAbsent());
                        }

                        calendarRelatedOperations();

                    }
                }
            }
        });
    }

    private void setCalendarMonths() {

        calendarView.setMonthHeaderBinder(new MonthHeaderFooterBinder<ViewContainer>() {
            @NonNull
            @Override
            public ViewContainer create(@NonNull View view) {
                return new MonthViewContainer(view);
            }

            @Override
            public void bind(@NonNull ViewContainer viewContainer, @NonNull CalendarMonth calendarMonth) {
                MonthViewContainer container = (MonthViewContainer) viewContainer;

                /*if (container.legendLayout.getTag() != null) {
                    container.legendLayout.setTag(calendarMonth.getYearMonth());

                    for (int i = 0; i < container.legendLayout.getChildCount(); i++) {
                        View childView = container.legendLayout.getChildAt(i);
                        if (childView instanceof TextView) {
                            ((TextView) childView).setText("temp");
                        }
                    }
                }*/
            }
        });

    }

    private void setCalendarDays() {
        calendarView.setDayBinder(new DayBinder<ViewContainer>() {
            @NonNull
            @Override
            public ViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NonNull ViewContainer viewContainer, @NonNull CalendarDay calendarDay) {
                DayViewContainer dayViewContainer = (DayViewContainer) viewContainer;
                dayViewContainer.textView.setText(String.valueOf(calendarDay.getDate().getDayOfMonth()));

                final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                if (attendancePresentList.size() > 0) {
                    for (int i = 0; i < attendancePresentList.size(); i++) {

                        LocalDate dt = LocalDate.from(dtf.parse(attendancePresentList.get(i)));
                        if (dt.equals(calendarDay.getDate())) {
                            dayViewContainer.exPresent.setVisibility(View.VISIBLE);
                        }
                    }
                }

                if (attendanceAbsentList.size() > 0) {
                    for (int i = 0; i < attendanceAbsentList.size(); i++) {
                        LocalDate dt = LocalDate.from(dtf.parse(attendanceAbsentList.get(i)));
                        if (dt.equals(calendarDay.getDate())) {
                            dayViewContainer.exAbsent.setVisibility(View.VISIBLE);
                        }
                    }
                }


            }
        });

    }


    private void calendarRelatedOperations() {
        YearMonth currentMonth = YearMonth.now();
        YearMonth firstMonth = currentMonth.minusMonths(10);
        YearMonth lastMonth = currentMonth.plusMonths(10);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek);
        calendarView.scrollToMonth(currentMonth);


        calendarView.setMonthScrollListener(calendarMonth -> {
            String title = monthTitleFormatter.format(calendarMonth.getYearMonth()) + " " + calendarMonth.getYearMonth().getYear();
            exYearText.setText(title);
            return Unit.INSTANCE;
        });

        setCalendarDays();
        setCalendarMonths();
    }


    static class DayViewContainer extends ViewContainer {
        TextView textView;
        View exPresent;
        View exAbsent;

        public DayViewContainer(@NonNull View view) {
            super(view);

            textView = view.findViewById(R.id.calendarDayText);
            exPresent = view.findViewById(R.id.exPresent);
            exAbsent = view.findViewById(R.id.exAbsent);
        }
    }

    static class MonthViewContainer extends ViewContainer {
        LinearLayout legendLayout;

        public MonthViewContainer(@NonNull View view) {
            super(view);

            legendLayout = view.findViewById(R.id.legendLayout);

        }
    }

}