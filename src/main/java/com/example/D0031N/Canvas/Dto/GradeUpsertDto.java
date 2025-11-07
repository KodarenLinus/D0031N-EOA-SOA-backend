package com.example.D0031N.Canvas.Dto;

import java.time.Instant;
/*
 * Data Transfer Object
 * For Grade Upsert
 * Grade Upsert (JSON):
 * GradeUpsert {
 *   grade: xxxxx,
 *   comment: xxxxx,
 *   gradeAt: xxxxx,
 * }
 * */
public record GradeUpsertDto(
        String grade,
        String comment,
        String gradedAt
) {
    public GradeUpsertDto {
        if (gradedAt == null) {
            gradedAt = Instant.now().toString();
        }
    }
}

