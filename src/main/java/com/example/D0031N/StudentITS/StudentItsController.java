package com.example.D0031N.StudentITS;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentits")
public class StudentItsController {

    private final Jdbi jdbi;

    public StudentItsController(@Qualifier("studentJdbi") Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @GetMapping("/users/{username}/personnummer")
    public ResponseEntity<?> getPersonnummer(@PathVariable String username) {
        String pnr = jdbi.onDemand(StudentDao.class)
                .findPersonnummerByStudentId(username)
                .orElse(null);

        if (pnr == null || pnr.isBlank()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PersonnummerDto(username, pnr));
    }
}
