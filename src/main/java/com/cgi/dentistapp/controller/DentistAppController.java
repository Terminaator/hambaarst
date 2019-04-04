package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.*;
import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.service.DentistService;
import com.cgi.dentistapp.service.DentistVisitService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    private final DentistVisitService dentistVisitService;
    private final DentistService dentistService;

    @Autowired
    public DentistAppController(DentistVisitService dentistVisitService, DentistService dentistService) {
        this.dentistVisitService = dentistVisitService;
        this.dentistService = dentistService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    /**
     * adds dentistVisitDTO attribute
     * @return needed for form post
     */
    @ModelAttribute("dentistVisitDTO")
    public DentistVisitDTO dentistVisitDTO() {
        return new DentistVisitDTO();
    }
    /**
     * adds dentist attribute
     * @return dentist with name and booked times
     */
    @ModelAttribute("dentists")
    public List<DentistDTO> dentistDTOS() {
        return dentistVisitService.findAllDates(dentistService.findAllDentists());
    }
    /**
     * Shows registering form
     *
     *
     * @return registering page
     */
    @GetMapping("/")
    public String showRegisterForm() {
        return "registrationForm";
    }

    /**
     * Adds new registration into database if successful
     *
     * @param model last page data
     * @param dentistVisitDTO form data
     * @param bindingResult checks if data is correct
     * @return  if failed then "form" else "redirect:results"
     */
    @PostMapping("/")
    public String postRegisterForm(Model model, @Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult) {
        DentistEntity dentist = dentistService.findDentist(dentistVisitDTO.getDentistName());
        if (bindingResult.hasErrors()) {
            return "registrationForm";
        } else if (dentist == null) {
            model.addAttribute("nameError", true);
            return "registrationForm";
        } else if (dentistVisitService.containsDate(dentistVisitDTO.getVisitTime(), dentist)) {
            model.addAttribute("dateError", true);
            return "registrationForm";
        }

        dentistVisitService.addVisit(dentist, dentistVisitDTO.getVisitTime());
        return "redirect:/results";
    }
}
