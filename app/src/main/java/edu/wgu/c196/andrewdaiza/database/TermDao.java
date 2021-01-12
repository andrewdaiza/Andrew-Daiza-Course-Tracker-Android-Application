package edu.wgu.c196.andrewdaiza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.wgu.c196.andrewdaiza.database.entities.Term;
import edu.wgu.c196.andrewdaiza.database.entities.TermAndCourses;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Term term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Term> term);

    @Delete
    void delete(Term term);

    @Query("delete from term_table")
    void deleteAll();

    @Query("select * from term_table")
    LiveData<List<Term>> getAllTerms();

    @Query("select * from term_table where term_id = :termId")
    Term getTermById(int termId);

    @Transaction
    @Query("select * from term_table where term_id = :termId")
    TermAndCourses getTermWithCourses(int termId);

}

