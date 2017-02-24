package com.ehealth.doctors.controller;

import com.ehealth.doctors.exception.ValidationFormatException;
import com.ehealth.doctors.model.dto.DoctorContactCardDTO;
import com.ehealth.doctors.model.dto.DoctorDTO;
import com.ehealth.doctors.model.entity.Doctor;
import com.ehealth.doctors.model.entity.DoctorContactCard;
import com.ehealth.doctors.service.DoctorService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/doctor", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Validated
public class DoctorController {

    private final DoctorService doctorService;

    private final MapperFacade mapper;

    @Autowired
    public DoctorController(MapperFacade mapper, DoctorService doctorService) {
        this.mapper = mapper;
        this.doctorService = doctorService;
    }

    @GetMapping(value = "/{id}")
    public DoctorDTO get(@PathVariable UUID id) {
        Doctor doctor = doctorService.getBy(id);

        return mapper.map(doctor, DoctorDTO.class);
    }

    @GetMapping
    public List<DoctorDTO> list() {
        Iterable<Doctor> doctors = doctorService.list();

        return mapper.mapAsList(doctors, DoctorDTO.class);
    }

    @PostMapping
    public DoctorDTO create(@Valid @RequestBody DoctorDTO doctorDto, BindingResult bindingResult) throws Exception {
        if (doctorDto.getId() != null) throw new ValidationFormatException("id", "должно быть пустое");
        if (bindingResult.hasErrors()) throw new ValidationFormatException(bindingResult);

        final Doctor doctor = mapper.map(doctorDto, Doctor.class);

        doctorService.save(doctor);

        return mapper.map(doctor, DoctorDTO.class);
    }

    @PutMapping
    public DoctorDTO update(@Valid @RequestBody DoctorDTO doctorDto, BindingResult bindingResult) throws Exception {
        if (doctorDto.getId() == null) throw new ValidationFormatException("id", "должно быть не пустое");
        if (bindingResult.hasErrors()) throw new ValidationFormatException(bindingResult);

        final Doctor doctor = mapper.map(doctorDto, Doctor.class);

        doctorService.save(doctor);

        return mapper.map(doctor, DoctorDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        doctorService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/contacts")
    public List<DoctorContactCardDTO> getContacts(@PathVariable UUID id) {
        Doctor doctor = doctorService.getBy(id);

        return mapper.mapAsList(doctor.getContacts(), DoctorContactCardDTO.class);
    }

    @PostMapping(value = "/{id}/contacts")
    public DoctorContactCardDTO createContacts(@PathVariable UUID id, @RequestBody DoctorContactCardDTO contactCardDTO) throws Exception {
        if (contactCardDTO.getId() != null) throw new Exception();

        final DoctorContactCard contactCard = mapper.map(contactCardDTO, DoctorContactCard.class);
        final Doctor doctor = doctorService.getBy(id);
        doctor.addContact(contactCard);

        doctorService.save(doctor);

        return mapper.map(contactCard, DoctorContactCardDTO.class);
    }

    @PutMapping(value = "/{id}/contacts")
    public DoctorContactCardDTO updateContacts(@PathVariable UUID id, @RequestBody DoctorContactCardDTO contactCardDTO) throws Exception {
        final DoctorContactCard contactCard = mapper.map(contactCardDTO, DoctorContactCard.class);
        final Doctor doctor = doctorService.getBy(id);
        doctor.addContact(contactCard);

        doctorService.save(doctor);

        return mapper.map(contactCard, DoctorContactCardDTO.class);
    }

    @DeleteMapping(value = "/{id}/contacts/{contactId}")
    public ResponseEntity deleteContacts(@PathVariable UUID id, @PathVariable UUID contactId) throws Exception {
        final Doctor doctor = doctorService.getBy(id);

        doctor.getContacts().removeIf(con -> con.getId().equals(contactId));

        doctorService.save(doctor);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}