package com.example.D0031N.Ladok;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ladok")
public class LadokController {

    private final Jdbi jdbi;
    public LadokController(@Qualifier("ladokJdbi") Jdbi jdbi) { this.jdbi = jdbi; }
    private LadokDao dao() { return jdbi.onDemand(LadokDao.class); }

    @GetMapping("/courses/{kurskod}/instances")
    public List<LadokCourseInstanceDto> listInstances(@PathVariable String kurskod) {
        return dao().listCourseInstances(kurskod);
    }

    @GetMapping("/courses/{kurskod}/instances/{instans}/roster")
    public List<LadokRosterItemDto> roster(@PathVariable String kurskod, @PathVariable String instans) {
        return dao().rosterByCourseInstance(kurskod, instans);
    }

    @PostMapping("/results")
    @ResponseStatus(HttpStatus.CREATED)
    public LadokResultResponseDto postResult(@RequestBody LadokResultRequestDto body) {
        Long id = dao().insertResultIfNotExists(body.personnummer(), body.kurskod(), body.modulkod(),
                body.datum(), body.betyg());
        if (id == null) {
            // redan finns => 409 semantics, men vi returnerar 200-liknande info i body (lättare i labb)
            return new LadokResultResponseDto(null, "hinder", "Redan registrerad");
        }
        return new LadokResultResponseDto(id, "registrerad", "OK");
    }
}
