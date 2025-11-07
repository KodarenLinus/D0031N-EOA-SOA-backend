package com.example.D0031N.Canvas.Dto;
/*
 * Data Transfer Object
 * For Grade
 * Grade (JSON):
 * Grade {
 *   studentId: xxxxx,
 *   grade: xxxxx,
 *   comment: xxxxx,
 *   gradeAt: xxxxx,
 * }
 * */
public record GradeDto(
        String studentId,
        String grade,
        String comment,
        String gradedAt
) {}
