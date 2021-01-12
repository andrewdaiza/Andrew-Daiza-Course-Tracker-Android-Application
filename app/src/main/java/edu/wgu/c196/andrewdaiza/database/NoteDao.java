package edu.wgu.c196.andrewdaiza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.c196.andrewdaiza.database.entities.Note;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Note> notes);

    @Delete
    void delete(Note note);

    @Query("delete from note_table")
    void deleteAll();

    @Query("delete from note_table where course_id = :courseId")
    void deleteNotesForCourse(int courseId);

    @Query("select * from note_table where note_id = :noteId")
    Note getNoteById(int noteId);

    @Query("select * from note_table where course_id = :courseId")
    LiveData<List<Note>> getCourseNotes(int courseId);

}
