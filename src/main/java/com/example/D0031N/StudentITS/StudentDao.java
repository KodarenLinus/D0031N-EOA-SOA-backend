package com.example.D0031N.StudentITS;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface StudentDao {
    @SqlQuery("select personnummer from students where username = :username")
    String findPersonnummer(@Bind("username") String username);
}