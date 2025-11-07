package com.example.D0031N.Ladok;

public record LadokResultRequestDto(
        String personnummer,
        String kurskod,
        String modulkod,
        String datum,
        String betyg
) {}
