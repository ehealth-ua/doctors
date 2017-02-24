package com.ehealth.doctors.model.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;


public class DoctorLicenseDTO implements java.io.Serializable {
    private UUID id;
    @NotNull
    private String issuedBy;
    @NotNull
    private String category;
    @NotNull
    private String orderNo;
    @NotNull
    private String dateIssued;
    @NotNull
    private String dateExpiry;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }
}
