package com.eagle.contentvalidation.controller;

import com.eagle.contentvalidation.service.ContentValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/")
public class ContentValidationController {

    @Autowired
    private ContentValidationService contentValidationService;

    @PostMapping("profanity/getprofanitychecklist")
    public ResponseEntity<?> findRecommendedConnections() throws IOException {
        Object response = contentValidationService.getTheProfanityCheckList();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
