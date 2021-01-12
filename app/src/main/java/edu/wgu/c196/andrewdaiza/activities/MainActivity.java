package edu.wgu.c196.andrewdaiza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.viewmodels.MainViewModel;


public class MainActivity extends AppCompatActivity {

private MainViewModel mViewModel;


    private int cCompCourses = 0;
    private int cProgressCourses = 0;
    private int cDroppedCourses = 0;
    private int cFailedCourses = 0;
    private int cPassedAssessments = 0;
    private int cPendingAssessments = 0;
    private int cFailedAssessments = 0;


    @BindView(R.id.coursesCompletedTextView)
    protected TextView mCompletedCourses;
    @BindView(R.id.coursesPendingTextView)
    protected TextView mPendingCourses;
    @BindView(R.id.coursesDroppedTextView)
    protected TextView mDroppedCourses;
    @BindView(R.id.coursesFailedTextView)
    protected TextView mFailedCourses;
    @BindView(R.id.assessmentsPassedTextView)
    protected TextView mPassedAssessments;
    @BindView(R.id.assessmentsPendingTextView)
    protected TextView mPendingAssessments;
    @BindView(R.id.assessmentsFailedTextView)
    protected TextView mFailedAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        mViewModel.mCompletedCourses.observe(MainActivity.this, courses -> {
            cCompCourses = courses;
            countUpdater(cCompCourses, mCompletedCourses);
        });
        mViewModel.mPendingCourses.observe(MainActivity.this, courses -> {
            cProgressCourses = courses;
            countUpdater(cProgressCourses, mPendingCourses);
        });
        mViewModel.mDroppedCourses.observe(MainActivity.this, courses -> {
            cDroppedCourses = courses;
            countUpdater(cDroppedCourses, mDroppedCourses);
        });
        mViewModel.mFailedCourses.observe(MainActivity.this, courses -> {
            cFailedCourses = courses;
            countUpdater(cFailedCourses, mFailedCourses);
        });
        mViewModel.mPassedAssessments.observe(MainActivity.this, assessments -> {
            cPassedAssessments = assessments;
            countUpdater(cPassedAssessments, mPassedAssessments);
        });
        mViewModel.mPendingAssessments.observe(MainActivity.this, assessments -> {
            cPendingAssessments = assessments;
            countUpdater(cPendingAssessments, mPendingAssessments);
        });
        mViewModel.mFailedAssessments.observe(MainActivity.this, assessments -> {
            cFailedAssessments = assessments;
            countUpdater(cFailedAssessments, mFailedAssessments);
        });

    }

    private void countUpdater(int numerator, TextView textView) {
        String num = Integer.toString(numerator);
        textView.setText(num);
    }

    @OnClick(R.id.view_terms)
    protected void termsClicker() {
        Intent intent = new Intent(MainActivity.this, TermsListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.populate_database)
    protected void populateDatabaseClicker() {
        mViewModel.addAllSampleData();
    }



    @OnClick(R.id.clear_database)
    protected void clearDatabaseClicker() {
        mViewModel.clearAllData();
    }

}
