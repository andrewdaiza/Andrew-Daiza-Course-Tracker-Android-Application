package edu.wgu.c196.andrewdaiza.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.c196.andrewdaiza.database.DataRepository;
import edu.wgu.c196.andrewdaiza.database.entities.Note;

public class ListNotesViewModel extends AndroidViewModel {

    protected final DataRepository mDataRepository;
    protected final Executor executor = Executors.newSingleThreadExecutor();

    private LiveData<List<Note>> allNotes;

    public ListNotesViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
    }


    public void initializeCoursesNotes(int courseId) {
        allNotes = mDataRepository.getCourseNotes(courseId);
    }

    public LiveData<List<Note>> getNotesCourse() {
        return allNotes;
    }

    public void deleteNote(Note note) {
        mDataRepository.deleteNote(note);
    }

}
