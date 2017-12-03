package com.ako.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IMessageRepository extends CrudRepository<Message, Integer> {
	
    @Query("select m from Message m where m.id in (select mu.messageId from MessageUser mu where mu.userId = ?1)")
	List<Message> findByUserId(int userId);

}
