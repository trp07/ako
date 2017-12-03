package com.ako.data;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
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
    
    
    @Query("select u from User u where u.firstName like %?1%")
    List<User> findByFirstnameStartsWith(String firstname);
}