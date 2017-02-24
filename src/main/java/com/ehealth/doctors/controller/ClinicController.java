package com.ehealth.doctors.controller;

import com.ehealth.doctors.model.dto.ClinicDTO;
import com.ehealth.doctors.model.dto.DoctorDTO;
import com.ehealth.doctors.model.entity.Clinic;
import com.ehealth.doctors.model.entity.ClinicDoctorBinding;
import com.ehealth.doctors.model.entity.Doctor;
import com.ehealth.doctors.service.ClinicService;
import io.swagger.annotations.Api;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clinic")
@ResponseBody
@Api(description = "Клініки")
public class ClinicController {

    private final ClinicService clinicService;

    private final MapperFacade mapper;

    @Autowired
    public ClinicController(ClinicService clinicService, MapperFacade mapper) {
        this.clinicService = clinicService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/{id}")
    public ClinicDTO get(@PathVariable UUID id) {
        Clinic clinic = clinicService.getBy(id);

        return mapper.map(clinic, ClinicDTO.class);
    }

    @GetMapping
    public List<ClinicDTO> list() {
        Iterable<Clinic> list = clinicService.list();

        return mapper.mapAsList(list, ClinicDTO.class);
    }

    @PostMapping
    public ClinicDTO create(@RequestBody ClinicDTO clinicDTO) throws Exception {
        if (clinicDTO.getId() != null) throw new Exception();

        final Clinic clinic = mapper.map(clinicDTO, Clinic.class);

        clinicService.save(clinic);

        return mapper.map(clinic, ClinicDTO.class);
    }

    @PutMapping
    public ClinicDTO update(@RequestBody ClinicDTO clinicDTO) {
        final Clinic clinic = mapper.map(clinicDTO, Clinic.class);

        clinicService.save(clinic);

        return mapper.map(clinic, ClinicDTO.class);
    }

    @GetMapping(value = "/{id}/doctors")
    public List<DoctorDTO> listDoctors(@PathVariable UUID id) {
        Clinic clinic = clinicService.getBy(id);

        final List<Doctor> collect = clinic.getDoctors().stream().map(ClinicDoctorBinding::getDoctor).collect(Collectors.toList());

        return mapper.mapAsList(collect, DoctorDTO.class);
    }
}