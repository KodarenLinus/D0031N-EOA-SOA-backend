package com.example.D0031N.Canvas.Dto;
/*
 * Data Transfer Object
 * For Canvas Roster Item
 * Canvas Roster Item (JSON):
 * CanvasRosterItem{
 *   studentId: xxxxx,
 *   name: xxxxx,
 *   canvasGrade: xxxxxx,
 *   gradedAt: xxxxxx,
 * }
 * */
public record CanvasRosterItemDto(
        String studentId,
        String name,
        String canvasGrade,
        String gradedAt
) {}
