package com.example.D0031N.Ladok.Controller;

import com.example.D0031N.Ladok.Dao.LadokDao;
import com.example.D0031N.Ladok.Dto.LadokResultRequestDto;
import com.example.D0031N.Ladok.Dto.LadokResultResponseDto;
import com.example.D0031N.Ladok.Dto.LadokRosterItemDto;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ladok")
@CrossOrigin
public class LadokController {

    private final Jdbi jdbi;

    public LadokController(@Qualifier("ladokJdbi") Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    private LadokDao dao() {
        return jdbi.onDemand(LadokDao.class);
    }

    @GetMapping("/courses/{kurskod}/roster")
    public List<LadokRosterItemDto> roster(
            @PathVariable String kurskod,
            @RequestParam(name = "modul") String modulkod
    ) {
        return dao().rosterByCourseAndModule(kurskod, modulkod);
    }

    // ---- Registrera resultat ----
    @PostMapping("/results")
    @ResponseStatus(HttpStatus.CREATED)
    public LadokResultResponseDto postResult(@RequestBody LadokResultRequestDto body) {
        Long id = dao().insertResultIfNotExists(
                body.personnummer(),
                body.kurskod(),
                body.modulkod(),
                body.datum(),
                body.betyg()
        );

        if (id == null) {
            // redan finns
            return new LadokResultResponseDto(null, "hinder", "Redan registrerad");
        }

        return new LadokResultResponseDto(id, "registrerad", "OK");
    }
}
