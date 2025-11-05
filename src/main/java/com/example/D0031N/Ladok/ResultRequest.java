package com.example.D0031N.Ladok;


import jakarta.validation.constraints.*;

public class ResultRequest {
    @Pattern(regexp="^[0-9]{8}-[0-9]{4}$")
    private String personnummer;

    @NotBlank private String kurskod;

    @Pattern(regexp="^[0-9]{4}$")
    private String modul;

    @NotBlank private String datum; // yyyy-MM-dd

    @Size(min=1, max=3)
    private String betyg;

    public String getPersonnummer(){
        return personnummer;
    }

    public String getKurskod(){
        return kurskod;
    }
    public String getModul(){
        return modul;
    }

    public String getDatum(){
        return datum;
    }

    public String getBetyg(){
        return betyg;
    }

    public void setPersonnummer(String v){
        this.personnummer=v;
    }

    public void setKurskod(String v){
        this.kurskod=v;
    }

    public void setModul(String v){
        this.modul=v;
    }

    public void setDatum(String v){
        this.datum=v;
    }

    public void setBetyg(String v){
        this.betyg=v;
    }
}