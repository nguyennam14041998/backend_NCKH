package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Nhansuthamgia} entity.
 */
public class NhansuthamgiaDTO implements Serializable {

    private Long id;

    private Integer sudung;


    private Long nhansuId;

    private Long detaiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Long getNhansuId() {
        return nhansuId;
    }

    public void setNhansuId(Long nhansuId) {
        this.nhansuId = nhansuId;
    }

    public Long getDetaiId() {
        return detaiId;
    }

    public void setDetaiId(Long detaiId) {
        this.detaiId = detaiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NhansuthamgiaDTO nhansuthamgiaDTO = (NhansuthamgiaDTO) o;
        if (nhansuthamgiaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhansuthamgiaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhansuthamgiaDTO{" +
            "id=" + getId() +
            ", sudung=" + getSudung() +
            ", nhansu=" + getNhansuId() +
            ", detai=" + getDetaiId() +
            "}";
    }
}
