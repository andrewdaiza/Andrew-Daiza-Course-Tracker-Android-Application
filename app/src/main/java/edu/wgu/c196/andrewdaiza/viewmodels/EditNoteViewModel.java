package edu.wgu.c196.andrewdaiza.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.c196.andrewdaiza.database.DataRepository;
import edu.wgu.c196.andrewdaiza.database.entities.Note;

public class EditNoteViewModel extends AndroidViewModel {

    protected final DataRepository mDataRepository;
    protected final Executor executor = Executors.newSingleThreadExecutor();

    public final MutableLiveData<Note> mAllNotes = new MutableLiveData<>();

    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
    }

    public void initializeNotes(int noteId) {
        executor.execute(() -> {
            Note note = mDataRepository.getNoteById(noteId);
            mAllNotes.postValue(note);
        });
    }

    public void saveNote(int courseId, String title, String description) {
        Note note = mAllNotes.getValue();
        if (note == null) {
            note = new Note(courseId, title, description);
        } else {
            note.setCourse_Id(courseId);
            note.setNote_title(title);
            note.setNote_description(description);
        }
        mDataRepository.insertNote(note);
    }

    public void deleteNote() {
        mDataRepository.deleteNote(mAllNotes.getValue());
    }
}
