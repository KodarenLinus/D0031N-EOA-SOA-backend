package com.example.D0031N.Epok;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/epok")
public class EpokController {

    private final Jdbi jdbi;
    public EpokController(@Qualifier("epokJdbi") Jdbi jdbi) { this.jdbi = jdbi; }
    private EpokDao dao() { return jdbi.onDemand(EpokDao.class); }

    @GetMapping("/courses/{kurskod}/modules")
    public List<EpokModuleDto> listModules(@PathVariable String kurskod,
                                           @RequestParam(defaultValue = "true") boolean onlyActive) {
        return dao().listModules(kurskod, onlyActive);
    }
}
