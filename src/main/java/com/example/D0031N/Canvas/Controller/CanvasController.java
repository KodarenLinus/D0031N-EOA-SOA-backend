// src/main/java/com/example/D0031N/Canvas/CanvasController.java
package com.example.D0031N.Canvas.Controller;

import com.example.D0031N.Canvas.Dao.CanvasDao;
import com.example.D0031N.Canvas.Dto.*;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* Controller Canvas DB
*
* Recevies/ sends data away/from api
* */
@RestController
@RequestMapping("/canvas")
public class CanvasController {

    private final Jdbi jdbi;

    public CanvasController(@Qualifier("canvasJdbi") Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    private CanvasDao dao() { return jdbi.onDemand(CanvasDao.class); }

    @GetMapping("/courses/{courseCode}/roster")
    public List<CanvasStudentDto> listRoster(@PathVariable String courseCode) {
        return dao().listRoster(courseCode);
    }
}
