package edu.wgu.c196.andrewdaiza.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.c196.andrewdaiza.database.entities.Assessment;
import edu.wgu.c196.andrewdaiza.database.entities.Course;
import edu.wgu.c196.andrewdaiza.database.entities.Mentor;
import edu.wgu.c196.andrewdaiza.database.entities.Note;
import edu.wgu.c196.andrewdaiza.database.entities.Term;
import edu.wgu.c196.andrewdaiza.utilities.Converters;

@Database(entities = {Term.class, Course.class, Assessment.class,
        Note.class, Mentor.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract NoteDao noteDao();
    public abstract MentorDao mentorDao();


    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "studentDatabase.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
