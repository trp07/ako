package com.ako.data;

import java.util.List;

import com.ako.data.Syllabus;
import org.joda.time.LocalDate;

/**
 * Interface for the SyllabusService
 * @author Tim
 */
public interface ISyllabus {
    public List<Syllabus> getAllAssignments();
    public Syllabus getAssignment(int id);
    public Syllabus addAssignment(String assignment);
    public Syllabus updateSyllabus(int id, String assignment);
    public Syllabus findByAssignment(String assignment);
    public Syllabus findByDueDate(LocalDate dueDate);
    public List<Syllabus> findByCompleted(boolean completed);
    public int deleteAll();
    public Syllabus deleteOneByName(String assignment);
    public int deleteOneById(int id);
}
