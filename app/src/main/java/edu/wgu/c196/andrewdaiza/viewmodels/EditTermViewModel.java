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
import edu.wgu.c196.andrewdaiza.database.entities.Course;
import edu.wgu.c196.andrewdaiza.database.entities.Term;
import edu.wgu.c196.andrewdaiza.utilities.CallBack;
import edu.wgu.c196.andrewdaiza.utilities.ValidateDelete;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateWithFormat;

public class EditTermViewModel extends AndroidViewModel {

    protected final DataRepository mDataRepository;
    protected final Executor executor = Executors.newSingleThreadExecutor();

    public final MutableLiveData<Term> mAllData = new MutableLiveData<>();

    private LiveData<List<Course>> mCourses;

    public EditTermViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
    }

    public void initializeTerm(int termId) {
        executor.execute(() -> {
            Term term = mDataRepository.getTermById(termId);
            mAllData.postValue(term);
        });
    }

    public void initializeTermCourses(int termId) {
        mCourses = mDataRepository.getCoursesByTerm(termId);
    }

    public void saveTerm(String title, String startDate, String eDate) {
        Term term = mAllData.getValue();
        if (term == null) {
            term = new Term(title, getDateWithFormat(startDate), getDateWithFormat(eDate));
        } else {
            term.setTerm_title(title.trim());
            term.setTerm_startDate(getDateWithFormat(startDate));
            term.setTerm_endDate(getDateWithFormat(eDate));
        }
        mDataRepository.insertTerm(term);
    }

    public void deleteTerm() {
        mDataRepository.deleteTerm(mAllData.getValue());
    }

    public LiveData<List<Course>> getTermCourses() {
        return mCourses;
    }

    public void deleteCourse(Course course) {
        mDataRepository.deleteCourse(course);
    }


    public void verifyBeforeTermDelete(Term term, CallBack onSuccess, CallBack onFailure) {
        ValidateDelete.verifyBeforeTermDelete(getApplication(), term, onSuccess, onFailure);
    }
}