package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Noidungdanhgia} entity.
 */
public class NoidungdanhgiaDTO implements Serializable {

    private Long id;

    private String noidung;

    private Integer sudung;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
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

        NoidungdanhgiaDTO noidungdanhgiaDTO = (NoidungdanhgiaDTO) o;
        if (noidungdanhgiaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noidungdanhgiaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoidungdanhgiaDTO{" +
            "id=" + getId() +
            ", noidung='" + getNoidung() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
