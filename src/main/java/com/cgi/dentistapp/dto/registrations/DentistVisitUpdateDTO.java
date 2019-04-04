package com.cgi.dentistapp.dto.registrations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class DentistVisitUpdateDTO {
    /**
     * database visit id
     */
    private long id;
    @Size(min = 1, max = 50)
    /**
     * dentist name
     */
    private String dentist;
    @NotNull
    /**
     * Do i even need this comments?
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

}
