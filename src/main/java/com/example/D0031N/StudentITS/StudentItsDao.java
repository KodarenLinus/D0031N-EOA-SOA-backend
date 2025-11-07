package com.example.D0031N.StudentITS;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

@RegisterConstructorMapper(ItsPersonLookupDto.class)
public interface StudentItsDao {

    @SqlQuery("""
        SELECT anvandarnamn, personnummer, fornamn, efternamn
        FROM its_person
        WHERE anvandarnamn = :user
    """)
    ItsPersonLookupDto findByUsername(@Bind("user") String anvandarnamn);
}
