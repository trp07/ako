package com.ako.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Prashant
 */

@RestController
@RequestMapping( value = "/syllabus", produces = MediaType.APPLICATION_JSON_VALUE )
public class SyllabusController {
	
	public String testMethod() {
		return "Hi";
	}
}