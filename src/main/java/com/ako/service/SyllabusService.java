package com.ako.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.joda.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ako.data.ISyllabusRepository;
import com.ako.data.ISyllabus;
import com.ako.data.Syllabus;

/**
 * Syllabus Service
 *
 * @author Tim
 */
@Service
public class SyllabusService implements ISyllabus {

    @Autowired
    private ISyllabusRepository repository;
    
    /* Logger */
    private final Logger logger = LogManager.getLogger(SyllabusService.class);

    /**
     * Fetch all assignments
     *
     * @return list of all assignments
     */
    @Override
    public List<Syllabus> getAllAssignments() {
        logger.info("getAllAssignmets() called");
        List<Syllabus> assignments = new ArrayList<>();
        this.repository.findAll().forEach(assignments::add);
        return assignments;
    }

    /**
     * Fetch assignment by id
     *
     * @param int id
     * @return assignment identified by given id
     */
    @Override
    public Syllabus getAssignment(int id) {
        logger.info("getAssignment() called");
        return this.repository.findOne(id);
    }

    /**
     * Add an assignment
     *
     * @param Syllabus assignment
     * @return the added assignment
     */
    @Override
    public Syllabus addAssignment(Syllabus assignment) {
        logger.info("addAssignmet() called");
        if (this.repository.exists(assignment.getId())) {
            return this.updateSyllabus(assignment);
        }
        return this.repository.save(assignment);
    }

    /**
     * Update an assignment
     *
     * @param Syllabus assignment
     * @return the updated assignment
     */
    @Override
    public Syllabus updateSyllabus(Syllabus assignment) {
        logger.info("updateSyllabus() called");
        return this.repository.save(assignment);
    }

    /**
     * Find assignment by assignment name
     *
     * @param String assignment name
     * @return the assignment
     */
    @Override
    public Syllabus findByAssignment(String assignment) {
        logger.info("findByAssignment() called");
        return this.repository.findByAssignment(assignment);
    }

    /**
     * Find assignment by Due Date
     *
     * @param LocalDate dueDate
     * @return the assignment
     */
    @Override
    public Syllabus findByDueDate(LocalDate dueDate) {
        logger.info("findByDueDate() called");
        return this.repository.findByDueDate(dueDate);
    }

    /**
     * Find assignments by completed status
     *
     * @param boolean completed
     * @return the list of assignments
     */
    @Override
    public List<Syllabus> findByCompleted(boolean completed) {
        logger.info("findByCompleted() called");
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
     *
     * @param None
     * @return 0 if successful
     */
    @Override
    public int deleteAll() {
        logger.info("deleteAll() called");
        this.repository.deleteAll();
        return 0;
    }
    
    /**
     * Delete an assignments
     *
     * @param Syllabus assignment
     * @return the deleted assignment
     */
    @Override
    public Syllabus deleteOne(Syllabus assignment) {
        logger.info("delete() called");
        this.repository.delete(assignment.getId());
        return assignment;
    }
}
