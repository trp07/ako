package com.ako.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ako.data.ISyllabusRepository;
import com.ako.data.ISyllabus;
import com.ako.data.Syllabus;

/**
 * Syllabus Service
 * @author Tim
 */
@Service
public class SyllabusService implements ISyllabus {

    @Autowired
    private ISyllabusRepository repository;
    
    /* private attributes */
    private final Logger logger = LogManager.getLogger(SyllabusService.class);

    /**
     * Fetch all assignments
     * @return list of all assignments
     */
    @Override
    public List<Syllabus> getAllAssignments() {
        logger.info("====SyllabusService==== getAllAssignmets() called");
        List<Syllabus> assignments = new ArrayList<>();
        this.repository.findAll().forEach(assignments::add);
        return assignments;
    }

    /**
     * Fetch assignment by id
     * @param int id
     * @return assignment identified by given id
     */
    @Override
    public Syllabus getAssignment(int id) {
        logger.info("====SyllabusService==== getAssignment() called");
        return this.repository.findOne(id);
    }

    /**
     * Add an assignment
     * @param String assignment
     * @param String dueDate
     * @return the added assignment
     */
    @Override
    public Syllabus addAssignment(String assignment, String dueDate) {
        logger.info("====SyllabusService==== addAssignmet() called");
        Date dd = new Date(Long.parseLong(dueDate));
        Syllabus a = new Syllabus(assignment, dd);
        return this.repository.save(a);
    }

    /**
     * Update an assignment
     * @param int id
     * @param String assignment
     * @return the updated assignment
     */
    @Override
    public Syllabus updateSyllabus(int id, String assignment) {
        logger.info("====SyllabusService==== updateSyllabus() called");
        Syllabus a = this.repository.findById(id);
        a.setAssignment(assignment);
        return this.repository.save(a);
    }

    /**
     * Find assignment by assignment name
     * @param String assignment name
     * @return the assignment
     */
    @Override
    public Syllabus findByName(String assignment) {
        logger.info("====SyllabusService==== findByAssignment() called");
        return this.repository.findByName(assignment);
    }

    /**
     * Find assignment by Due Date
     * @param Date dueDate
     * @return the assignment
     */
    @Override
    public Syllabus findByDueDate(Date dueDate) {
        logger.info("====SyllabusService==== findByDueDate() called");
        return this.repository.findByDueDate(dueDate);
    }

    /**
     * Find assignments by completed status
     * @param boolean completed
     * @return the list of assignments
     */
    @Override
    public List<Syllabus> findByCompleted(boolean completed) {
        logger.info("====SyllabusService==== findByCompleted() called");
        List<Syllabus> assignments = new ArrayList<>();
        List<Syllabus> comp = new ArrayList<>();

        assignments = this.getAllAssignments();

        for (Syllabus assignment : assignments) {
            if (assignment.isComplete() == completed) {
                comp.add(assignment);
            }
        }
        return comp;
    }

    /**
     * Delete all assignments
     * @param None
     * @return 0 if successful
     */
    @Override
    public int deleteAll() {
        logger.info("====SyllabusService==== deleteAll() called");
        this.repository.deleteAll();
        return 0;
    }
    
    /**
     * Delete an assignment
     * @param String assignment
     * @return the deleted assignment
     */
    @Override
    public Syllabus deleteOneByName(String assignment) {
        logger.info("====SyllabusService==== deleteOne() called");
        Syllabus a = this.repository.findByName(assignment);
        this.repository.delete(a.getId());
        return a;
    }
    
    /**
     * Delete an assignments
     * @param int id
     * @return the id of the deleted assignment
     */
    @Override
    public int deleteOneById(int id) {
        logger.info("====SyllabusService==== deleteOne() called");
        this.repository.delete(id);
        return id;
    }
}
