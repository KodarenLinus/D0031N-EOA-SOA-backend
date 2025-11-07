package com.example.D0031N.Canvas;

public record CanvasRosterItemDto(
        String studentId,
        String name,
        String email,
        String canvasGrade,  // ev. betyg för vald assignment
        String gradedAt      // ISO8601-sträng
) {}
