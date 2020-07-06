package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Chucdanh} entity.
 */
public class ChucdanhDTO implements Serializable {

    private Long id;

    private String machucdanh;

    private String tenchucdanh;

    private Integer sudung;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMachucdanh() {
        return machucdanh;
    }

    public void setMachucdanh(String machucdanh) {
        this.machucdanh = machucdanh;
    }

    public String getTenchucdanh() {
        return tenchucdanh;
    }

    public void setTenchucdanh(String tenchucdanh) {
        this.tenchucdanh = tenchucdanh;
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

        ChucdanhDTO chucdanhDTO = (ChucdanhDTO) o;
        if (chucdanhDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chucdanhDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChucdanhDTO{" +
            "id=" + getId() +
            ", machucdanh='" + getMachucdanh() + "'" +
            ", tenchucdanh='" + getTenchucdanh() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
