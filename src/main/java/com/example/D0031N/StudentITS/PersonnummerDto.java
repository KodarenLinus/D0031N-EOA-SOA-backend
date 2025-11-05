package com.example.D0031N.StudentITS;

public class PersonnummerDto {
    private String username;
    private String personnummer;

    public PersonnummerDto() {}
    public PersonnummerDto(String username, String personnummer) {
        this.username = username; this.personnummer = personnummer;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonnummer() {
        return personnummer;
    }

    public void setUsername(String v){
        this.username=v;
    }

    public void setPersonnummer(String v){
        this.personnummer=v;
    }
}
