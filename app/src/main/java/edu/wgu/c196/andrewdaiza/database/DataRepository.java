package edu.wgu.c196.andrewdaiza.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.c196.andrewdaiza.database.entities.Assessment;
import edu.wgu.c196.andrewdaiza.database.entities.Course;
import edu.wgu.c196.andrewdaiza.database.entities.Mentor;
import edu.wgu.c196.andrewdaiza.database.entities.Note;
import edu.wgu.c196.andrewdaiza.database.entities.Term;
import edu.wgu.c196.andrewdaiza.database.entities.TermAndCourses;
import edu.wgu.c196.andrewdaiza.utilities.SampleData;

public class DataRepository {


    private final TermDao termDao;
    private final CourseDao courseDao;
    private final AssessmentDao assessmentDao;
    private final NoteDao noteDao;
    private final MentorDao mentorDao;
    private final AppDatabase db;
    private static DataRepository instance;
    private final LiveData<List<Term>> allTerms;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private DataRepository(Context context) {
        db = AppDatabase.getInstance(context);
        termDao = db.termDao();
        courseDao = db.courseDao();
        assessmentDao = db.assessmentDao();
        noteDao = db.noteDao();
        mentorDao = db.mentorDao();
        allTerms = termDao.getAllTerms();
    }

    public static DataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DataRepository(context);
        }
        return instance;
    }

    public void insertTerm(Term term) {
        executor.execute(() -> termDao.insert(term));
    }

    public void deleteTerm(Term term) {
        executor.execute(() -> termDao.delete(term));
    }

    public void insertCourse(Course course) {
        executor.execute(() -> courseDao.insert(course));
    }

    public void deleteCourse(Course course) {
        executor.execute(() -> {
            courseDao.delete(course);
            noteDao.deleteNotesForCourse(course.getId());
            assessmentDao.deleteAssessmentsForCourse(course.getId());
            mentorDao.deleteMentorsForCourse(course.getId());
        });
    }

    public void insertNote(Note note) {
        executor.execute(() -> noteDao.insert(note));
    }

    public void deleteNote(Note note) {
        executor.execute(() -> noteDao.delete(note));
    }

    public void insertAssessment(Assessment assessment) {
        executor.execute(() -> assessmentDao.insert(assessment));
    }

    public void deleteAssessment(Assessment assessment) {
        executor.execute(() -> assessmentDao.delete(assessment));
    }

    public void insertMentor(Mentor mentor) {
        executor.execute(() -> mentorDao.insert(mentor));
    }

    public void deleteMentor(Mentor mentor) {
        executor.execute(() -> mentorDao.delete(mentor));
    }



    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public Term getTermById(int termId) {
        return db.termDao().getTermById(termId);
    }

    public LiveData<Integer> getCoursesByStatus(String status) {
        return courseDao.getCoursesByStatus(status);
    }

    public LiveData<List<Course>> getCoursesByTerm(int termId) {
        return courseDao.getAllCoursesForTerm(termId);
    }

    public LiveData<List<Note>> getCourseNotes(int courseId) {
        return noteDao.getCourseNotes(courseId);
    }

    public Note getNoteById(int noteId) {
        return noteDao.getNoteById(noteId);
    }

    public LiveData<Integer> getAssessmentsByStatus(String status) {
        return assessmentDao.getAssessmentsByStatus(status);
    }


    public LiveData<List<Assessment>> getAssessmentsForCourse(int courseId) {
        return assessmentDao.getAllAssessmentsForCourse(courseId);
    }

    public void addAllSampleData() {
        executor.execute(() -> {
            termDao.insertAll(SampleData.getSampleTerms());
            courseDao.insertAll(SampleData.getSampleCourses());
            assessmentDao.insertAll(SampleData.getSampleAssessments());
            noteDao.insertAll(SampleData.getSampleCourseNotes());
            mentorDao.insertAll(SampleData.getSampleMentors());
        });
    }

    public void clearAllData() {
        executor.execute(() -> {
            mentorDao.deleteAll();
            noteDao.deleteAll();
            assessmentDao.deleteAll();
            courseDao.deleteAll();
            termDao.deleteAll();
        });
    }

    public TermAndCourses getTermWithCourses(int termId) {
        return termDao.getTermWithCourses(termId);
    }

    public Course getCourseById(int courseId) {
        return courseDao.getCourseById(courseId);
    }

    public Assessment getAssessmentById(int assessmentId) {
        return assessmentDao.getAssessmentById(assessmentId);
    }

    public LiveData<List<Mentor>> getMentorsForCourse(int courseId) {
        return mentorDao.getAllMentorsForCourse(courseId);
    }


    public Mentor getMentorById(int mentorId) {
        return mentorDao.getMentorById(mentorId);
    }


}

