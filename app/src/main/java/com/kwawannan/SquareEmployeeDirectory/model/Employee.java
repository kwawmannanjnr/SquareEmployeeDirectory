
package com.kwawannan.SquareEmployeeDirectory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {
    @SerializedName("employees")
    @Expose
    private String uuid;
    @SerializedName("employees")
    @Expose
    private String fullName;
    @SerializedName("employees")
    @Expose
    private String phoneNumber;
    @SerializedName("employees")
    @Expose
    private String emailAddress;
    @SerializedName("employees")
    @Expose
    private String biography;
    @SerializedName("employees")
    @Expose
    private String photoUrlSmall;
    @SerializedName("employees")
    @Expose
    private String photoUrlLarge;
    @SerializedName("employees")
    @Expose
    private String team;
    @SerializedName("employees")
    @Expose
    private Position employeeType;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhotoUrlSmall() {
        return photoUrlSmall;
    }

    public void setPhotoUrlSmall(String photoUrlSmall) {
        this.photoUrlSmall = photoUrlSmall;
    }

    public String getPhotoUrlLarge() {
        return photoUrlLarge;
    }

    public void setPhotoUrlLarge(String photoUrlLarge) {
        this.photoUrlLarge = photoUrlLarge;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Position getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Position employeeType) {
        this.employeeType = employeeType;
    }

}
