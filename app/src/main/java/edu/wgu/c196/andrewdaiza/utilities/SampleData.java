package edu.wgu.c196.andrewdaiza.utilities;

import java.util.ArrayList;
import java.util.List;

import edu.wgu.c196.andrewdaiza.database.entities.Assessment;
import edu.wgu.c196.andrewdaiza.database.entities.Course;
import edu.wgu.c196.andrewdaiza.database.entities.Mentor;
import edu.wgu.c196.andrewdaiza.database.entities.Note;
import edu.wgu.c196.andrewdaiza.database.entities.Term;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateWithFormat;


public class SampleData {

    public static List<Course> getSampleCourses() {
        List<Course> dataArray = new ArrayList<>();
        dataArray.add(new Course(1, 1,
                "Course Sample 1",
                getDateWithFormat("February 28, 2020"),
                getDateWithFormat("November 30, 2020"),"Plan to Take"));
        dataArray.add(new Course(2, 1,
                "Course Sample 2",
                getDateWithFormat("October 19, 2020"),
                getDateWithFormat("November 10, 2020"), "Complete"));
        dataArray.add(new Course(3, 2,
                "Course Sample 3",
                getDateWithFormat("March 6, 2020"),
                getDateWithFormat("May 12, 2020"),"In Progress"));
        dataArray.add(new Course(4, 2,
                "Course Sample 4",
                getDateWithFormat("January 7, 2020"),
                getDateWithFormat("March 14, 2020"),"Dropped"));
        dataArray.add(new Course(5, 3,
                "Course Sample 5",
                getDateWithFormat("April 10, 2020"),
                getDateWithFormat("May 20, 2020"),"Complete"));
        dataArray.add(new Course(6, 3,
                "Course Sample 6",
                getDateWithFormat("August 9, 2020"),
                getDateWithFormat("September 19, 2020"),"Failed"));
        dataArray.add(new Course(7, 4,
                "Course Sample 7",
                getDateWithFormat("November 7, 2021"),
                getDateWithFormat("December 17, 2021"), "Plan to Take"));
        dataArray.add(new Course(8, 4,
                "Course Sample 8",
                getDateWithFormat("February 10, 2021"),
                getDateWithFormat("March 20, 2021"),"In Progress"));
        return dataArray;
    }

    public static List<Term> getSampleTerms() {
        List<Term> dataArray = new ArrayList<>();
        dataArray.add(new Term(1, "Term Sample 1",
                getDateWithFormat("February 1, 2020"), getDateWithFormat("July 31, 2021")));
        dataArray.add(new Term(2, "Term Sample 2",
                getDateWithFormat("April 1, 2020"), getDateWithFormat("September 30, 2020")));
        dataArray.add(new Term(3, "Term Sample 3",
                getDateWithFormat("November 1, 2020"), getDateWithFormat("April 31, 2020")));
        dataArray.add(new Term(4, "Term Sample 4",
                getDateWithFormat("August 1, 2020"), getDateWithFormat("January 31, 2020")));
        dataArray.add(new Term(5,"Deletable Term (No Courses)",
                getDateWithFormat("January 1, 2021"), getDateWithFormat("June 31, 2021")));
        dataArray.add(new Term(6,"Deletable Term 2 (No Courses)",
                getDateWithFormat("May 1, 2021"), getDateWithFormat("October 31, 2021")));
        return dataArray;
    }

    public static List<Assessment> getSampleAssessments() {
        List<Assessment> dataArray = new ArrayList<>();
        dataArray.add(new Assessment(1, 1,
                "Performance", "Assessment Sample 1",
                "Pass", getDateWithFormat("November 30, 2020")));
        dataArray.add(new Assessment(2, 1,
                "Performance", "Assessment Sample 2",
                "Fail", getDateWithFormat("November 10, 2020")));
        dataArray.add(new Assessment(3, 2,
                "Objective", "Assessment Sample 3",
                "Pending", getDateWithFormat("May 12, 2020")));
        dataArray.add(new Assessment(4, 3,
                "Objective", "Assessment Sample 4",
                "Pass", getDateWithFormat("May 20, 2020")));
        dataArray.add(new Assessment(5, 4,
                "Performance", "Assessment Sample 5",
                "Fail", getDateWithFormat("September 19, 2020")));
        dataArray.add(new Assessment(6, 5,
                "Objective", "Assessment Sample 6",
                "Pending", getDateWithFormat("May 20, 2020")));
        return dataArray;
    }

    public static List<Mentor> getSampleMentors() {
        List<Mentor> dataArray = new ArrayList<>();
        dataArray.add(new Mentor(1, "Example Mentor", "",
                "(555) 555-5555", "exampleemail@email.com"));
        dataArray.add(new Mentor(2, "Example Mentor", "",
                "(555) 555-5555", "exampleemail@email.com"));
        dataArray.add(new Mentor(3, "Example Mentor", "",
                "(555) 555-5555", "exampleemail@email.com"));
        dataArray.add(new Mentor(4, "Example Mentor", "",
                "(555) 555-5555", "exampleemail@email.com"));
        dataArray.add(new Mentor(5, "Example Mentor", "",
                "(555) 555-5555", "exampleemail@email.com"));
        dataArray.add(new Mentor(6, "Example Mentor", "",
                "(555) 555-5555", "exampleemail@email.com"));
        return dataArray;
    }

    public static List<Note> getSampleCourseNotes() {
        List<Note> dataArray = new ArrayList<>();
        dataArray.add(new Note(1, 1, "Note title",
                "Body of Note"));
        dataArray.add(new Note(2, 2, "Note title",
                "Body of Note"));
        dataArray.add(new Note(3, 3, "Note title",
                "Body of Note"));
        dataArray.add(new Note(5, 4, "Note title",
                "Body of Note"));
        dataArray.add(new Note(5, 5, "Note title",
                "Body of Note"));
        return dataArray;
    }


}
