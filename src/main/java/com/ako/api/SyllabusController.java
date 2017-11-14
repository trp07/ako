package com.ako.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ako.service.SyllabusService;

/**
 * Syllabus File Upload and Download Controller
 * @author Tim
 */

@Controller
@RequestMapping(value="/syllabus", produces=MediaType.APPLICATION_PDF_VALUE)
public class SyllabusController {

    @Autowired
    SyllabusService syllabusService;
	

    /* File DOWNLOAD */
    @RequestMapping(method=RequestMethod.GET, value="/")
	public @ResponseBody void downloadSyllabus() {
        
		syllabusService.downloadFile("syllabus.pdf");

	}


    /* File UPLOAD    */
    @RequestMapping(method=RequestMethod.POST, value="/")
    public @ResponseBody void uploadSyllabus(@RequestParam("File") 
                                                String file) {

        syllabusService.uploadFile("syllabus.pdf", file);

    }

}
