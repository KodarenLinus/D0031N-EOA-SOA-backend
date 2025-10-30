package com.example.D0031N.Ladok;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import jakarta.validation.Valid;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ladok")
public class LadokController {
    private final Jdbi jdbi;
    public LadokController(@Qualifier("ladokJdbi") Jdbi jdbi) { this.jdbi = jdbi; }

    @PostMapping("/results")
    public ResponseEntity<ResultResponse> register(@Valid @RequestBody ResultRequest req) {
        try {
            LocalDate date = LocalDate.parse(req.getDatum());
            int rows = jdbi.onDemand(ResultDao.class)
                    .insert(req.getPersonnummer(), req.getKurskod(), req.getModul(), date, req.getBetyg(), "registrerad");
            if (rows == 1)
                return ResponseEntity.ok(new ResultResponse("registrerad", "Resultat registrerat"));
            return ResponseEntity.internalServerError().body(new ResultResponse("fel", "Inget sparat"));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(new ResultResponse("fel", "Ogiltigt datum"));
        }
    }
}