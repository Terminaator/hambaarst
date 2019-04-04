package com.cgi.dentistapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DentistDTO {
    private String dentistName;
    private List<Date> dates;

    public DentistDTO(String dentistName, List<Date> list) {
        this.dentistName = dentistName;
        this.dates = list;
    }
}
