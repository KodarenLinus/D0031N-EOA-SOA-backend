package com.example.D0031N.ws;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/ws")
public class WSController {
    private final Jdbi epok;
    private final Jdbi student;
    private final Jdbi ladok;

    public WSController(@Qualifier("epokJdbi") Jdbi epok,
                        @Qualifier("studentJdbi") Jdbi student,
                        @Qualifier("ladokJdbi") Jdbi ladok) {
        this.epok = epok;
        this.student = student;
        this.ladok = ladok;
    }

    // DTOs
    public record ListReq(String kurskod, String modulKod, String assignmentId) {}
    public record StudentItem(String username, String personnummer, String betyg, String datum, String status) {}
    public record ListResp(List<StudentItem> students, Map<String,Object> meta) {}

    @PostMapping("/list-students")
    public ResponseEntity<ListResp> listStudents(@RequestBody ListReq req) {
        // 1. Kontrollera modul finns i Epok
        boolean valid = epok.withHandle(h ->
                h.createQuery("SELECT count(*) FROM course_modules WHERE kurskod=:k AND modul_kod=:m AND aktiv=true")
                        .bind("k", req.kurskod()).bind("m", req.modulKod()).mapTo(int.class).one() > 0
        );
        if (!valid) return ResponseEntity.badRequest().build();

        // 2. Hämta studenter från StudentITS
        var students = student.withHandle(h ->
                h.createQuery("SELECT username, personnummer FROM students ORDER BY username")
                        .map((r, ctx) -> new StudentItem(r.getString("username"), r.getString("personnummer"),
                                "A", null, "VALBAR"))
                        .list()
        );

        // 3. Returnera svar
        return ResponseEntity.ok(new ListResp(students, Map.of("kurskod", req.kurskod(), "modulKod", req.modulKod())));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String,Object>> transfer(@RequestBody Map<String,Object> body) {
        var kurskod = (String) body.get("kurskod");
        var modul = (String) body.get("modulKod");
        var mode = (String) body.getOrDefault("mode", "UTKAST");

        int count = 0;
        try {
            var items = (List<Map<String,Object>>) body.get("items");
            for (var item : items) {
                var pnr = (String) item.get("personnummer");
                var betyg = (String) item.get("betyg");
                var datum = (String) item.get("datum");
                ladok.useHandle(h -> h.createUpdate("""
                    INSERT INTO results(personnummer, kurskod, modul, datum, betyg, status)
                    VALUES (:pnr, :kurs, :modul, :datum, :betyg, :status)
                    """)
                        .bind("pnr", pnr)
                        .bind("kurs", kurskod)
                        .bind("modul", modul)
                        .bind("datum", datum)
                        .bind("betyg", betyg)
                        .bind("status", mode.equalsIgnoreCase("KLARMARKERA") ? "klarmarkerad" : "utkast")
                        .execute());
                count++;
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
        return ResponseEntity.ok(Map.of("ok", true, "saved", count));
    }
}
