package com.ehealth.doctors.model.converter;

import com.ehealth.doctors.model.dto.DoctorClinicDTO;
import com.ehealth.doctors.model.dto.DoctorDTO;
import com.ehealth.doctors.model.entity.ClinicDoctorBinding;
import com.ehealth.doctors.model.entity.Doctor;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DoctorDoctorDtoMapper extends CustomMapper<Doctor, DoctorDTO> {
    @Autowired
    private OrikaBeanMapper mapper;

    @Override
    public void mapAtoB(Doctor doctor, DoctorDTO doctorDTO, MappingContext context) {

        //clinics to doctor
        final Set<DoctorClinicDTO> clinics = doctor.getClinics()
                .stream()
                .map(ClinicDoctorBinding::getClinic)
                .map(clinic -> mapper.map(clinic, DoctorClinicDTO.class))
                .collect(Collectors.toSet());

        doctorDTO.setClinics(clinics);

        super.mapAtoB(doctor, doctorDTO, context);
    }
}
