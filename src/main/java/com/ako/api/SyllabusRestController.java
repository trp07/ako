package com.ako.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ako.data.Syllabus;
import com.ako.service.SyllabusService;
import java.util.List;


/**
 * Syllabus REST Controller
 * @author phillipstr
 */
@RestController
@RequestMapping(value="/syllabus", produces=MediaType.APPLICATION_JSON_VALUE)
public class SyllabusRestController {
    
    @Autowired
    SyllabusService syllabusService;
    
    
    @RequestMapping(method=RequestMethod.GET, path="/")
    public List<Syllabus> getSyllabus() {
        return syllabusService.getAllAssignments();
    }

    @RequestMapping(method=RequestMethod.POST, path="/")
    public Syllabus addAssignment(@RequestBody Syllabus assignment) {
            return syllabusService.addAssignment(assignment);
    }
    
    @RequestMapping(method=RequestMethod.PUT, path="/{id}")
    public Syllabus updateAssignment(@RequestBody Syllabus assignment, @PathVariable int id) {
            return syllabusService.updateSyllabus(assignment);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, path="/{id}")
    public Syllabus deleteAssignment(@RequestBody Syllabus assignment) {
            return syllabusService.deleteOne(assignment);
    }
    
}
