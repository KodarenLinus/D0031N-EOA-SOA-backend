package com.example.D0031N.StudentITS.Dto;
/**
 *  Data Transfer Object
 *  For Its Person Look Up
 *  Its Person Look Up (JSON):
 *  ItsPersonLookUp {
 *      anvandarnamn: xxxxxx,
 *      personnummer: xxxxxxxxxx-xxxx,
 *      fornamn: xxxxxxxxxx,
 *      efternamn: xxxxxxxxxx
 *  }
 * */
public record ItsPersonLookupDto(
        String anvandarnamn,
        String personnummer,
        String fornamn,
        String efternamn
) {}
