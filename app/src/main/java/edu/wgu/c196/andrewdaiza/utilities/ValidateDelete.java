package edu.wgu.c196.andrewdaiza.utilities;

import android.content.Context;
import android.os.AsyncTask;

import edu.wgu.c196.andrewdaiza.database.DataRepository;
import edu.wgu.c196.andrewdaiza.database.entities.Term;
import edu.wgu.c196.andrewdaiza.database.entities.TermAndCourses;

public class ValidateDelete {

    public static void verifyBeforeTermDelete(Context context, Term term, CallBack onSuccess, CallBack onFailure) {
        DataRepository mDataRepository = DataRepository.getInstance(context);
        AsyncTask<Void, Void, Boolean> taskAsync = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if (term == null) {
                    return true;
                }
                TermAndCourses termAndCourses = mDataRepository.getTermWithCourses(term.getId());
                return !(termAndCourses != null &&
                        termAndCourses.courses != null &&
                        !termAndCourses.courses.isEmpty());
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    onSuccess.callback();
                } else {
                    onFailure.callback();
                }
            }
        };
        taskAsync.execute();
    }
}
