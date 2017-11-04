package com.ako.data;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * A user type data service
 * CrudRepository<TableName, TypeOfPrimaryKey>
 * @author noel.buruca
 */

public interface IUserTypeRepository extends CrudRepository<UserType, Integer> {

}
