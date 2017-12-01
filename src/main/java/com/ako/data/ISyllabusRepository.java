package com.ako.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.joda.time.LocalDate;

/**
 * A syllabus data service
 * CrudRepository<TableName, TypeOfPrimaryKey>
 * @author Tim
 */
public interface ISyllabusRepository extends CrudRepository<Syllabus, Integer> {
    Syllabus findById(int id);
    Syllabus findByAssignment(String assignment);
    Syllabus findByDueDate(LocalDate dueDate);
    //List<Syllabus> findByCompleted(boolean completed);
}
