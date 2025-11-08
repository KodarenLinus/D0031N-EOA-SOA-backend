package com.example.D0031N.Canvas.Dao;

import com.example.D0031N.Canvas.Dto.*;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

/*
* Data Access Object
*
* This is used to handel database operations for Canvas DB
* */
@RegisterConstructorMapper(CanvasStudentDto.class)
public interface CanvasDao {
    @SqlQuery("""
        SELECT s.anvandarnamn AS studentId,
            (COALESCE(s.fornamn,'') || ' ' || COALESCE(s.efternamn,'')) AS name
                FROM canvas_course c
                JOIN canvas_room r             ON r.course_id = c.id
                JOIN canvas_room_enrollment e  ON e.room_id = r.id AND e.status = 'ACTIVE'
                JOIN canvas_student s          ON s.id = e.student_id
                WHERE c.kurskod = :courseCode
                ORDER BY s.id
    """)
    List<CanvasStudentDto> listRoster(@Bind("courseCode") String courseCode);
}
