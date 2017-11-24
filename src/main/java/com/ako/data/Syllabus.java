package com.ako.data;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.LocalDate;

/**
 * Syllabus model representing Syllabus table
 * @author Tim
 *
 */
@Entity
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String assignment;
    private LocalDate dueDate;
    private boolean completed;
    
    
    /************************************
    *   CONSTRUCTORS
    ************************************/    
    public Syllabus(String assignment, LocalDate dueDate) {
        this.assignment = assignment;
        this.dueDate = dueDate;
        this.completed = false;
    }
    
    public Syllabus(String assignment) {
        this.assignment = assignment;
        this.dueDate = new LocalDate();
        this.completed = false;
    }

    public Syllabus() {
        this.assignment = "Not Specified.";
        this.dueDate = new LocalDate();
        this.completed = false;
    }
    
    /************************************
    *   GETTERS & SETTERS
    ************************************/
    public int getId() {
        return id;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isComplete() {
        return this.completed;
    }

    public void setComplete(boolean completed) {
        this.completed = completed;
    }

}
