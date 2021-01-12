package edu.wgu.c196.andrewdaiza.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses_table")
public class Course implements findId {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int id;
    @ColumnInfo(name = "term_id")
    private int term_Id;

    @Override
    public int getId() {
        return id;
    }

    public int getTerm_Id() {
        return term_Id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTerm_Id(int termId) {
        this.term_Id = termId;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public void setCourse_startDate(Date course_startDate) {
        this.course_startDate = course_startDate;
    }

    public void setCourse_endDate(Date course_endDate) {
        this.course_endDate = course_endDate;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }

    public Date getCourse_startDate() {
        return course_startDate;
    }

    public Date getCourse_endDate() {
        return course_endDate;
    }

    public String getCourse_status() {
        return course_status;
    }

    private String course_title;
    private Date course_startDate;
    private Date course_endDate;
    private String course_status;

    public Course(int id, int term_Id, String course_title, Date course_startDate, Date course_endDate, String course_status) {
        this.id = id;
        this.term_Id = term_Id;
        this.course_title = course_title;
        this.course_startDate = course_startDate;
        this.course_endDate = course_endDate;
        this.course_status = course_status;
    }

    @Ignore
    public Course(int term_Id, String course_title, Date course_startDate, Date course_endDate, String course_status) {
        this.term_Id = term_Id;
        this.course_title = course_title;
        this.course_startDate = course_startDate;
        this.course_endDate = course_endDate;
        this.course_status = course_status;
    }

}
