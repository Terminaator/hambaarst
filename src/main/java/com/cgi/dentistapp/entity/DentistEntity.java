package com.cgi.dentistapp.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dentist")
public class DentistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String dentistName;

    public DentistEntity(){

    }
    public DentistEntity(String dentistName){
        this.dentistName = dentistName;
    }
}
