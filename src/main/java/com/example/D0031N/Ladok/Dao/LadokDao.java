package com.example.D0031N.Ladok.Dao;

import com.example.D0031N.Ladok.Dto.LadokRosterItemDto;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.customizer.Bind;

import java.time.LocalDate;
import java.util.List;

@RegisterConstructorMapper(LadokRosterItemDto.class)
public interface LadokDao {


    @SqlQuery("""
        SELECT
            k.kurskod AS kurskod,
            p.personnummer AS personnummer,
            p.fornamn AS fornamn,
            p.efternamn AS efternamn,
            r.status AS registreringsStatus,
            (lr.id IS NOT NULL) AS sent,
            lr.status AS ladokStatus,
            lr.betyg AS ladokBetyg,
            lr.datum AS resultatDatum,
            lr.created_at AS registeredAt
        FROM ladok_registrering r
        JOIN ladok_person p ON p.id  = r.person_id
        JOIN ladok_kurstillfalle kt ON kt.id = r.kurstillfalle_id
        JOIN ladok_kurs k ON k.id = kt.kurs_id
        LEFT JOIN ladok_resultat lr ON lr.kurskod = :courseCode
            AND lr.modulkod = :moduleCode
        WHERE k.kurskod = :courseCode
        ORDER BY p.efternamn, p.fornamn
    """)
    List<LadokRosterItemDto> rosterByCourseAndModule(
            @Bind("courseCode") String courseCode,
            @Bind("moduleCode") String moduleCode
    );

    // Säkert insert: returnerar id om ny rad skapades, annars null
    @SqlUpdate("""
        INSERT INTO ladok_resultat (personnummer, kurskod, modulkod, datum, betyg, status)
        VALUES (:pnr, :courseCode, :moduleCode, :date, :grade, 'registrerad')
        ON CONFLICT (personnummer, kurskod, modulkod) DO NOTHING
    """)
    @GetGeneratedKeys
    Long insertResultIfNotExists(@Bind("pnr") String pnr,
                                 @Bind("courseCode") String courseCode,
                                 @Bind("moduleCode") String moduleCode,
                                 @Bind("date") LocalDate date,
                                 @Bind("grade") String grade);
}
