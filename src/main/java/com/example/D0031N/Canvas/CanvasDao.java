package com.example.D0031N.Canvas;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.customizer.Bind;

import java.util.List;

public interface CanvasDao {

    @SqlQuery("""
    SELECT
        a.id, 
        a.name, 
        a.scale_hint, 
        a.type
    FROM 
        canvas.assignment a
    WHERE
        a.course_code = :kurskod
    ORDER BY
        a.id
  """)
    @RegisterBeanMapper(AssignmentDto.class)
    List<AssignmentDto> findAssignmentsByCourse(@Bind("kurskod") String kurskod);

    @SqlQuery("""
    SELECT
        g.username, 
        g.label, 
        g.points, 
        to_char(g.graded_at,'YYYY-MM-DD"T"HH24:MI:SS') as gradedAt
    FROM
        canvas.grade g
    WHERE
        g.assignment_id = :assignmentId
    ORDER BY
        g.id
  """)
    @RegisterBeanMapper(GradeDto.class)
    List<GradeDto> findGradesByAssignment(@Bind("assignmentId") Long assignmentId);
}
