package edu.wgu.c196.andrewdaiza.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.c196.andrewdaiza.database.DataRepository;
import edu.wgu.c196.andrewdaiza.database.entities.Assessment;
import edu.wgu.c196.andrewdaiza.database.entities.Course;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateWithFormat;

public class EditCourseViewModel extends AndroidViewModel {

    protected final DataRepository mDataRepository;
    protected final Executor executor = Executors.newSingleThreadExecutor();

    public final MutableLiveData<Course> mAllCourses = new MutableLiveData<>();

    private LiveData<List<Assessment>> mAssessments;

    public EditCourseViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
    }

    public void initializeCourse(int courseId) {
        executor.execute(() -> {
            Course course = mDataRepository.getCourseById(courseId);
            mAllCourses.postValue(course);
        });
    }

    public void initializeAssessmentsCourse(int courseId) {
        mAssessments = mDataRepository.getAssessmentsForCourse(courseId);
    }

    public void saveCourse(String title, String termId, String status, String startDate, String endDate) {
        Course course = mAllCourses.getValue();
        if (course == null) {
            course = new Course(Integer.parseInt(termId), title, getDateWithFormat(startDate), getDateWithFormat(endDate), status);
        } else {
            course.setTerm_Id(Integer.parseInt(termId));
            course.setCourse_title(title);
            course.setCourse_status(status);
            course.setCourse_startDate(getDateWithFormat(startDate));
            course.setCourse_endDate(getDateWithFormat(endDate));
        }
        mDataRepository.insertCourse(course);
    }

    public void deleteCourse() {
        mDataRepository.deleteCourse(mAllCourses.getValue());
    }

    public LiveData<List<Assessment>> getAssessmentsCourse() {
        return mAssessments;
    }

}
