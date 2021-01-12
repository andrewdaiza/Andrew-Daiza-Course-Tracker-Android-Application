package edu.wgu.c196.andrewdaiza.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note implements findId {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int id;
    @ColumnInfo(name = "course_id")
    private int course_Id;
    private String note_title;
    private String note_description;

    public Note(int id, int course_Id, String note_title, String note_description) {
        this.id = id;
        this.course_Id = course_Id;
        this.note_title = note_title;
        this.note_description = note_description;
    }
    @Ignore
    public Note(int course_Id, String note_title, String note_description) {
        this.course_Id = course_Id;
        this.note_title = note_title;
        this.note_description = note_description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourse_Id(int course_Id) {
        this.course_Id = course_Id;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public void setNote_description(String note_description) {
        this.note_description = note_description;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getCourse_Id() {
        return course_Id;
    }

    public String getNote_title() {
        return note_title;
    }

    public String getNote_description() {
        return note_description;
    }
}
