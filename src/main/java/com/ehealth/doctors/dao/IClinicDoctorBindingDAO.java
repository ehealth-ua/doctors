package com.ehealth.doctors.dao;

import com.ehealth.doctors.model.entity.ClinicDoctorBinding;
import com.ehealth.doctors.model.entity.ClinicDoctorBindingId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClinicDoctorBindingDAO extends CrudRepository<ClinicDoctorBinding, ClinicDoctorBindingId> {

}