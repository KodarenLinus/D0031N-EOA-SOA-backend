package com.example.D0031N;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StatusController {

    private final Jdbi epok, student, ladok;

    public StatusController(@Qualifier("epokJdbi") Jdbi epok,
                            @Qualifier("studentJdbi") Jdbi student,
                            @Qualifier("ladokJdbi") Jdbi ladok) {
        this.epok = epok;
        this.student = student;
        this.ladok = ladok;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("server", "✅ Spring Boot running on port 8080");

        try {
            status.put("epok", epok.withHandle(h ->
                    h.createQuery("SELECT version()").mapTo(String.class).one()));
        } catch (Exception e) {
            status.put("epok", "❌ " + e.getMessage());
        }

        try {
            status.put("studentits", student.withHandle(h ->
                    h.createQuery("SELECT version()").mapTo(String.class).one()));
        } catch (Exception e) {
            status.put("studentits", "❌ " + e.getMessage());
        }

        try {
            status.put("ladok", ladok.withHandle(h ->
                    h.createQuery("SELECT version()").mapTo(String.class).one()));
        } catch (Exception e) {
            status.put("ladok", "❌ " + e.getMessage());
        }

        return ResponseEntity.ok(status);
    }
}