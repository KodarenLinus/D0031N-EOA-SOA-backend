package com.example.D0031N.Canvas;

public record CanvasStudentDto(
        String studentId,
        String name,   // first + last
        String email   // nullable if not available
) {}
