package com.example.D0031N.StudentITS.Dao;

import com.example.D0031N.StudentITS.Dto.ItsPersonLookupDto;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

/**
 *  Data Access Object
 *  For StudentIts
 * */
@RegisterConstructorMapper(ItsPersonLookupDto.class)
public interface StudentItsDao {

    @SqlQuery("""
        SELECT anvandarnamn, personnummer, fornamn, efternamn
        FROM its_person
        WHERE anvandarnamn = :user
    """)
    ItsPersonLookupDto findByUsername(@Bind("user") String anvandarnamn);
}
