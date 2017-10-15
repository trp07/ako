package com.ako.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyllabusController {

	@RequestMapping("/syllabus")
	public String testMethod() {
		return "Hi";
	}
}