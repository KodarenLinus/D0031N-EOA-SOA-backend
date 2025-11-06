// GradeUpsertDto.java – request body vid rättning/uppdatering
package com.example.D0031N.Canvas;

import java.time.Instant;

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

