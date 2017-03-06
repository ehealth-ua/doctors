package com.ehealth.doctors.service;

import com.ehealth.doctors.dao.IClinicDAO;
import com.ehealth.doctors.dao.IClinicDoctorBindingDAO;
import com.ehealth.doctors.dao.IDoctorDAO;
import com.ehealth.doctors.exception.DataDuplicatedException;
import com.ehealth.doctors.exception.DataNotAvailableException;
import com.ehealth.doctors.model.entity.Clinic;
import com.ehealth.doctors.model.entity.ClinicDoctorBinding;
import com.ehealth.doctors.model.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by vilyam on 17.02.17.
 */
@Service
public class ClinicService {
    @Autowired
    private IClinicDAO iClinicDAO;
    @Autowired
    private IDoctorDAO iDoctorDAO;
    @Autowired
    private IClinicDoctorBindingDAO iClinicDoctorBindingDAO;

    public Clinic getBy(UUID id) {
        return iClinicDAO.findOne(id);
    }

    public Iterable<Clinic> list() {
        return iClinicDAO.findAll();
    }

    public void save(Clinic clinic) {
        iClinicDAO.save(clinic);
    }

    public List<Doctor> addDoctor(final Clinic clinic, final Doctor doctor) throws DataDuplicatedException {
        final Set<ClinicDoctorBinding> doctors = clinic.getDoctors();

        final boolean contains = doctors.stream().anyMatch(cdb -> cdb.getDoctor().getId().equals(doctor.getId()));
        if (contains) throw new DataDuplicatedException();

        ClinicDoctorBinding clinicDoctorBinding = new ClinicDoctorBinding();
        clinicDoctorBinding.setClinic(clinic);
        clinicDoctorBinding.setDoctor(doctor);

        doctors.add(clinicDoctorBinding);

        iClinicDAO.save(clinic);

        return doctors.stream().map(ClinicDoctorBinding::getDoctor).collect(Collectors.toList());
    }

    public List<Doctor> removeDoctor(final Clinic clinic, final Doctor doctor) throws DataNotAvailableException {
        final Set<ClinicDoctorBinding> doctors = clinic.getDoctors();

        ClinicDoctorBinding clinicDoctorBinding = doctors.stream()
                .filter(cdb -> cdb.getDoctor().getId().equals(doctor.getId()))
                .findFirst()
                .orElseThrow(DataNotAvailableException::new);

        clinic.getDoctors().remove(clinicDoctorBinding);
        doctor.getClinics().remove(clinicDoctorBinding);

        iClinicDoctorBindingDAO.delete(clinicDoctorBinding.getPk());

        return doctors.stream().map(ClinicDoctorBinding::getDoctor).collect(Collectors.toList());
    }

    public void create(Clinic clinic) {
        iClinicDAO.save(clinic);
    }
}
