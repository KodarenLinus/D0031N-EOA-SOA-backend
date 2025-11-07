// src/main/java/com/example/D0031N/Canvas/CanvasDao.java
package com.example.D0031N.Canvas;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

// Viktigt: records behöver constructor-mapper (inte BeanMapper)
@RegisterConstructorMapper(AssignmentDto.class)
@RegisterConstructorMapper(GradeDto.class)
@RegisterConstructorMapper(CanvasStudentDto.class)
@RegisterConstructorMapper(CanvasRosterItemDto.class)
public interface CanvasDao {

    @SqlQuery("""
        SELECT 
            a.assignment_id AS id,
            a.name          AS name,
            a.scale_hint    AS scaleHint,
            a.type          AS type
        FROM assignment a
        JOIN module   m ON a.module_id = m.module_id
        JOIN course   c ON m.course_id = c.course_id
        WHERE c.course_code = :courseCode
        ORDER BY a.assignment_id
    """)
    List<AssignmentDto> findAssignmentsByCourse(@Bind("courseCode") String courseCode);

    // === Grades per assignment (för GET /assignments/{id}/grades) ===
    // Alias matchar GradeDto: studentId, grade, comment, gradedAt
    @SqlQuery("""
        SELECT 
            s.student_id AS studentId,
            g.grade      AS grade,
            g.comment    AS comment,
            to_char(g.graded_at,'YYYY-MM-DD"T"HH24:MI:SS') AS gradedAt
        FROM canvas.grade g
        JOIN submission s ON s.submission_id = g.submission_id
        WHERE s.assignment_id = :assignmentId
        ORDER BY s.student_id
    """)
    List<GradeDto> findGradesByAssignment(@Bind("assignmentId") Long assignmentId);

    // === Upsert grade (skapar submission om saknas; uppdaterar annars) ===
    @SqlUpdate("""
        WITH existing_sub AS (
          SELECT submission_id
          FROM submission
          WHERE assignment_id = :assignmentId AND student_id = :studentId
        ),
        ins_sub AS (
          INSERT INTO submission(assignment_id, student_id, submission_date)
          SELECT :assignmentId, :studentId, now()
          WHERE NOT EXISTS (SELECT 1 FROM existing_sub)
          RETURNING submission_id
        ),
        sub_union AS (
          SELECT submission_id FROM ins_sub
          UNION ALL
          SELECT submission_id FROM existing_sub
        ),
        upd AS (
          UPDATE canvas.grade g
          SET grade = :grade,
              comment = :comment,
              graded_at = COALESCE(:gradedAt::timestamp, now())
          WHERE g.submission_id = (SELECT submission_id FROM sub_union)
          RETURNING grade_id
        )
        INSERT INTO grade(submission_id, grade, comment, graded_at)
        SELECT (SELECT submission_id FROM sub_union),
               :grade,
               :comment,
               COALESCE(:gradedAt::timestamp, now())
        WHERE NOT EXISTS (SELECT 1 FROM upd)
    """)
    void upsertGrade(@Bind("assignmentId") Long assignmentId,
                     @Bind("studentId") String studentId,
                     @Bind("grade") String grade,
                     @Bind("comment") String comment,
                     @Bind("gradedAt") String gradedAtNullable);

    // === Roster utan betyg (alla registrerade studenter i kursen) ===
    // Alias matchar CanvasStudentDto: studentId, name, email
    @SqlQuery("""
        SELECT 
            s.student_id AS studentId,
            (COALESCE(s.first_name,'') || ' ' || COALESCE(s.last_name,'')) AS name,
            s.email AS email
        FROM course c
        JOIN course_registration r ON r.course_id = c.course_id
        JOIN student s            ON s.student_id = r.student_id
        WHERE c.course_code = :courseCode
        ORDER BY s.student_id
    """)
    List<CanvasStudentDto> listStudentsByCourse(@Bind("courseCode") String courseCode);

    // === Roster + ev. betyg för specifik assignment ===
    // Alias matchar CanvasRosterItemDto: studentId, name, email, canvasGrade, gradedAt
    @SqlQuery("""
        SELECT 
            s.student_id AS studentId,
            (COALESCE(s.first_name,'') || ' ' || COALESCE(s.last_name,'')) AS name,
            s.email AS email,
            g.grade AS canvasGrade,
            CASE WHEN g.graded_at IS NULL THEN NULL
                 ELSE to_char(g.graded_at,'YYYY-MM-DD"T"HH24:MI:SS')
            END AS gradedAt
        FROM course c
        JOIN course_registration r  ON r.course_id = c.course_id
        JOIN student s              ON s.student_id = r.student_id
        LEFT JOIN submission sub    ON sub.student_id = s.student_id AND sub.assignment_id = :assignmentId
        LEFT JOIN grade g           ON g.submission_id = sub.submission_id
        WHERE c.course_code = :courseCode
        ORDER BY s.student_id
    """)
    List<CanvasRosterItemDto> listRosterWithAssignment(@Bind("courseCode") String courseCode,
                                                       @Bind("assignmentId") Long assignmentId);
}
