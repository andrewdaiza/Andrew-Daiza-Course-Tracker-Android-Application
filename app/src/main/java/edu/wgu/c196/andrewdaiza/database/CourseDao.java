package edu.wgu.c196.andrewdaiza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.c196.andrewdaiza.database.entities.Course;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Course> courses);

    @Delete
    void delete(Course course);

    @Query("delete from courses_table")
    void deleteAll();

    @Query("select * from courses_table where term_id = :termId")
    LiveData<List<Course>> getAllCoursesForTerm(int termId);

    @Query("select * from courses_table where course_id = :courseId")
    Course getCourseById(int courseId);

    @Query("select count(*) from courses_table where course_status = :status")
    LiveData<Integer> getCoursesByStatus(String status);

}
