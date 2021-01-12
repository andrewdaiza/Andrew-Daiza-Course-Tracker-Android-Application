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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.database.entities.Assessment;
import edu.wgu.c196.andrewdaiza.utilities.CallAlert;
import edu.wgu.c196.andrewdaiza.utilities.DatePickerFragment;
import edu.wgu.c196.andrewdaiza.viewmodels.AddEditAssessmentViewModel;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateFormatting;
import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateWithFormat;


public class AssessmentAddEditActivity extends AppCompatActivity {

    public static final String COURSE_ID =
            "edu.wgu.c196.andrewdaiza.utilities.course_editor.COURSE_ID";
    public static final String ASSESSMENT_ID =
            "edu.wgu.c196.andrewdaiza.utilities.assessment_editor.ASSESSMENT_ID";
    public static final String ALARM_CHAN_ID =
            "edu.wgu.c196.andrewdaiza.utilities.notification.ALARM_CHAN_ID";

    public static final String START_DATE_ALERT_CHAN = "1";
    public static final String END_DATE_ALERT_CHAN = "2";

    public static final String LOG_TAG_FUN = "EditAssessmentTag";

    private AddEditAssessmentViewModel mViewModel;

    SimpleDateFormat format;

    long timeLong;
    protected boolean mActivity;
    protected int mId;
    protected int mIdEntity;
    protected ViewModelProvider.Factory factory;


    private Date dateComplete;
    private final AssessmentViewObject assessmentViewObject = new AssessmentViewObject();

    @BindView(R.id.assessment_title_view_text)
    protected EditText mVarTextTitle;

    @BindView(R.id.assessment_type_select)
    protected Spinner mVarType;

    @BindView(R.id.status_type_select)
    protected Spinner mVarStatus;

    @BindView(R.id.due_date_select)
    protected TextView mVarTextDateComplete;

    @BindView(R.id.delete_assesment)
    protected Button deleteAssessmentButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewContent());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());


        mViewModel = new ViewModelProvider(this, factory).get(AddEditAssessmentViewModel.class);
        mViewModel.mAllAssessments.observe(this, (assessment) -> {
            if (assessment != null) {
                mVarTextTitle.setText(assessmentViewObject.title == null ? assessment.getAssessment_title() : assessmentViewObject.title);

                SpinnerAdapter spinnerAdaptTypeAssessment = mVarType.getAdapter();
                ArrayAdapter<String> assessmentTypeAdapter = (ArrayAdapter<String>)spinnerAdaptTypeAssessment;
                mVarType.setSelection(assessmentViewObject.assessmentTypePosition == null ?
                        assessmentTypeAdapter.getPosition(assessment.getAssessment_type()) :
                        assessmentViewObject.assessmentTypePosition);
                SpinnerAdapter statusSpinnerAdapter = mVarStatus.getAdapter();
                ArrayAdapter<String> statusAdapter = (ArrayAdapter<String>) statusSpinnerAdapter;
                mVarStatus.setSelection(assessmentViewObject.statusPosition == null ?
                        statusAdapter.getPosition(assessment.getAssessment_status()) :
                        assessmentViewObject.statusPosition);
                dateComplete = assessmentViewObject.completionDate == null ?
                        assessment.getAssessment_completionDate() :
                        getDateWithFormat(assessmentViewObject.completionDate);
                if (dateComplete != null) {
                    mVarTextDateComplete.setText(getDateFormatting(dateComplete));
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        mIdEntity = extras.getInt(COURSE_ID);

        if (extras.getInt(ASSESSMENT_ID) == 0) {
            setTitle("New Assessment");
            deleteAssessmentButton.setVisibility(View.GONE);
            mActivity = true;
        } else {
            setTitle("Edit Assessment");
            mId = extras.getInt(ASSESSMENT_ID);
            mViewModel.initializeAssessment(mId);
        }

    }


    protected void closeActivity() {
        finish();
    }

    protected void showValidationError(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
                .setCancelable(true)
                .setPositiveButton("Okay", (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }


    @OnClick(R.id.save_assesment)
    protected void save() {
        String title = String.valueOf(mVarTextTitle.getText());
        String assessmentType = String.valueOf(mVarType.getSelectedItem());
        String status = String.valueOf(mVarStatus.getSelectedItem());
        String completionDate = String.valueOf(mVarTextDateComplete.getText());

        if (title.trim().isEmpty() || completionDate.trim().isEmpty() || status.trim().isEmpty() || assessmentType.trim().isEmpty()) {
            showValidationError("Missing Fields", "Please fill out fields");
            return;
        }
        mViewModel.saveAssessment(mIdEntity, assessmentType, title, status, completionDate);

        Toast.makeText(AssessmentAddEditActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @OnClick(R.id.assesment_alarm)
    protected void saveWithAlarm() {
        String textTitle = String.valueOf(mVarTextTitle.getText());
        String textType = String.valueOf(mVarType.getSelectedItem());
        String textStatus = String.valueOf(mVarStatus.getSelectedItem());
        String dateComplete = String.valueOf(mVarTextDateComplete.getText());

        if (textTitle.trim().isEmpty() || dateComplete.trim().isEmpty() || textStatus.trim().isEmpty() || textType.trim().isEmpty()) {
            showValidationError("Missing Fields", "Please fill out fields");
            return;
        }
        mViewModel.saveAssessment(mIdEntity, textType, textTitle, textStatus, dateComplete);
        onAlarmNotification(textTitle, dateComplete);
        Toast.makeText(AssessmentAddEditActivity.this, textTitle + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @OnClick(R.id.delete_assesment)
    protected void delete() {
        Assessment course = mViewModel.mAllAssessments.getValue();
        String textTitle = course.getAssessment_title();
        mViewModel.deleteAssessment();
        String message = textTitle + " Deleted";
        Toast.makeText(AssessmentAddEditActivity.this, message, Toast.LENGTH_SHORT).show();
        closeActivity();
    }


        private void onAlarmNotification(String title, String completionDate) {

            try {


               long num = getDateWithFormat(completionDate).getTime();



            Random rand = new Random();
            int randomId = rand.nextInt(499);

            String alertTitle = "WGU Student Term Scheduler Assessment Alert";
            String message = title + " scheduled complete date is " + completionDate;
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            boolean start = "start".equals("end");
            Intent intent = new Intent(this, CallAlert.class);
            intent.putExtra("title", alertTitle);
            intent.putExtra("message", message);
            intent.putExtra("id", randomId);
            intent.putExtra(ALARM_CHAN_ID, start ? START_DATE_ALERT_CHAN : END_DATE_ALERT_CHAN);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, randomId, intent, 0);

            alarmManager.set(AlarmManager.RTC_WAKEUP, num, pendingIntent);
            System.out.println(num);
        } catch(NumberFormatException e) {
                e.printStackTrace();
    }
    }



    protected int getViewContent() {
        return R.layout.activity_assessment_editor;
    }



    @OnClick(R.id.due_date_select)
    protected void OnClickDateComplete() {
        DialogFragment dateDialog = new DatePickerFragment(mVarTextDateComplete, dateComplete);
        dateDialog.show(getSupportFragmentManager(), "assessmentDatePicker");
    }


    private static class AssessmentViewObject {
        private String title;
        private Integer assessmentTypePosition;
        private Integer statusPosition;
        private String completionDate;
    }
}
