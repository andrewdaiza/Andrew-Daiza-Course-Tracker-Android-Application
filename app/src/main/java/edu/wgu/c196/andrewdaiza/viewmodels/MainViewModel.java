package edu.wgu.c196.andrewdaiza.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import edu.wgu.c196.andrewdaiza.database.DataRepository;

public class MainViewModel extends AndroidViewModel {

    protected final DataRepository mDataRepository;

    public final LiveData<Integer> mCompletedCourses;

    public final LiveData<Integer> mPendingCourses;

    public final LiveData<Integer> mDroppedCourses;

    public final LiveData<Integer> mFailedCourses;

    public final LiveData<Integer> mPendingAssessments;

    public final LiveData<Integer> mPassedAssessments;

    public final LiveData<Integer> mFailedAssessments;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
        mCompletedCourses = mDataRepository.getCoursesByStatus("Complete");
        mPendingCourses = mDataRepository.getCoursesByStatus("In Progress");
        mDroppedCourses = mDataRepository.getCoursesByStatus("Dropped");
        mFailedCourses = mDataRepository.getCoursesByStatus("Failed");
        mPassedAssessments = mDataRepository.getAssessmentsByStatus("Pass");
        mPendingAssessments = mDataRepository.getAssessmentsByStatus("Pending");
        mFailedAssessments = mDataRepository.getAssessmentsByStatus("Fail");
    }


    public void addAllSampleData() {
        mDataRepository.addAllSampleData();
    }

    public void clearAllData() {
        mDataRepository.clearAllData();
    }
}


