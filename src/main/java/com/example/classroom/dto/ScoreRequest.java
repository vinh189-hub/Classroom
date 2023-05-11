package com.example.classroom.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ScoreRequest {
    private int assignmentId;
    private int classroomId;
    JsonNode score;
}
