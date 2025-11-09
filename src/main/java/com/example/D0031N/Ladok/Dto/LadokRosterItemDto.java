package com.example.D0031N.Ladok.Dto;
/**
 * Data Transfer Object
 * For Ladok Roster Item
 * Ladok Roster Item (JSON):
 * LadokRosterItem {
 *     kurskod: xxxx,
 *     personnummer: xxxxxxxxx-xxxx,
 *     fornamn: xxxxxxx,
 *     efternamn: xxxxxx,
 *     registreringsStatus: xxxxxx,
 *     sent: xxxxxxxx,
 *     ladokStatus: xxxxxxx,
 *     ladokBetyg: xxxxxxx,
 *     registeredAt: xxxxxxxx,
 * }
 * */
public record LadokRosterItemDto(
        String kurskod,
        String personnummer,
        String fornamn,
        String efternamn,
        String registreringsStatus,
        Boolean sent,
        String ladokStatus,
        String ladokBetyg,
        java.time.OffsetDateTime registeredAt
) {}
