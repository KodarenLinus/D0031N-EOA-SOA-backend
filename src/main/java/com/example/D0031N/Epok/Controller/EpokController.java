package com.example.D0031N.Epok.Controller;

import com.example.D0031N.Epok.Dao.EpokDao;
import com.example.D0031N.Epok.Dto.EpokModuleDto;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/epok")
public class EpokController {

    private final Jdbi jdbi;
    public EpokController(@Qualifier("epokJdbi") Jdbi jdbi) {
        this.jdbi = jdbi;
    }
    private EpokDao dao() {
        return jdbi.onDemand(EpokDao.class);
    }

    @GetMapping("/courses/{kurskod}/modules")
    @ResponseStatus(HttpStatus.OK)
    public List<EpokModuleDto> listModules(@PathVariable String kurskod,
                                           @RequestParam(defaultValue = "true") boolean onlyActive) {
        List<EpokModuleDto> dto = dao().listModules(kurskod, onlyActive);
        if (dto == null) throw new org.springframework.web.server.ResponseStatusException(
                HttpStatus.NOT_FOUND, "No active modules for course " + kurskod + " or kurskod is invalid.");
        return dto;
    }
}
