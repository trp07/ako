package com.ako.api;

import com.ako.data.Syllabus;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ako.service.S3Service;
import com.ako.service.SyllabusService;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Syllabus Controller
 * @author Tim
 * @refs
 * https://thysmichels.com/2014/08/04/spring-mvc-angularjs-todo-list/
 */
@RestController
@RequestMapping(value="/syllabus", produces=MediaType.APPLICATION_JSON_VALUE)
public class SyllabusController {

    @Autowired
    S3Service s3Service;
    
    @Autowired
    SyllabusService syllabusService;

    /* private attributes */
    private final Logger logger = LogManager.getLogger(SyllabusController.class);
    private String downloadLink = "https://s3-us-west-2.amazonaws.com/enpm613-ako/Syllabus_Default.pdf";
    
    
    /********************************************************************
     * GETTERS & SETTERS
     ********************************************************************/

    public String getDownloadLink() {
        return this.downloadLink;
    }

    public void setDownloadLink(String url) {
        this.downloadLink = url; 
    }    
    
    
    /********************************************************************
     * S3Service
     ********************************************************************/
    
    @RequestMapping(method=RequestMethod.GET, value="/download")
    public @ResponseBody String downloadSyllabus() {
        logger.info("====SyllabusController==== downloadSyllabus() called");
        return this.downloadLink;
	}

    /* TODO: may need to do user-type checking to make sure only the
       instructor is uploading */
    @RequestMapping(method=RequestMethod.POST, value="/upload/{file}")
    public @ResponseBody String uploadSyllabus(@PathVariable("file") MultipartFile file) {
        String fname = file.getOriginalFilename();
        String dlink;

        logger.info("====SyllabusController==== uploadSyllabus() called with: " + fname);
        try {
            s3Service.uploadFile(fname, file);
            dlink = s3Service.generateDownloadLink(fname);
            if (!dlink.equals("NO_URL")) {
                this.setDownloadLink(dlink);
            }
            logger.info("====SyllabusController==== uploadFile() successful!");
            
        } catch (Exception ace) {
            logger.error("====SyllabusController==== uploadSyllabus() Error");
        }
        return this.downloadLink;
    }
    
    
    /********************************************************************
     * SyllabusService
     ********************************************************************/
    
    @RequestMapping(method=RequestMethod.GET, value="/all.json")
    public @ResponseBody List<Syllabus> getSyllabus() {
        logger.info("====SyllabusController==== getSyllabus() called");
        return syllabusService.getAllAssignments();
    }

    @RequestMapping(method=RequestMethod.POST, value="/add/{assignment}/{dueDate}")
    public @ResponseBody void addAssignment(@PathVariable("assignment") String assignment, @PathVariable("dueDate") String dueDate) {
        logger.info("====SyllabusController==== addAssignment() called with: " + assignment + " => " + dueDate);
        syllabusService.addAssignment(assignment, dueDate);
    }
    
    @RequestMapping(method=RequestMethod.PUT, value="/update/{id}/{assignment}")
    public @ResponseBody void updateAssignment(@PathVariable("id") int id, 
                @PathVariable("assignment") String assignment) {
        logger.info("====SyllabusController==== updateAssignment() called with: " + assignment);
        syllabusService.updateSyllabus(id, assignment);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/delete/{assignment}")
    public @ResponseBody void deleteAssignment(@PathVariable("assignment") String assignment) {
        logger.info("====SyllabusController==== deleteAssignment() called with: " + assignment);
        syllabusService.deleteOneByName(assignment);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/deleteAll")
    public int deleteAll() {
        logger.info("====SyllabusController==== deleteAll() called");
        return syllabusService.deleteAll();
    }
}
