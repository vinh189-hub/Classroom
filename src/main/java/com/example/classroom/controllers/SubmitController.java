package com.example.classroom.controllers;

import com.example.classroom.dto.SubmitRequest;
import com.example.classroom.services.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submit")
public class SubmitController extends BaseController {

    SubmitService submitService;

    @Autowired
    public SubmitController(SubmitService submitService){
        this.submitService = submitService;
    }
    @PostMapping("/create")
    public ResponseEntity submit(
            @ModelAttribute SubmitRequest submitRequest,
             @RequestParam("files") List<MultipartFile> multipartFile
            ){
        var userId = this.getUserId();
        this.submitService.create(submitRequest,userId, multipartFile);
        return ResponseEntity.ok("submitRequest");
    }
}
