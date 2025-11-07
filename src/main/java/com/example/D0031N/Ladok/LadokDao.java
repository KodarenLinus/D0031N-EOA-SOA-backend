package com.example.D0031N.Ladok;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterConstructorMapper(LadokRosterItemDto.class)
@RegisterConstructorMapper(LadokCourseInstanceDto.class)
public interface LadokDao {

    @SqlQuery("""
        SELECT kt.id AS id, k.kurskod AS kurskod, kt.instanskod AS instanskod
        FROM ladok_kurstillfalle kt
        JOIN ladok_kurs k ON k.id = kt.kurs_id
        WHERE k.kurskod = :kurskod
        ORDER BY kt.id
    """)
    List<LadokCourseInstanceDto> listCourseInstances(@Bind("kurskod") String kurskod);

    @SqlQuery("""
        SELECT kt.id AS kurstillfalleId, k.kurskod AS kurskod, kt.instanskod AS instanskod,
               p.personnummer, p.fornamn, p.efternamn, r.status
        FROM ladok_registrering r
        JOIN ladok_person p       ON p.id = r.person_id
        JOIN ladok_kurstillfalle kt ON kt.id = r.kurstillfalle_id
        JOIN ladok_kurs k         ON k.id = kt.kurs_id
        WHERE k.kurskod = :kurskod AND kt.instanskod = :instans
        ORDER BY p.personnummer
    """)
    List<LadokRosterItemDto> rosterByCourseInstance(@Bind("kurskod") String kurskod,
                                                    @Bind("instans") String instanskod);

    // Idempotent insert: returnerar genererat id om nytt, annars NULL
    @SqlQuery("""
        INSERT INTO ladok_resultat(personnummer, kurskod, modulkod, datum, betyg, status)
        VALUES (:pnr, :kurskod, :modulkod, :datum::date, :betyg, 'registrerad')
        ON CONFLICT (personnummer, kurskod, modulkod) DO NOTHING
        RETURNING id
    """)
    Long insertResultIfNotExists(@Bind("pnr") String personnummer,
                                 @Bind("kurskod") String kurskod,
                                 @Bind("modulkod") String modulkod,
                                 @Bind("datum") String datum,
                                 @Bind("betyg") String betyg);
}
