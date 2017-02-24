package com.ehealth.doctors.service;

import com.ehealth.doctors.dao.IDoctorDAO;
import com.ehealth.doctors.model.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by vilyam on 17.02.17.
 */
@Service
public class DoctorService {

    @Autowired
    private IDoctorDAO iDoctorDAO;

    public Doctor getBy(UUID id) {
        return iDoctorDAO.findOne(id);
    }

    public void delete(UUID id) {
        iDoctorDAO.delete(id);
    }

    public Iterable<Doctor> list() {
        return iDoctorDAO.findAll();
    }

    public void save(Doctor doc) {
        iDoctorDAO.save(doc);
    }

    public void create(Doctor doc) {
        Date date = new Date();
        doc.setDateCreated(date);
        iDoctorDAO.save(doc);
    }
}
