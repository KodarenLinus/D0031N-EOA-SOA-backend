package com.example.D0031N.EPOK;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface EpokDao {
    @SqlQuery("""
        SELECT 
            modul_kod AS kod, 
            modul_namn AS namn, 
            hp, 
            aktiv
        FROM 
            course_modules
        WHERE 
            kurskod = :kurskod 
        AND 
            aktiv = TRUE
        ORDER BY 
            modul_kod
    """)
    @RegisterBeanMapper(ModuleDto.class)
    List<ModuleDto> findActiveModules(@Bind("kurskod") String kurskod);
}