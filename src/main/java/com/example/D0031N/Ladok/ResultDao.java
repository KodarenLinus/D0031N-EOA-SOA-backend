package com.example.D0031N.Ladok;

import java.time.LocalDate;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ResultDao {
    @SqlUpdate("""
        INSERT INTO 
            results(
                personnummer, 
                kurskod, 
                modul, 
                datum, 
                betyg, 
                status
            )
        VALUES (
            :pnr, 
            :kurskod, 
            :modul, 
            :datum, 
            :betyg, 
            :status
        )
    """)
    int insert(@Bind("pnr") String pnr,
               @Bind("kurskod") String kurskod,
               @Bind("modul") String modul,
               @Bind("datum") LocalDate datum,
               @Bind("betyg") String betyg,
               @Bind("status") String status);
}