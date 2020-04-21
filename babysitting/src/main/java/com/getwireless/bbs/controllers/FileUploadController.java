package com.getwireless.bbs.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.getwireless.bbs.model.MessageResponse;
import com.getwireless.bbs.services.FlickrService;

@RestController
@RequestMapping("/upload")
@CrossOrigin("*")
public class FileUploadController {
	@Autowired
	private FlickrService flickrService;

	@PostMapping("/savefile")
	public ResponseEntity<MessageResponse> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String message;
		String result;
		try {
			try {
				result = flickrService.uploadImage(file.getInputStream(), file.getOriginalFilename());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("FAIL!");
			}

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(true, result));
		} catch (Exception e) {
			e.printStackTrace();
			message = "Failed to upload!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(false, message));
		}
	}

}
