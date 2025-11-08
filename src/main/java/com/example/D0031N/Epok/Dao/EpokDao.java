package com.example.D0031N.Epok.Dao;

import com.example.D0031N.Epok.Dto.EpokModuleDto;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import java.util.List;

@RegisterConstructorMapper(EpokModuleDto.class)
public interface EpokDao {

    @SqlQuery("""
        SELECT m.modulkod AS modulkod, m.modulnamn AS modulnamn, m.aktiv AS aktiv
        FROM epok_module m
        JOIN epok_course c ON c.id = m.kurs_id
        WHERE c.kurskod = :kurskod
        AND (:onlyActive::boolean IS FALSE OR m.aktiv = TRUE)
        ORDER BY m.modulkod
    """)
    List<EpokModuleDto> listModules(@Bind("kurskod") String kurskod,
                                    @Bind("onlyActive") boolean onlyActive);
}
