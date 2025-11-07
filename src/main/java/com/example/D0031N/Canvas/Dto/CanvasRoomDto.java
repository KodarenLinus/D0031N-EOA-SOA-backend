package com.example.D0031N.Canvas.Dto;
/*
 * Data Transfer Object
 * For Canvas Room
 * Canvas Room (JSON):
 * CanvasRoom {
 *   id: xxxxx,
 *   courseCode: xxxxx,
 *   instanceCode: xxxxxx,
 * }
 * */
public record CanvasRoomDto(
        Long id,
        String courseCode,
        String instanceCode
) {}
