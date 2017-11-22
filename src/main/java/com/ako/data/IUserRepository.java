package com.ako.data;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * A user data service
 * CrudRepository<TableName, TypeOfPrimaryKey>
 * @author Prashant
 */
public interface IUserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    User findByUserName(String email);
}