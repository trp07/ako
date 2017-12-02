package com.ako.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ako.data.File;
import com.ako.data.Module;
import com.ako.service.FileService;
import com.ako.service.ModuleService;

/**
 * Module Controller
 * @author burucan
 *
 */
@RestController
@RequestMapping(value="/module", produces=MediaType.APPLICATION_JSON_VALUE)
public class ModuleController {
	
	@Autowired
	ModuleService moduleService;
	
	@Autowired
	FileService fileService;
	/**
	 * The logger
	 */
	private final Logger logger = LogManager.getLogger(ModuleController.class);
	
	/**
	 * Gets all of the modules for a given course
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/{courseId}")
	public List<Module> viewModules(@PathVariable int courseId) {
		logger.info("The module controller received a request to retrieve all modules for course " + courseId + ".");
		return moduleService.getAllModules(courseId);
	}
	
	/**
	 * Creates a module
	 * @param module
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, path= "/")
	public Module createModule(@RequestBody Module module) {
		logger.info("The module controller received a request to CREATE a module with name " + module.getName() + ".");
		return moduleService.uploadModule(module);
	}
	
	/**
	 * Saves an existing module
	 * @param module
	 * @return
	 */
	@RequestMapping(method=RequestMethod.PUT, path= "/")
	public Module editModule(@RequestBody Module module) {
		logger.info("The module controller received a request to SAVE a module with id " + module.getId() + ".");
		return moduleService.saveModule(module);
	}
	
	/**
	 * Delete a module
	 * @param module
	 * @return
	 */
	@RequestMapping(method=RequestMethod.DELETE, path= "/{moduleId}")
	public List<Module> deleteModule(@PathVariable int moduleId) {
		logger.info("The module controller received a request to DELETE a module with id " + moduleId + ".");
		Module module = moduleService.getModule(moduleId);
		if (module == null) {
			String error = "The module does not exist.";
			logger.error(error);
		}
		return moduleService.deleteModule(module);
	}
	
	/**
	 * Adds a file to a module
	 * @param file
	 * @return
	 */
	@RequestMapping(method=RequestMethod.PUT, path= "/file/{moduleId}")
	public Module addModuleFile(@RequestBody File file, @PathVariable int moduleId) {
		logger.info("The module controller received a request to SAVE a file in module " + moduleId + ".");
		logger.info("The file name is " + file.getName() + ".");
		Module module = moduleService.getModule(moduleId);
		List<File> moduleFiles = module.getModuleFiles();
		if (!moduleFiles.contains(file)) {
			moduleFiles.add(file);
			module.setModuleFiles(moduleFiles);
			moduleService.saveModule(module);
		} else {
			logger.info("The file is already in the module!");
		}
		return moduleService.getModule(moduleId);
	}
	
	/**
	 * Deletes a file from the module
	 * @param file
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.DELETE, path= "/file/{fileId}")
	public Module deleteModuleFile(@PathVariable int fileId) {
		logger.info("The module controller received a request to DELETE a file with id " + fileId +".");
		File file = fileService.getFile(fileId);
		Module module = file.getModule();
		logger.info("The file belongs to module " + module.getId() + ".");
		List<File> moduleFiles = module.getModuleFiles();
		if (moduleFiles.contains(file)) {
			moduleFiles.remove(file);
			module.setModuleFiles(moduleFiles);
			moduleService.saveModule(module);
		} else {
			logger.info("The module did not contain the file!");
		}
		return moduleService.getModule(module.getId());
	}
}
