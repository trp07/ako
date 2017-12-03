package com.ako.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface IModuleRepository extends CrudRepository<Module, Integer>{
	List<Module> findByCourseId(int courseId);
}
