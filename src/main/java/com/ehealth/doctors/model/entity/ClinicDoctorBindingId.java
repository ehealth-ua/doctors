package com.ehealth.doctors.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by vilyam on 17.02.17.
 */
@Embeddable
public class ClinicDoctorBindingId implements java.io.Serializable {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Doctor doctor;

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClinicDoctorBindingId)) return false;

        ClinicDoctorBindingId that = (ClinicDoctorBindingId) o;

        if (getClinic() != null ? !getClinic().equals(that.getClinic()) : that.getClinic() != null) return false;
        return getDoctor() != null ? getDoctor().equals(that.getDoctor()) : that.getDoctor() == null;
    }

    @Override
    public int hashCode() {
        int result = getClinic() != null ? getClinic().hashCode() : 0;
        result = 31 * result + (getDoctor() != null ? getDoctor().hashCode() : 0);
        return result;
    }
}
