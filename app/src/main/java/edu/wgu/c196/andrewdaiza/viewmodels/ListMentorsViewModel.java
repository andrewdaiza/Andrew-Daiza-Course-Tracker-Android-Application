package edu.wgu.c196.andrewdaiza.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.c196.andrewdaiza.database.DataRepository;
import edu.wgu.c196.andrewdaiza.database.entities.Mentor;

public class ListMentorsViewModel extends AndroidViewModel {

    protected final DataRepository mDataRepository;

    private LiveData<List<Mentor>> allMentors;

    public ListMentorsViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
    }


    public void initializeCourseMentors(int courseId) {
        allMentors = mDataRepository.getMentorsForCourse(courseId);
    }


    public void deleteMentor(Mentor mentor) {
        mDataRepository.deleteMentor(mentor);
    }

    public LiveData<List<Mentor>> getCourseMentors() {
        return allMentors;
    }
}
