package edu.wgu.c196.andrewdaiza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.c196.andrewdaiza.database.entities.Assessment;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessment assessment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Assessment> assessments);

    @Delete
    void delete(Assessment assessment);

    @Query("delete from assessments_table")
    void deleteAll();

    @Query("select * from assessments_table where course_id = :courseId")
    LiveData<List<Assessment>> getAllAssessmentsForCourse(int courseId);

    @Query("select * from assessments_table where assessment_id = :assessmentId")
    Assessment getAssessmentById(int assessmentId);

    @Query("select count(*) from assessments_table where assessment_status = :status")
    LiveData<Integer> getAssessmentsByStatus(String status);

    @Query("delete from assessments_table where course_id = :courseId")
    void deleteAssessmentsForCourse(int courseId);

}
