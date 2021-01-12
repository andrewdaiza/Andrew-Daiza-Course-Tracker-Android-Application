package edu.wgu.c196.andrewdaiza.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments_table")
public class Assessment implements findId {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private int id;
    @ColumnInfo(name = "course_id")
    private int course_Id;
    private String assessment_type;
    private String assessment_title;
    private String assessment_status;
    private Date assessment_completionDate;

    public Assessment(int id, int course_Id, String assessment_type, String assessment_title, String assessment_status, Date assessment_completionDate) {
        this.id = id;
        this.course_Id = course_Id;
        this.assessment_type = assessment_type;
        this.assessment_title = assessment_title;
        this.assessment_status = assessment_status;
        this.assessment_completionDate = assessment_completionDate;
    }

    @Ignore
    public Assessment(int course_Id, String assessment_type, String assessment_title, String assessment_status, Date assessment_completionDate) {
        this.course_Id = course_Id;
        this.assessment_type = assessment_type;
        this.assessment_title = assessment_title;
        this.assessment_status = assessment_status;
        this.assessment_completionDate = assessment_completionDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourse_Id(int course_Id) {
        this.course_Id = course_Id;
    }

    public void setAssessment_type(String assessment_type) {
        this.assessment_type = assessment_type;
    }

    public void setAssessment_title(String assessment_title) {
        this.assessment_title = assessment_title;
    }

    public void setAssessment_status(String assessment_status) {
        this.assessment_status = assessment_status;
    }

    public void setAssessment_completionDate(Date assessment_completionDate) {
        this.assessment_completionDate = assessment_completionDate;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getCourse_Id() {
        return course_Id;
    }

    public String getAssessment_type() {
        return assessment_type;
    }

    public String getAssessment_title() {
        return assessment_title;
    }

    public String getAssessment_status() {
        return assessment_status;
    }

    public Date getAssessment_completionDate() {
        return assessment_completionDate;
    }

}
