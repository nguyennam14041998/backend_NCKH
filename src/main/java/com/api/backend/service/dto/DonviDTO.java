package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Donvi} entity.
 */
public class DonviDTO implements Serializable {

    private Long id;

    private String madv;

    private String tendv;

    private Integer dienthoai;

    private Integer fax;

    private String email;

    private Integer sudung;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMadv() {
        return madv;
    }

    public void setMadv(String madv) {
        this.madv = madv;
    }

    public String getTendv() {
        return tendv;
    }

    public void setTendv(String tendv) {
        this.tendv = tendv;
    }

    public Integer getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(Integer dienthoai) {
        this.dienthoai = dienthoai;
    }

    public Integer getFax() {
        return fax;
    }

    public void setFax(Integer fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DonviDTO donviDTO = (DonviDTO) o;
        if (donviDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), donviDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DonviDTO{" +
            "id=" + getId() +
            ", madv='" + getMadv() + "'" +
            ", tendv='" + getTendv() + "'" +
            ", dienthoai=" + getDienthoai() +
            ", fax=" + getFax() +
            ", email='" + getEmail() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
