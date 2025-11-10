package com.example.D0031N.Ladok.Dto;

import jakarta.validation.constraints.Pattern;

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
        @Pattern(regexp = "^[0-9]{8}-[0-9]{4}$")
        String personnummer,
        String kurskod,
        @Pattern(regexp = "^[0-9]{4}$")
        String modulkod,
        LocalDate datum,
        String betyg
) {}
