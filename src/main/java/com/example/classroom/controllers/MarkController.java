package com.example.classroom.controllers;


import com.example.classroom.dto.ScoreRequest;
import com.example.classroom.services.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/marks")
public class MarkController extends BaseController {

    MarkService markService;
    @Autowired
    public MarkController(MarkService markService){
        this.markService = markService;
    }
    @PostMapping("/score")
    public ResponseEntity score(@RequestBody ScoreRequest scoreRequest){

        var userId = this.getUserId();
        this.markService.score(scoreRequest, userId);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        var data = this.markService.getAll();
        return ResponseEntity.ok(data);
    }
}
