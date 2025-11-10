package com.example.D0031N.Ladok.Dto;
/**
 * Data Transfer Object
 * For Ladok Roster Item
 * Ladok Roster Item (JSON):
 * LadokRosterItem {
 *     kurskod: xxxx,
 *     personnummer: xxxxxxxxx-xxxx,
 *     registreringsStatus: xxxxxx,
 *     sent: xxxxxxxx,
 *     ladokStatus: xxxxxxx,
 *     ladokBetyg: xxxxxxx,
 * }
 * */
public record LadokRosterItemDto(
        String kurskod,
        String personnummer,
        String registreringsStatus,
        Boolean sent,
        String ladokStatus,
        String ladokBetyg
) {}
