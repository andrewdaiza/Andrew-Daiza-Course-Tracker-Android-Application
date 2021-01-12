package edu.wgu.c196.andrewdaiza.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.c196.andrewdaiza.database.DataRepository;
import edu.wgu.c196.andrewdaiza.database.entities.Term;

public class ListTermsViewModel extends AndroidViewModel {

    protected final DataRepository mDataRepository;

    private final LiveData<List<Term>> allTerms;


    public ListTermsViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
        allTerms = mDataRepository.getAllTerms();
    }

    public void deleteTerm(Term term) {
        mDataRepository.deleteTerm(term);
    }

    public void deleteAll() {
        mDataRepository.clearAllData();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

}

