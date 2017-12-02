package com.ako.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

/**
 * Syllabus model representing Syllabus table
 * @author Tim
 */
@Entity
@Table(name="assignment")
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createDate;
    
    @Column(nullable = false)
    private String assignment;
    
    @Column(nullable = false)
    private Date dueDate;
    
    @Column(nullable = false)
    private boolean complete;
    
    
    /************************************
    *   CONSTRUCTORS
    ************************************/    
    public Syllabus(String assignment, Date dueDate) {
        this.assignment = assignment;
        this.dueDate = dueDate;
        this.complete = false;
    }
    
    public Syllabus(String assignment) {
        this.assignment = assignment;
        this.dueDate = new Date();
        this.complete = false;
    }

    public Syllabus() {
        this.assignment = "Not Specified.";
        this.dueDate = new Date();
        this.complete = false;
    }
    
    /************************************
    *   GETTERS & SETTERS
    ************************************/
    public int getId() {
        return id;
    }
    
    @JsonIgnore
    public Date getCreateDate() {
            return createDate;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    @JsonIgnore
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @JsonIgnore
    public boolean isComplete() {
        return this.complete;
    }

    @JsonIgnore
    public void setComplete(boolean completed) {
        this.complete = completed;
    }

}
