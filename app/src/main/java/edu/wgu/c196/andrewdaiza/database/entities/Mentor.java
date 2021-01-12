package edu.wgu.c196.andrewdaiza.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentor_table")
public class Mentor implements findId {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mentor_id")
    private int id;
    @ColumnInfo(name = "course_id")
    private int course_Id;
    private String mentor_firstName;
    private String mentor_lastName;
    private String mentor_phoneNumber;
    private String mentor_email;

    public Mentor(int id, int course_Id, String mentor_firstName, String mentor_lastName, String mentor_phoneNumber, String mentor_email) {
        this.id = id;
        this.course_Id = course_Id;
        this.mentor_firstName = mentor_firstName;
        this.mentor_lastName = mentor_lastName;
        this.mentor_phoneNumber = mentor_phoneNumber;
        this.mentor_email = mentor_email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourse_Id(int course_Id) {
        this.course_Id = course_Id;
    }

    public void setMentor_firstName(String mentor_firstName) {
        this.mentor_firstName = mentor_firstName;
    }

    public void setMentor_lastName(String mentor_lastName) {
        this.mentor_lastName = mentor_lastName;
    }

    public void setMentor_phoneNumber(String mentor_phoneNumber) {
        this.mentor_phoneNumber = mentor_phoneNumber;
    }

    public void setMentor_email(String mentor_email) {
        this.mentor_email = mentor_email;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getCourse_Id() {
        return course_Id;
    }

    public String getMentor_firstName() {
        return mentor_firstName;
    }

    public String getMentor_lastName() {
        return mentor_lastName;
    }

    public String getMentor_phoneNumber() {
        return mentor_phoneNumber;
    }

    public String getMentor_email() {
        return mentor_email;
    }

    @Ignore
    public Mentor(int course_Id, String mentor_firstName, String mentor_lastName, String mentor_phoneNumber, String mentor_email) {
        this.id = id;
        this.course_Id = course_Id;
        this.mentor_firstName = mentor_firstName;
        this.mentor_lastName = mentor_lastName;
        this.mentor_phoneNumber = mentor_phoneNumber;
        this.mentor_email = mentor_email;
    }

}
