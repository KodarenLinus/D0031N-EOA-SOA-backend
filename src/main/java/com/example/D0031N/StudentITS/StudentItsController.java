package com.example.D0031N.StudentITS;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/its")
public class StudentItsController {

    private final Jdbi jdbi;
    public StudentItsController(@Qualifier("studentJdbi") Jdbi jdbi) { this.jdbi = jdbi; }
    private StudentItsDao dao() { return jdbi.onDemand(StudentItsDao.class); }

    @GetMapping("/personnummer")
    @ResponseStatus(HttpStatus.OK)
    public ItsPersonLookupDto personnummer(@RequestParam String anvandarnamn) {
        ItsPersonLookupDto dto = dao().findByUsername(anvandarnamn);
        if (dto == null) throw new org.springframework.web.server.ResponseStatusException(
                HttpStatus.NOT_FOUND, "Okänt användarnamn");
        return dto;
    }
}
