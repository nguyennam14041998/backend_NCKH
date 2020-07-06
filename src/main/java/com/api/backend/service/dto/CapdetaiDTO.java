package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Capdetai} entity.
 */
public class CapdetaiDTO implements Serializable {

    private Long id;

    private String macapdetai;

    private String tencapdetai;

    private Integer sudung;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMacapdetai() {
        return macapdetai;
    }

    public void setMacapdetai(String macapdetai) {
        this.macapdetai = macapdetai;
    }

    public String getTencapdetai() {
        return tencapdetai;
    }

    public void setTencapdetai(String tencapdetai) {
        this.tencapdetai = tencapdetai;
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

        CapdetaiDTO capdetaiDTO = (CapdetaiDTO) o;
        if (capdetaiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capdetaiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CapdetaiDTO{" +
            "id=" + getId() +
            ", macapdetai='" + getMacapdetai() + "'" +
            ", tencapdetai='" + getTencapdetai() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
