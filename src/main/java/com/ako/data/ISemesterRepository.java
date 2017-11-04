package com.ako.data;

import org.springframework.data.repository.CrudRepository;

/**
 * A Semester data service
 * CrudRepository<TableName, TypeOfPrimaryKey>
 * @author noel.buruca
 */

public interface ISemesterRepository extends CrudRepository<Semester, Integer> {

}
