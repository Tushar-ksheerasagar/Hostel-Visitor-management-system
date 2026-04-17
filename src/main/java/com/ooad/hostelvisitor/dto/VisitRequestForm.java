package com.ooad.hostelvisitor.dto;

import com.ooad.hostelvisitor.model.IdType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VisitRequestForm {

    @NotBlank
    private String visitorName;

    @NotBlank
    private String visitorPhone;

    @NotBlank
    private String purpose;

    @NotBlank
    private String studentRoomNo;

    @NotNull
    private IdType idType;

    @NotBlank
    private String idNumber;

    @NotNull
    private Long guardId;

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorPhone() {
        return visitorPhone;
    }

    public void setVisitorPhone(String visitorPhone) {
        this.visitorPhone = visitorPhone;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStudentRoomNo() {
        return studentRoomNo;
    }

    public void setStudentRoomNo(String studentRoomNo) {
        this.studentRoomNo = studentRoomNo;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Long getGuardId() {
        return guardId;
    }

    public void setGuardId(Long guardId) {
        this.guardId = guardId;
    }
}
