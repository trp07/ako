package com.ako.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ako.data.Module;
import com.ako.data.IModuleRepository;

/**
 * A module business service
 * @author burucan
 *
 */
@Service
public class ModuleService {
	/**
	 * The Module repository
	 */
	@Autowired
	IModuleRepository moduleRepository;
	
	/**
	 * The Logger
	 */
	private final Logger logger = LogManager.getLogger(ModuleService.class);

	/**
	 * Returns all of the modules for a given course
	 * @param course
	 * @return
	 */
	public List<Module> getAllModules(int courseId) {
		logger.debug("The module service received a request to get all modules for course " + courseId + ".");
		return moduleRepository.findByCourseId(courseId);
	}
	
	/**
	 * Returns all of the published modules for a given course
	 * @param course
	 * @return
	 */
	public List<Module> getAllPublishedModules(int courseId) {
		logger.debug("The module service received a request to get all published modules for course " + courseId + ".");
		List<Module> modules = moduleRepository.findByCourseId(courseId);
		List<Module> publishedModules = new ArrayList<Module>();
		for (Module module: modules) {
			if (module.getIsPublished()) {
				publishedModules.add(module);
			}
		}
		return publishedModules;
	}

	/**
	 * Find a module by id
	 * @param id
	 * @return
	 */
	public Module getModule(int id){
		logger.debug("The module service received a request to get the module with id " + id + ".");
		return moduleRepository.findOne(id);
	}
	
	/**
	 * Add a module to the database
	 * @param module
	 * @return
	 */
	public Module uploadModule(Module module) {
		logger.debug("The module service received a request to ADD a module for course " + module.getId()+ ".");
		if (this.moduleRepository.exists(module.getId())) {
			return saveModule(module);
		}
		return moduleRepository.save(module);
	}
	/**
	 * Updates a module in the database
	 * @param module
	 * @return
	 */
	public Module saveModule(Module module) {
		logger.debug("The module service received a request to SAVE the module with id " + module.getId() + ".");
		return moduleRepository.save(module);
	}
	/**
	 * Deletes a module from the database
	 * @param module
	 * @return
	 */
	public List<Module> deleteModule(Module module) {
		logger.debug("The module service received a request to DELETE the module with id " + module.getId() + ".");
		moduleRepository.delete(module);
		return getAllModules(module.getCourseId());
	}

}
