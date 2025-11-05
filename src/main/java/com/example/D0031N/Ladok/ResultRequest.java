package com.example.D0031N.Ladok;

import jakarta.validation.constraints.*;

public record ResultRequest(
        // REG EX, Only personummer with xxxxxx-xxxx
        @Pattern(regexp = "^[0-9]{6}-[0-9]{4}$")
        String personnummer,

        @NotBlank
        String kurskod,

        @Pattern(regexp = "^[0-9]{4}$")
        String modul,

        @NotBlank
        String datum, // yyyy-MM-dd

        @Size(min = 1, max = 3)
        String betyg
) {}
