package com.example.D0031N.Ladok.Dto;
/**
 * Data Transfer Object
 * For Ladok Result Response
 * Ladok Result Response (JSON):
 * LadokResultResponse {
 *     resultatId: xxxxxxxxx-xxxx,
 *     status: xxxxxx,
 *     detaljer: xxxx
 * }
 * */
public record LadokResultResponseDto(
        Long resultatId,
        String status,
        String detaljer
) {}
