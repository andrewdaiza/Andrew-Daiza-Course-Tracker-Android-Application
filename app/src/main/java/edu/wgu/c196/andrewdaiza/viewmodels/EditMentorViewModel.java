package edu.wgu.c196.andrewdaiza.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.c196.andrewdaiza.database.DataRepository;
import edu.wgu.c196.andrewdaiza.database.entities.Mentor;

public class EditMentorViewModel extends AndroidViewModel {

    protected final DataRepository mDataRepository;
    protected final Executor executor = Executors.newSingleThreadExecutor();

    public final MutableLiveData<Mentor> mLiveMentor = new MutableLiveData<>();

    public EditMentorViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
    }

    public void initializeMentor(int mentorId) {
        executor.execute(() -> {
            Mentor mentor = mDataRepository.getMentorById(mentorId);
            mLiveMentor.postValue(mentor);
        });
    }

    public void saveMentor(int courseId, String firstName, String lastName, String phone, String email) {
        Mentor mentor = mLiveMentor.getValue();
        if (mentor == null) {
            mentor = new Mentor(courseId, firstName, lastName, phone, email);
        } else {
            mentor.setCourse_Id(courseId);
            mentor.setMentor_firstName(firstName);
            mentor.setMentor_lastName(lastName);
            mentor.setMentor_phoneNumber(phone);
            mentor.setMentor_email(email);
        }
        mDataRepository.insertMentor(mentor);
    }

    public void deleteMentor() {
        mDataRepository.deleteMentor(mLiveMentor.getValue());
    }


}
