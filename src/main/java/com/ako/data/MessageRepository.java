package com.ako.data;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Email, Integer> {

}
