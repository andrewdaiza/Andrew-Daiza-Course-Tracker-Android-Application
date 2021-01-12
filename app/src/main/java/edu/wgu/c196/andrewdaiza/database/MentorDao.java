package edu.wgu.c196.andrewdaiza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.c196.andrewdaiza.database.entities.Mentor;

@Dao
public interface MentorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Mentor mentor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Mentor> mentors);

    @Delete
    void delete(Mentor mentor);

    @Query("delete from mentor_table")
    void deleteAll();

    @Query("select * from mentor_table where course_id = :courseId")
    LiveData<List<Mentor>> getAllMentorsForCourse(int courseId);

    @Query("select * from mentor_table where mentor_id = :mentorId")
    Mentor getMentorById(int mentorId);

    @Query("delete from mentor_table where course_id = :courseId")
    void deleteMentorsForCourse(int courseId);
}
