package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistDTO;
import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DentistVisitService {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     *
     * @param dentistEntities
     * @return needed for attr - dentistdto dentist with dates
     */
    @SuppressWarnings("unchecked")
    public List<DentistDTO> findAllDates(List<DentistEntity> dentistEntities) {
        List<DentistDTO> dentists = new ArrayList<>();
        for (DentistEntity dentist : dentistEntities) {
            List<Date> list = (List<Date>) entityManager
                    .createQuery("select a.date from DentistVisitEntity a where a.dentist =:dentist")
                    .setParameter("dentist", dentist)
                    .getResultList();
            dentists.add(new DentistDTO(dentist.getDentistName(), list));
        }
        return dentists;
    }

    public List<DentistVisitEntity> findAllVisits() {
        return (List<DentistVisitEntity>) entityManager.createQuery("select a from DentistVisitEntity a").getResultList();
    }

    public void addVisit(DentistEntity dentist, Date visitTime) {
        DentistVisitEntity dentistVisitEntity = new DentistVisitEntity(dentist, visitTime);
        entityManager.persist(dentistVisitEntity);
    }

    /**
     *
     * @param date
     * @param dentist
     * @return checks if date is in the database if it is then true
     */
    public boolean containsDate(Date date, DentistEntity dentist) {
        List list = entityManager
                .createQuery("select a from DentistVisitEntity a where a.dentist =:dentist and a.date =:date")
                .setParameter("dentist", dentist)
                .setParameter("date", date)
                .getResultList();
        return list.size() == 1;
    }

    public DentistVisitEntity findById(long id) {
        return entityManager.find(DentistVisitEntity.class, id);
    }

    /**
     *
     * @param id
     * @return returns true if removed else false
     */
    public boolean remove(long id) {
        DentistVisitEntity visit = findById(id);
        if (visit != null) {
            entityManager.remove(visit);
            return true;
        }
        return false;
    }

    public void update(DentistVisitEntity dentistVisitEntity) {
        entityManager.merge(dentistVisitEntity);
    }
}
