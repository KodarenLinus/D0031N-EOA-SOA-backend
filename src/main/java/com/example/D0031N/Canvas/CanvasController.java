// com/example/D0031N/Canvas/CanvasController.java
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

    @GetMapping("/courses/{kurskod}/assignments")
    public List<AssignmentDto> listAssignments(@PathVariable String kurskod) {
        return jdbi.onDemand(CanvasDao.class).findAssignmentsByCourse(kurskod);
    }

    @GetMapping("/assignments/{assignmentId}/grades")
    public List<GradeDto> listGrades(@PathVariable Long assignmentId) {
        return jdbi.onDemand(CanvasDao.class).findGradesByAssignment(assignmentId);
    }

    // Roster (utan assignment)
    @GetMapping("/courses/{kurskod}/students")
    public List<CanvasStudentDto> listStudents(@PathVariable String kurskod) {
        return jdbi.onDemand(CanvasDao.class).listStudentsByCourse(kurskod);
    }

    // Roster + Canvas-betyg för given assignment (om du vill se omdömet direkt)
    @GetMapping("/courses/{kurskod}/roster")
    public List<CanvasRosterItemDto> listRosterWithAssignment(@PathVariable String kurskod,
                                                              @RequestParam(required = false) Long assignmentId) {
        if (assignmentId == null) {
            // om assignmentId saknas: bygg roster med null betyg
            return jdbi.onDemand(CanvasDao.class).listStudentsByCourse(kurskod).stream()
                    .map(s -> new CanvasRosterItemDto(s.getStudentId(), s.getName(), s.getEmail(), null, null))
                    .toList();
        }
        return jdbi.onDemand(CanvasDao.class).listRosterWithAssignment(kurskod, assignmentId);
    }

    // Rättning (skapa/uppdatera betyg) – lärarflödet (låt stå)
    @PutMapping("/assignments/{assignmentId}/grades/{studentId}")
    public GradeDto upsertGrade(@PathVariable Long assignmentId,
                                @PathVariable String studentId,
                                @RequestBody GradeUpsertDto body) {
        jdbi.onDemand(CanvasDao.class)
                .upsertGrade(assignmentId, studentId, body.getGrade(), body.getComment(), body.getGradedAt());

        return jdbi.onDemand(CanvasDao.class)
                .findGradesByAssignment(assignmentId)
                .stream()
                .filter(g -> g.getStudentId().equals(studentId))
                .findFirst()
                .orElseGet(() -> new GradeDto(studentId, body.getGrade(), body.getComment(), body.getGradedAt()));
    }
}
