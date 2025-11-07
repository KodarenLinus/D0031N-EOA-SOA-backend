// src/main/java/com/example/D0031N/Canvas/CanvasController.java
package com.example.D0031N.Canvas.Controller;

import com.example.D0031N.Canvas.Dao.CanvasDao;
import com.example.D0031N.Canvas.Dto.*;
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

    private CanvasDao dao() { return jdbi.onDemand(CanvasDao.class); }

    // ==== Kurs → Rum ====
    @GetMapping("/courses/{courseCode}/rooms")
    public List<CanvasRoomDto> listRoomsByCourse(@PathVariable String courseCode) {
        return dao().listRoomsByCourse(courseCode);
    }

    // ==== Roster per rum ====
    @GetMapping("/rooms/{roomId}/roster")
    public List<CanvasStudentDto> listStudentsByRoom(@PathVariable Long roomId) {
        return dao().listStudentsByRoom(roomId);
    }

    @GetMapping("/courses/{courseCode}/assignments")
    public List<AssignmentDto> listAssignments(@PathVariable String courseCode) {
        return dao().findAssignmentsByCourse(courseCode);
    }

    // ==== Grades & Submissions ====
    @GetMapping("/assignments/{assignmentId}/grades")
    public List<GradeDto> listGrades(@PathVariable Long assignmentId) {
        return dao().findGradesByAssignment(assignmentId);
    }

    @GetMapping("/courses/{courseCode}/roster")
    public List<CanvasRosterItemDto> listRosterWithAssignment(@PathVariable String courseCode,
                                                              @RequestParam Long assignmentId) {
        return dao().listRosterWithAssignment(courseCode, assignmentId);
    }

    @GetMapping("/assignments/{assignmentId}/submissions")
    public List<SubmissionDto> listSubmissionsByAssignment(@PathVariable Long assignmentId) {
        return dao().listSubmissionsByAssignment(assignmentId);
    }

    // var: @PutMapping("/assignments/{assignmentId}/grades/{studentId}")
    @PutMapping("/assignments/{assignmentId}/grades/{studentId}")
    public GradeDto upsertGrade(@PathVariable Long assignmentId,
                                @PathVariable String studentId,   // <-- String nu
                                @RequestBody GradeUpsertDto body) {

        Long sid = Long.valueOf(studentId); // parse till Long för DB

        dao().upsertGrade(
                assignmentId,
                sid,
                body.grade(),
                body.comment(),   // ignoreras i DAO
                body.gradedAt()
        );

        // Hämta tillbaka som GradeDto(String,...)
        return dao().findGradesByAssignment(assignmentId).stream()
                .filter(g -> g.studentId().equals(studentId))
                .findFirst()
                .orElse(new GradeDto(studentId, body.grade(), body.comment(), body.gradedAt()));
    }
}
