package com.example.D0031N.Canvas.Dto;
/*
 * Data Transfer Object
 * For Canvas Student
 *  Canvas Student (JSON):
 * CanvasStudent {
 *   studentId: xxxxx,
 *   name: xxxxx,
 * }
 * */
public record CanvasStudentDto(
        String studentId,
        String name
) {}
