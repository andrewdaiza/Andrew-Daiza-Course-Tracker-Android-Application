package edu.wgu.c196.andrewdaiza.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.c196.andrewdaiza.database.DataRepository;
import edu.wgu.c196.andrewdaiza.database.entities.Assessment;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateWithFormat;

public class AddEditAssessmentViewModel extends AndroidViewModel {

    protected final DataRepository mDataRepository;
    protected final Executor executor = Executors.newSingleThreadExecutor();

    public final MutableLiveData<Assessment> mAllAssessments = new MutableLiveData<>();

    public AddEditAssessmentViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
    }

    public void initializeAssessment(int assessmentId) {
        executor.execute(() -> {
            Assessment assessment = mDataRepository.getAssessmentById(assessmentId);
            mAllAssessments.postValue(assessment);
        });
    }

    public void saveAssessment(Integer courseId, String assessmentType, String title, String status, String completionDate) {
        Assessment assessment = mAllAssessments.getValue();
        if (assessment == null) {
            assessment = new Assessment(courseId, assessmentType, title, status, getDateWithFormat(completionDate));
        } else {
            assessment.setCourse_Id(courseId);
            assessment.setAssessment_type(assessmentType);
            assessment.setAssessment_title(title);
            assessment.setAssessment_status(status);
            assessment.setAssessment_completionDate(getDateWithFormat(completionDate));
        }
        mDataRepository.insertAssessment(assessment);
    }

    public void deleteAssessment() {
        mDataRepository.deleteAssessment(mAllAssessments.getValue());
    }
}
