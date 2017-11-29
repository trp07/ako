package com.ako.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ako.data.Syllabus;
import com.ako.service.SyllabusService;
import java.util.List;


/**
 * Syllabus REST Controller
 * @author phillipstr
 * @Refs:
 * https://thysmichels.com/2014/08/04/spring-mvc-angularjs-todo-list/
 */
@RestController
@RequestMapping(value="/syllabus", produces=MediaType.APPLICATION_JSON_VALUE)
public class SyllabusRestController {
    
    @Autowired
    SyllabusService syllabusService;
    
    
    @RequestMapping(method=RequestMethod.GET, value="/")
    public @ResponseBody List<Syllabus> getSyllabus() {
        return syllabusService.getAllAssignments();
    }

    @RequestMapping(method=RequestMethod.POST, value="/add/{assignment}")
    public @ResponseBody void addAssignment(@PathVariable("assignment") Syllabus assignment) {
            syllabusService.addAssignment(assignment);
    }
    
    @RequestMapping(method=RequestMethod.PUT, value="/update/{id}")
    public @ResponseBody void updateAssignment(@PathVariable("id") int id, Syllabus assignment) {
            syllabusService.updateSyllabus(id, assignment);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/delete/{id}")
    public @ResponseBody void deleteAssignment(@PathVariable("id") int id) {
            syllabusService.deleteOneById(id);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/deleteAll")
    public int deleteAll() {
            return syllabusService.deleteAll();
    }
    
}
