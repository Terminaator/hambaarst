package com.cgi.dentistapp.dto.registrations;

import com.cgi.dentistapp.entity.DentistEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationsDTO {
    private Long id;
    private DentistEntity dentist;
    private String date;
    public RegistrationsDTO(Long id, DentistEntity dentist, String date){
        this.id = id;
        this.dentist = dentist;
        this.date = date;
    }
}
