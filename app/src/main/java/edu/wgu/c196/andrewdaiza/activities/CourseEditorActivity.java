package edu.wgu.c196.andrewdaiza.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.adapters.AssessmentAdapter;
import edu.wgu.c196.andrewdaiza.database.entities.Course;
import edu.wgu.c196.andrewdaiza.utilities.CallAlert;
import edu.wgu.c196.andrewdaiza.utilities.DatePickerFragment;
import edu.wgu.c196.andrewdaiza.viewmodels.EditCourseViewModel;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateFormatting;
import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateWithFormat;


public class CourseEditorActivity extends AppCompatActivity {

    public static final String TERM_ID =
            "edu.wgu.c196.andrewdaiza.utilities.term_editor.TERM_ID";
    public static final String COURSE_ID =
            "edu.wgu.c196.andrewdaiza.utilities.course_editor.COURSE_ID";
    public static final String ASSESSMENT_ID =
            "edu.wgu.c196.andrewdaiza.utilities.assessment_editor.ASSESSMENT_ID";
    public static final String ALARM_CHAN_ID =
            "edu.wgu.c196.andrewdaiza.utilities.notification.ALARM_CHAN_ID";

    public static final String START_DATE_ALERT_CHAN = "1";
    public static final String END_DATE_ALERT_CHAN = "2";

    protected boolean mActivity;
    protected int mId;
    protected int mIdEntity;
    protected ViewModelProvider.Factory factory;

    private EditCourseViewModel mViewModel;
    private AssessmentAdapter mAdapter;
    private Date startDate;
    private Date endDate;
    private final CourseViewObject courseViewObject = new CourseViewObject();

    @BindView(R.id.course_title_text)
    protected EditText mVarTextTitle;
    @BindView(R.id.status_select)
    protected Spinner mVarStatus;
    @BindView(R.id.start_date_select)
    protected TextView mStartDate;
    @BindView(R.id.end_date_select)
    protected TextView mEndDate;
    @BindView(R.id.save_alarm_start)
    protected TextView mStartDateAlarm;
    @BindView(R.id.course_assessment_list)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.add_assessment)
    protected FloatingActionButton mFab;
    @BindView(R.id.btn_course_notes)
    protected Button mCourseNotes;
    @BindView(R.id.btn_course_mentors)
    protected Button mCourseMentors;
    @BindView(R.id.delete_course)
    protected Button deleteCourseButton;

    private void loadState() {
        mVarTextTitle.setText(courseViewObject.title);
        mVarStatus.setSelection(courseViewObject.status);
        mStartDate.setText(courseViewObject.startDate);
        if (courseViewObject.startDate != null) {
            startDate = getDateWithFormat(courseViewObject.startDate);
        }
        mEndDate.setText(courseViewObject.endDate);
        if (courseViewObject.endDate != null) {
            endDate = getDateWithFormat(courseViewObject.endDate);
        }
    }


    protected void openActivity(Intent intent) {
        startActivity(intent);
    }

    protected void closeActivity() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new AssessmentAdapter();

        mAdapter.setClickListener(assessment -> {
            Intent intent = new Intent(CourseEditorActivity.this, AssessmentAddEditActivity.class);
            intent.putExtra(COURSE_ID, mId);
            intent.putExtra(ASSESSMENT_ID, assessment.getId());
            openActivity(intent);
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());


        mViewModel = new ViewModelProvider(this, factory).get(EditCourseViewModel.class);
        mViewModel.mAllCourses.observe(this, (course) -> {
            if (course != null) {
                mVarTextTitle.setText(courseViewObject.title == null ? course.getCourse_title() : courseViewObject.title);

                SpinnerAdapter ssa = mVarStatus.getAdapter();
                ArrayAdapter<String> statusAdapter = (ArrayAdapter<String>) ssa;
                mVarStatus.setSelection(courseViewObject.status == null ? statusAdapter.getPosition(course.getCourse_status()) : courseViewObject.status);
                startDate = courseViewObject.startDate == null ? course.getCourse_startDate() : getDateWithFormat(courseViewObject.startDate);
                endDate = courseViewObject.endDate == null ? course.getCourse_endDate() : getDateWithFormat(courseViewObject.endDate);
                if (startDate != null) {
                    mStartDate.setText(getDateFormatting(startDate));
                }
                if (endDate != null) {
                    mEndDate.setText(getDateFormatting(endDate));
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mIdEntity = extras.getInt(TERM_ID);

            if (extras.getInt(COURSE_ID) == 0) {
                setTitle(getString(R.string.new_course));
                mActivity = true;
                deleteCourseButton.setVisibility(View.GONE);
                mCourseNotes.setVisibility(View.GONE);
                mCourseMentors.setVisibility(View.GONE);
                mFab.setVisibility(View.GONE);
            } else {
                setTitle(getString(R.string.edit_course));
                mId = extras.getInt(COURSE_ID);
                mViewModel.initializeCourse(mId);
                mViewModel.initializeAssessmentsCourse(mId);
                mViewModel.getAssessmentsCourse().observe(this, (assessments) ->
                        mAdapter.submitList(assessments));
            }
            mFab.setOnClickListener(view -> {
                Intent intent = new Intent(CourseEditorActivity.this, AssessmentAddEditActivity.class);
                intent.putExtra(COURSE_ID, mId);
                openActivity(intent);
            });
        }


    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }



    protected void showValidationError(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
                .setCancelable(true)
                .setPositiveButton("Okay", (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.save_course)
    protected void save() {
        String title = String.valueOf(mVarTextTitle.getText());
        String status = String.valueOf(mVarStatus.getSelectedItem());
        String termId = String.valueOf(mIdEntity);
        String startDate = String.valueOf(mStartDate.getText());
        String endDate = String.valueOf(mEndDate.getText());

        if (title.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty()  || title.trim().isEmpty()) {
            showValidationError("Missing fields", "Please enter all Fields");
            return;
        }
        mViewModel.saveCourse(title, termId, status, startDate, endDate);
        Toast.makeText(CourseEditorActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @OnClick(R.id.save_alarm_start)
    protected void startSaveAlarm() {
        String title = String.valueOf(mVarTextTitle.getText());
        String status = String.valueOf(mVarStatus.getSelectedItem());
        String termId = String.valueOf(mIdEntity);
        String startDate = String.valueOf(mStartDate.getText());
        String endDate = String.valueOf(mEndDate.getText());

        if (title.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty()  || title.trim().isEmpty()) {
            showValidationError("Missing fields", "Please enter all Fields");
            return;
        }
        mViewModel.saveCourse(title, termId, status, startDate, endDate);
        onAlarmNotificationStart(title, startDate);
        Toast.makeText(CourseEditorActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @OnClick(R.id.save_alarm_end)
    protected void endSaveAlarm() {
        String title = String.valueOf(mVarTextTitle.getText());
        String status = String.valueOf(mVarStatus.getSelectedItem());
        String termId = String.valueOf(mIdEntity);
        String startDate = String.valueOf(mStartDate.getText());
        String endDate = String.valueOf(mEndDate.getText());

        if (title.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty()  || title.trim().isEmpty()) {
            showValidationError("Missing fields", "Please enter all Fields");
            return;
        }
        mViewModel.saveCourse(title, termId, status, startDate, endDate);
        onAlarmNotificationEnd(title, endDate);
        Toast.makeText(CourseEditorActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    private void onAlarmNotificationStart(String title, String startDate) {
        Calendar calendarNow = Calendar.getInstance();

        long num = getDateWithFormat(startDate).getTime();

        Random rand = new Random();
        int randomId = rand.nextInt(499);

        String alertTitle = "WGU Student Term Scheduler course Alert";
        String message = title + " scheduled course start date is " + startDate;
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        boolean start = "start".equals("end");
        Intent intent = new Intent(this, CallAlert.class);
        intent.putExtra("title", alertTitle);
        intent.putExtra("message", message);
        intent.putExtra("id", randomId);
        intent.putExtra(ALARM_CHAN_ID, start ? START_DATE_ALERT_CHAN : END_DATE_ALERT_CHAN);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, randomId, intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, num, pendingIntent);

        System.out.println(calendarNow.getTime().getTime());
    }

    private void onAlarmNotificationEnd(String title, String endDate) {
        Calendar calendarNow = Calendar.getInstance();

        long num = getDateWithFormat(endDate).getTime();

        Random rand = new Random();
        int randomId = rand.nextInt(499);

        String alertTitle = "WGU Student Term Scheduler course Alert";
        String message = title + " scheduled course end date is " + endDate;
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        boolean start = "start".equals("end");
        Intent intent = new Intent(this, CallAlert.class);
        intent.putExtra("title", alertTitle);
        intent.putExtra("message", message);
        intent.putExtra("id", randomId);
        intent.putExtra(ALARM_CHAN_ID, start ? START_DATE_ALERT_CHAN : END_DATE_ALERT_CHAN);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, randomId, intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, num, pendingIntent);
    }




    @OnClick(R.id.delete_course)
    protected void delete() {
        Course course = mViewModel.mAllCourses.getValue();
                        mViewModel.deleteCourse();
                        Toast.makeText(CourseEditorActivity.this, course.getCourse_title() + " Removed", Toast.LENGTH_SHORT).show();
                        closeActivity();
    }

    @OnClick(R.id.btn_course_notes)
    protected void onCourseNotesClick() {
        Intent intent = new Intent(CourseEditorActivity.this, NotesListActivity.class);
        intent.putExtra(COURSE_ID, mId);
        openActivity(intent);
    }

    @OnClick(R.id.btn_course_mentors)
    protected void onCourseMentorsClick() {
        Intent intent = new Intent(CourseEditorActivity.this, MentorsListActivity.class);
        intent.putExtra(COURSE_ID, mId);
        openActivity(intent);
    }


    @OnClick(R.id.start_date_select)
    protected void onStartDateClick() {
        DialogFragment dateDialog = new DatePickerFragment(mStartDate, startDate);
        dateDialog.show(getSupportFragmentManager(), "courseStartDatePicker");
    }


    @OnClick(R.id.end_date_select)
    protected void onEndDateClick() {
        DialogFragment dateDialog = new DatePickerFragment(mEndDate, endDate);
        dateDialog.show(getSupportFragmentManager(), "courseEndDatePicker");
    }


    private static class CourseViewObject {
        private String title;
        private Integer status;
        private String startDate;
        private String endDate;
    }
}
