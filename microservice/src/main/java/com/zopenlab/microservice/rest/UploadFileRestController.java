package com.zopenlab.microservice.rest;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadFileRestController {

	
	 private final Logger logger = LoggerFactory.getLogger(UploadFileRestController.class);
	
	 @Autowired
	 ResourceLoader resourceLoader;

	//Save the uploaded file to this folder
	    private static String UPLOADED_FOLDER = "/home/asseke/";
	    
	    @PostMapping(value="/api/upload")
	    public ResponseEntity<Object> uploadFile(@RequestParam( "file") MultipartFile file){
	    	
	    	logger.debug("Single file upload!");
	    	
	    	if(file.isEmpty()) return new ResponseEntity<Object>("Please select file", HttpStatus.OK);
	    	
	    	try {
				saveUploadedFiles(Arrays.asList(file));
			} catch (IOException e) {
				
				return new ResponseEntity<>("File <<"+ file.getOriginalFilename()+">> already exist",HttpStatus.BAD_REQUEST);
			}
	    	
	    	return new ResponseEntity<Object>("Successfully uploaded - " + file.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
	    }

	    @PostMapping(value="/api/multi/upload")
	    public ResponseEntity<Object> uploadMultipleFile(@RequestParam( "files") MultipartFile[] files){
	    	
	    	 String uploadedFileName = Arrays.stream(files).map(x -> x.getOriginalFilename())
	                 .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

	         if (StringUtils.isEmpty(uploadedFileName)) {
	        	 
	            return new ResponseEntity<>("please select a file!", HttpStatus.OK);
	         }

	    	
	    
	    	try {
				saveUploadedFiles(Arrays.asList(files));
			} catch (IOException e) {
				return new ResponseEntity<>("Files already exist",HttpStatus.BAD_REQUEST);
			}
	    	
	    	return new ResponseEntity<Object>("Successfully uploaded - " +uploadedFileName , new HttpHeaders(), HttpStatus.OK);
	    }
	    
	    @GetMapping(value="/api/{filename}")
	    public ResponseEntity<Resource> getFile(@PathVariable String filename){
	    	
	    	Resource resource=resourceLoader.getResource("file:"+UPLOADED_FOLDER+filename);
	    	
	    	if(!resource.exists()) return ResponseEntity.notFound().build();
	    	
	    	return ResponseEntity.ok().body(resource) ;
	    }
	    
	    @GetMapping(value="/api/download/{filename}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String filename){	    	
	    	Resource resource=resourceLoader.getResource("file:"+UPLOADED_FOLDER+filename);
	    	
	    	if(!resource.exists()) return ResponseEntity.notFound().build();
	    	
	    	return ResponseEntity.ok()
	    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + Paths.get(resource.getFilename()).toString())
	    			.body(resource) ;
	    }
	    
	    private void saveUploadedFiles(List<MultipartFile> files) throws IOException  {
	    	for (MultipartFile file : files) {	
	            if (!file.isEmpty()) {   
	            	Files.copy(file.getInputStream(), Paths.get(UPLOADED_FOLDER, file.getOriginalFilename()));
					
	            }
	            
	        }
	    }
}
