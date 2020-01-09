package edu.ufp.esof.projecto.controllers;

import edu.ufp.esof.projecto.services.MomentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/momento")
public class MomentoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private MomentoService momentoService;

    @Autowired
    public MomentoController(MomentoService momentoService) {
        this.momentoService = momentoService;
    }
}