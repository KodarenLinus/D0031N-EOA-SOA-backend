// src/main/java/com/example/D0031N/Canvas/CanvasDao.java
package com.example.D0031N.Canvas.Dao;

import com.example.D0031N.Canvas.Dto.*;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterConstructorMapper(AssignmentDto.class)
@RegisterConstructorMapper(GradeDto.class)
@RegisterConstructorMapper(CanvasStudentDto.class)
@RegisterConstructorMapper(CanvasRosterItemDto.class)
@RegisterConstructorMapper(CanvasRoomDto.class)
@RegisterConstructorMapper(SubmissionDto.class)
public interface CanvasDao {

    // ===== Kurs → Rum =====
    @SqlQuery("""
        SELECT 
            r.id         AS id,
            c.kurskod    AS courseCode,
            r.instanskod AS instanceCode
        FROM canvas_room r
        JOIN canvas_course c ON c.id = r.course_id
        WHERE c.kurskod = :courseCode
        ORDER BY r.id
    """)
    List<CanvasRoomDto> listRoomsByCourse(@Bind("courseCode") String courseCode);

    // ===== Roster per rum =====
    @SqlQuery("""
        SELECT 
            s.anvandarnamn AS studentId,                         -- ändrat
            (COALESCE(s.fornamn,'') || ' ' || COALESCE(s.efternamn,'')) AS name,
            NULL::varchar AS email
        FROM canvas_room_enrollment e
        JOIN canvas_student s ON s.id = e.student_id
        WHERE e.room_id = :roomId AND e.status = 'ACTIVE'
        ORDER BY s.id
    """)
    List<CanvasStudentDto> listStudentsByRoom(@Bind("roomId") Long roomId);

    // ===== Assignments per kurs (alla moduler) =====
    @SqlQuery("""
        SELECT 
            a.id    AS id,
            a.name  AS name,
            NULL    AS scaleHint,
            a.type  AS type
        FROM canvas_assignment a
        JOIN canvas_module m ON a.module_id = m.id
        JOIN canvas_course c ON m.course_id = c.id
        WHERE c.kurskod = :courseCode
        ORDER BY a.id
    """)
    List<AssignmentDto> findAssignmentsByCourse(@Bind("courseCode") String courseCode);

    // ===== Grades per assignment =====
    @SqlQuery("""
            SELECT 
                CAST(sub.student_id AS varchar)                AS studentId,
                g.grade                                        AS grade,
                NULL                                           AS comment,
                to_char(g.graded_at,'YYYY-MM-DD"T"HH24:MI:SS') AS gradedAt
            FROM canvas_grade g
            JOIN canvas_submission sub ON sub.id = g.submission_id
            WHERE sub.assignment_id = :assignmentId
            ORDER BY sub.student_id
        """)
    List<GradeDto> findGradesByAssignment(@Bind("assignmentId") Long assignmentId);

    @SqlQuery("""
        SELECT 
            s.anvandarnamn AS studentId,                         -- ändrat
            (COALESCE(s.fornamn,'') || ' ' || COALESCE(s.efternamn,'')) AS name,
            NULL::varchar AS email,
            g.grade AS canvasGrade,
            CASE WHEN g.graded_at IS NULL THEN NULL
                 ELSE to_char(g.graded_at,'YYYY-MM-DD"T"HH24:MI:SS')
            END AS gradedAt
        FROM canvas_course c
        JOIN canvas_module m           ON m.course_id = c.id
        JOIN canvas_assignment a       ON a.module_id = m.id AND a.id = :assignmentId
        JOIN canvas_room r             ON r.course_id = c.id
        JOIN canvas_room_enrollment e  ON e.room_id = r.id AND e.status = 'ACTIVE'
        JOIN canvas_student s          ON s.id = e.student_id
        LEFT JOIN canvas_submission sub ON sub.student_id = s.id AND sub.assignment_id = a.id
        LEFT JOIN canvas_grade g        ON g.submission_id = sub.id
        WHERE c.kurskod = :courseCode
        ORDER BY s.id
    """)
    List<CanvasRosterItemDto> listRosterWithAssignment(@Bind("courseCode") String courseCode,
                                                       @Bind("assignmentId") Long assignmentId);
    // ===== Submissions per assignment =====
    @SqlQuery("""
        SELECT 
            sub.id AS submissionId,
            sub.student_id AS studentId,
            sub.status AS status,
            to_char(sub.submitted_at,'YYYY-MM-DD"T"HH24:MI:SS') AS submittedAt
        FROM canvas_submission sub
        WHERE sub.assignment_id = :assignmentId
        ORDER BY sub.student_id
    """)
    List<SubmissionDto> listSubmissionsByAssignment(@Bind("assignmentId") Long assignmentId);

    // ===== Upsert Grade (skapa submission om saknas, annars uppdatera) =====
    @SqlUpdate("""
        WITH existing_sub AS (
          SELECT id AS submission_id
          FROM canvas_submission
          WHERE assignment_id = :assignmentId AND student_id = :studentId
        ),
        ins_sub AS (
          INSERT INTO canvas_submission(assignment_id, student_id, submitted_at, status)
          SELECT :assignmentId, :studentId, now(), 'SUBMITTED'
          WHERE NOT EXISTS (SELECT 1 FROM existing_sub)
          RETURNING id AS submission_id
        ),
        sub_union AS (
          SELECT submission_id FROM ins_sub
          UNION ALL
          SELECT submission_id FROM existing_sub
        ),
        upd AS (
          UPDATE canvas_grade g
          SET grade     = :grade,
              graded_at = COALESCE(:gradedAt::timestamp, now())
          WHERE g.submission_id = (SELECT submission_id FROM sub_union)
          RETURNING id
        )
        INSERT INTO canvas_grade(submission_id, grade, graded_at)
        SELECT (SELECT submission_id FROM sub_union),
               :grade,
               COALESCE(:gradedAt::timestamp, now())
        WHERE NOT EXISTS (SELECT 1 FROM upd)
    """)
    void upsertGrade(@Bind("assignmentId") Long assignmentId,
                     @Bind("studentId") Long studentId,
                     @Bind("grade") String grade,
                     @Bind("comment") String commentIgnored,
                     @Bind("gradedAt") String gradedAtNullable);

    // (Valfritt) markera betyg som rapporterat till Ladok
    @SqlUpdate("""
        UPDATE canvas_grade
        SET reported_to_ladok_at = COALESCE(:reportedAt::timestamp, now()),
            ladok_status = :ladokStatus,
            ladok_ref_id = :ladokRefId
        WHERE id = :gradeId
    """)
    int markGradeReported(@Bind("gradeId") Long gradeId,
                          @Bind("ladokStatus") String ladokStatus,
                          @Bind("reportedAt") String reportedAtNullable,
                          @Bind("ladokRefId") Long ladokRefId);
}
