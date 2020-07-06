package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Coquanphoihop} entity.
 */
public class CoquanphoihopDTO implements Serializable {

    private Long id;

    private String macoquan;

    private String tencoquan;

    private String noidung;

    private String tendaidien;

    private Integer sudung;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMacoquan() {
        return macoquan;
    }

    public void setMacoquan(String macoquan) {
        this.macoquan = macoquan;
    }

    public String getTencoquan() {
        return tencoquan;
    }

    public void setTencoquan(String tencoquan) {
        this.tencoquan = tencoquan;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTendaidien() {
        return tendaidien;
    }

    public void setTendaidien(String tendaidien) {
        this.tendaidien = tendaidien;
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

        CoquanphoihopDTO coquanphoihopDTO = (CoquanphoihopDTO) o;
        if (coquanphoihopDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coquanphoihopDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoquanphoihopDTO{" +
            "id=" + getId() +
            ", macoquan='" + getMacoquan() + "'" +
            ", tencoquan='" + getTencoquan() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", tendaidien='" + getTendaidien() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
