package com.example.D0031N.EPOK;

import java.util.List;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
*
* This it the rest controller for epok
* In this controller we expose the diffrent services.
*
* */
@RestController
@RequestMapping("/epok")
public class EpokController {
    private final Jdbi jdbi;

    public EpokController(@Qualifier("epokJdbi") Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @GetMapping("/courses/{kurskod}/modules")
    public ResponseEntity<List<ModuleDto>> getModules(@PathVariable String kurskod) {
        var modules = jdbi.onDemand(EpokDao.class).findActiveModules(kurskod);
        return ResponseEntity.ok(modules);
    }
}