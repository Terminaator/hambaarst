package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistDTO;
import com.cgi.dentistapp.dto.registrations.DentistVisitRemoveDTO;
import com.cgi.dentistapp.dto.registrations.DentistVisitUpdateDTO;
import com.cgi.dentistapp.dto.registrations.RegistrationsDTO;
import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.service.DentistService;
import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class DentistRegistrationsController extends WebMvcConfigurerAdapter {
    private final DentistVisitService dentistVisitService;
    private final DentistService dentistService;

    @Autowired
    public DentistRegistrationsController(DentistVisitService dentistVisitService, DentistService dentistService) {
        this.dentistVisitService = dentistVisitService;
        this.dentistService = dentistService;
    }

    /**
     *
     * @return form data
     */
    @ModelAttribute("dentistVisitRemoveDTO")
    public DentistVisitRemoveDTO dentistVisitRemoveDTO() {
        return new DentistVisitRemoveDTO();
    }

    /**
     *
     * @return form data
     */
    @ModelAttribute("dentistVisitUpdateDTO")
    public DentistVisitUpdateDTO dentistVisitUpdateDTO() {
        return new DentistVisitUpdateDTO();
    }

    /**
     *
     * @return form data
     */
    @ModelAttribute("dentists")
    public List<DentistDTO> dentistDTOS() {
        return dentistVisitService.findAllDates(dentistService.findAllDentists());
    }

    /**
     * attribute
     * @return sends RegistrationsDTO to the page
     */
    @ModelAttribute("registrations")
    public List<RegistrationsDTO> dentistVisitEntities() {
        List<RegistrationsDTO> registrations = new ArrayList<>();
        for (DentistVisitEntity dentistVisitEntity : dentistVisitService.findAllVisits()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(dentistVisitEntity.getDate());
            registrations.add(new RegistrationsDTO(dentistVisitEntity.getId(), dentistVisitEntity.getDentist(), date));
        }
        return registrations;
    }

    @GetMapping("/registrations")
    public String showRegistrations() {
        return "registrations";
    }

    /**
     * updates visit value
     * @param model
     * @param dentistVisitUpdateDTO
     * @param bindingResult
     * @return returns table if error then with the error attr
     */
    @PostMapping("/update")
    public String updateRemoveForm(Model model, DentistVisitUpdateDTO dentistVisitUpdateDTO, BindingResult bindingResult) {
        DentistVisitEntity dentistVisitEntity = dentistVisitService.findById(dentistVisitUpdateDTO.getId());
        DentistEntity dentistEntity = dentistService.findDentist(dentistVisitUpdateDTO.getDentist());
        if (dentistVisitEntity == null || dentistEntity == null || bindingResult.hasErrors()) {
            model.addAttribute("error", true);
            return "registrations";
        } else if (dentistEntity == dentistVisitEntity.getDentist()) {
            if (dentistVisitEntity.getDate() == dentistVisitUpdateDTO.getDate()) {
                return "registrations";
            } else if (dentistVisitService.containsDate(dentistVisitUpdateDTO.getDate(), dentistEntity)) {
                model.addAttribute("error", true);
                return "registrations";
            }
            dentistVisitEntity.setDate(dentistVisitUpdateDTO.getDate());
            dentistVisitService.update(dentistVisitEntity);
        } else if (dentistEntity != dentistVisitEntity.getDentist()) {
            if (dentistVisitService.containsDate(dentistVisitUpdateDTO.getDate(), dentistEntity)) {
                model.addAttribute("error", true);
                return "registrations";
            }
            dentistVisitEntity.setDate(dentistVisitUpdateDTO.getDate());
            dentistVisitEntity.setDentist(dentistEntity);
            dentistVisitService.update(dentistVisitEntity);
        } else {
            model.addAttribute("error", true);
            return "registrations";
        }
        model.addAttribute("registrations", dentistVisitEntities());
        return "registrations";
    }

    /**
     * removes from database visit reg.
     * @param model page data
     * @param dentistVisitRemoveDTO form data
     * @param bindingResult data controll
     * @return returns table if error then with the error attr.
     */
    @PostMapping("/remove")
    public String postRemoveForm(Model model, DentistVisitRemoveDTO dentistVisitRemoveDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || !dentistVisitService.remove(dentistVisitRemoveDTO.getId())) {
            model.addAttribute("error", true);
        }
        model.addAttribute("registrations", dentistVisitEntities());
        return "registrations";
    }
}
