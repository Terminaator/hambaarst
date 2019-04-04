package com.cgi.dentistapp.service;

import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DentistService {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     *
     * @return all dentists if none then null
     */
    public List<DentistEntity> findAllDentists() {
        Query query = entityManager.createQuery("SELECT e FROM DentistEntity e");
        return (List<DentistEntity>) query.getResultList();
    }

    /**
     *
     * @param dentistName
     * @return finds by id
     */
    public DentistEntity findDentist(String dentistName) {
        List<DentistEntity> dentists = (List<DentistEntity>) entityManager.createQuery("select e from DentistEntity e where e.dentistName =:dentis").setParameter("dentis", dentistName).getResultList();
        if (dentists.size() == 0) return null;
        else return dentists.get(0);
    }

}
