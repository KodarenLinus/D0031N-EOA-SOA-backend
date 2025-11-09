package com.example.D0031N.Ladok.Dto;

import java.time.LocalDate;

/**
 * Data Transfer Object
 * For Ladok Result Request
 * Ladok Result Request (JSON):
 * LadokResultRequest {
 *     personnummer: xxxxxxxxx-xxxx,
 *     kurskod: xxxxxx,
 *     modulkod: xxxx,
 *     datum: xxxxxxx,
 *     betyg: xx
 * }
 * */
public record LadokResultRequestDto(
        String personnummer,
        String kurskod,
        String modulkod,
        LocalDate datum,
        String betyg
) {}
