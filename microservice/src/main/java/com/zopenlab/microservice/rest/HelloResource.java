package com.zopenlab.microservice.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {
	
	@GetMapping("/hello")
	public String HelloWorld(){
		return "Hello World";
		
	}

	@GetMapping(value="/json")
	public Hello HelloJson(){
		
		return new Hello("Greetings","Hello World");
	}
	
	private class Hello {
		private String title;
		private String value;
		
		public Hello() {	
		}
		
		public Hello(String title, String value) {
			super();
			this.title = title;
			this.value = value;
		}

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
}
