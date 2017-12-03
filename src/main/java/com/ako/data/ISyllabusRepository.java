package com.ako.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


/**
 * A syllabus data service
 * CrudRepository<TableName, TypeOfPrimaryKey>
 * @author Tim
 */
public interface ISyllabusRepository extends CrudRepository<Syllabus, Integer> {
    Syllabus findById(int id);
    Syllabus findByName(String assignment);
    Syllabus findByDueDate(Date dueDate);
    //List<Syllabus> findByCompleted(boolean completed);
}
