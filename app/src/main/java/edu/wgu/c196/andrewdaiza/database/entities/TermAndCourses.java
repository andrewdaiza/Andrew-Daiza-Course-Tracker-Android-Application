package edu.wgu.c196.andrewdaiza.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;


public class TermAndCourses {
    @Embedded
    public Term term;
    @Relation(parentColumn = "term_id",
            entityColumn = "term_id")
    public List<Course> courses;
}
