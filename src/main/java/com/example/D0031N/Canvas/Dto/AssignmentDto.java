package com.example.D0031N.Canvas.Dto;
/*
* Data Transfer Object
* For Assignments
* Assignment (JSON):
* assignment{
*   id: xxxxx,
*   name: xxxxx,
*   scalehint: xxxxxx,
*   type: xxxxxx,
* }
* */
public record AssignmentDto(
        Long id,
        String name,
        String scaleHint,
        String type
) {}