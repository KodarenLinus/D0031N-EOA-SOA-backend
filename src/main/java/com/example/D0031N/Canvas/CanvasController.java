package com.example.D0031N.Canvas;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/canvas")
public class CanvasController {

    private final Jdbi jdbi;

    public CanvasController(@Qualifier("canvasJdbi") Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @GetMapping("/courses/{courseCode}/assignments")
    public List<AssignmentDto> listAssignments(@PathVariable String courseCode) {
        return jdbi.onDemand(CanvasDao.class).findAssignmentsByCourse(courseCode);
    }

    @GetMapping("/assignments/{assignmentId}/grades")
    public List<GradeDto> listGrades(@PathVariable Long assignmentId) {
        return jdbi.onDemand(CanvasDao.class).findGradesByAssignment(assignmentId);
    }

    // Roster (utan assignment)
    @GetMapping("/courses/{courseCode}/students")
    public List<CanvasStudentDto> listStudents(@PathVariable String courseCode) {
        return jdbi.onDemand(CanvasDao.class).listStudentsByCourse(courseCode);
    }

    // Roster + Canvas-betyg för given assignment (om du vill se omdömet direkt)
    @GetMapping("/courses/{courseCode}/roster")
    public List<CanvasRosterItemDto> listRosterWithAssignment(@PathVariable String courseCode,
                                                              @RequestParam(required = false) Long assignmentId) {
        if (assignmentId == null) {
            // om assignmentId saknas: bygg roster med null betyg
            return jdbi.onDemand(CanvasDao.class).listStudentsByCourse(courseCode).stream()
                    .map(s -> new CanvasRosterItemDto(s.studentId(), s.name(), s.email(), null, null))
                    .toList();
        }
        return jdbi.onDemand(CanvasDao.class).listRosterWithAssignment(courseCode, assignmentId);
    }

    // Rättning (skapa/uppdatera betyg) – lärarflödet
    @PutMapping("/assignments/{assignmentId}/grades/{studentId}")
    public GradeDto upsertGrade(@PathVariable Long assignmentId,
                                @PathVariable String studentId,
                                @RequestBody GradeUpsertDto body) {
        jdbi.onDemand(CanvasDao.class)
                .upsertGrade(assignmentId, studentId, body.grade(), body.comment(), body.gradedAt());

        return jdbi.onDemand(CanvasDao.class)
                .findGradesByAssignment(assignmentId)
                .stream()
                .filter(g -> g.studentId().equals(studentId))
                .findFirst()
                .orElseGet(() -> new GradeDto(studentId, body.grade(), body.comment(), body.gradedAt()));
    }
}
