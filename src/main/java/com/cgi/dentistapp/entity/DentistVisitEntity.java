package com.cgi.dentistapp.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date;

    @ManyToOne
    private DentistEntity dentist;
    public DentistVisitEntity(){

    }
    public DentistVisitEntity(DentistEntity dentist, Date visitTime) {
        this.dentist = dentist;
        this.date = visitTime;
    }

    //TODO implementation

}
