package com.example.webdemo.controller;

import com.example.webdemo.service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value= "/s3")
public class AWSS3Controller {
    @Autowired
    private AWSS3Service awss3Service;

    @PostMapping(value= "/upload")
    public ResponseEntity<String> uploadFile(@RequestPart(value= "file") final MultipartFile multipartFile) {
        awss3Service.uploadFile(multipartFile);
        final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
