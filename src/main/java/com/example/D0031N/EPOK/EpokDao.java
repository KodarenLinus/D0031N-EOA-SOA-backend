package com.example.D0031N.EPOK;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import com.example.D0031N.EPOK.ModuleDto;

public interface EpokDao {
    @SqlQuery("""
    select modul_kod as kod, modul_namn as namn, hp, aktiv
    from course_modules
    where kurskod = :kurskod and aktiv = true
    order by modul_kod
  """)
    @RegisterBeanMapper(ModuleDto.class)
    List<ModuleDto> findActiveModules(@Bind("kurskod") String kurskod);
}