package com.ako.api;

import java.io.File;
import java.lang.Exception;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;

import com.ako.service.S3Service;


/**
 * Syllabus Controller
 * @author Tim
 */
@Controller
@RequestMapping(value="/syllabus", produces=MediaType.APPLICATION_PDF_VALUE)
public class SyllabusController {

    @Autowired
    S3Service s3Service;

    /* private attributes */
    private final Logger logger = LogManager.getLogger(SyllabusController.class);
    private String downloadLink = "https://s3-us-west-2.amazonaws.com/enpm613-ako/Syllabus_Default.pdf";

    public String getDownloadLink() {
        return this.downloadLink;
    }

    public void setDownloadLink(String url) {
        this.downloadLink = url; 
    }    
    
    @RequestMapping(method=RequestMethod.GET, value="/download")
    public @ResponseBody void downloadSyllabus() {
        
        logger.info("SyllabusController.downloadSyllabus called");
        s3Service.downloadFile("syllabus.pdf");

	}

    /* TODO: may need to do user-type checking to make sure only the
       instructor is uploading */
    @RequestMapping(method=RequestMethod.POST, value="/upload")
    public @ResponseBody void uploadSyllabus(@RequestParam("syllabus") MultipartFile file) {
        String fname = file.getOriginalFilename();
        String dlink;

        logger.info("SyllabusController.uploadSyllabus called with: " + fname);
        try {
            s3Service.uploadFile(fname, file);
            dlink = s3Service.generateDownloadLink(fname);
            if (!dlink.equals("NO_URL")) {
                this.setDownloadLink(dlink);
            }
            logger.info("SyllabusController.uploadFile successful!");

        } catch (Exception ace) {
            logger.error("SyllabusController.UploadSyllabus Error");
        }
    }   
}
