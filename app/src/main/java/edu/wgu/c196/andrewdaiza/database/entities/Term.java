package edu.wgu.c196.andrewdaiza.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term_table")
public class Term implements findId {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "term_id")
    private int id;
    private String term_title;
    private Date term_startDate;
    private Date term_endDate;

    public Term(int id, String term_title, Date term_startDate, Date term_endDate) {
        this.id = id;
        this.term_title = term_title;
        this.term_startDate = term_startDate;
        this.term_endDate = term_endDate;
    }
    @Ignore
    public Term(String term_title, Date term_startDate, Date term_endDate) {
        this.term_title = term_title;
        this.term_startDate = term_startDate;
        this.term_endDate = term_endDate;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getTerm_title() {
        return term_title;
    }

    public Date getTerm_startDate() {
        return term_startDate;
    }

    public Date getTerm_endDate() {
        return term_endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTerm_title(String term_title) {
        this.term_title = term_title;
    }

    public void setTerm_startDate(Date term_startDate) {
        this.term_startDate = term_startDate;
    }

    public void setTerm_endDate(Date term_endDate) {
        this.term_endDate = term_endDate;
    }

}
