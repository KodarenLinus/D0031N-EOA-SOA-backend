package com.example.D0031N.Canvas;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.customizer.Bind;

import java.util.List;

public interface CanvasDao {

    @SqlQuery("""
    select a.id, a.name, a.scale_hint, a.type
    from canvas.assignment a
    where a.course_code = :kurskod
    order by a.id
  """)
    @RegisterBeanMapper(AssignmentDto.class)
    List<AssignmentDto> findAssignmentsByCourse(@Bind("kurskod") String kurskod);

    @SqlQuery("""
    select g.username, g.label, g.points, to_char(g.graded_at,'YYYY-MM-DD"T"HH24:MI:SS') as gradedAt
    from canvas.grade g
    where g.assignment_id = :assignmentId
    order by g.id
  """)
    @RegisterBeanMapper(GradeDto.class)
    List<GradeDto> findGradesByAssignment(@Bind("assignmentId") Long assignmentId);
}
