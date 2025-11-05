package com.example.D0031N.StudentITS;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.customizer.Bind;

import java.util.Optional;

public interface StudentDao {

    @SqlQuery("""
        SELECT personnummer
        FROM studentits.student
        WHERE student_id = :username
        LIMIT 1
    """)
    Optional<String> findPersonnummerByStudentId(@Bind("username") String username);
}
