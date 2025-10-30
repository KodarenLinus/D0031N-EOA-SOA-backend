package com.example.D0031N.Ladok;

import jakarta.validation.constraints.*;

public class ResultRequest {
    @Pattern(regexp="^[0-9]{8}-[0-9]{4}$")
    private String personnummer;
    @NotBlank private String kurskod;
    @Pattern(regexp="^[0-9]{4}$") private String modul;
    @NotBlank private String datum;
    @Size(min=1, max=3) private String betyg;

    public String getPersonnummer() { return personnummer; }
    public String getKurskod() { return kurskod; }
    public String getModul() { return modul; }
    public String getDatum() { return datum; }
    public String getBetyg() { return betyg; }
}