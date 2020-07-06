package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Coquanphoihopthamgia} entity.
 */
public class CoquanphoihopthamgiaDTO implements Serializable {

    private Long id;

    private Integer sudung;


    private Long detaiId;

    private Long coquanphoihopId;

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

    public Long getDetaiId() {
        return detaiId;
    }

    public void setDetaiId(Long detaiId) {
        this.detaiId = detaiId;
    }

    public Long getCoquanphoihopId() {
        return coquanphoihopId;
    }

    public void setCoquanphoihopId(Long coquanphoihopId) {
        this.coquanphoihopId = coquanphoihopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO = (CoquanphoihopthamgiaDTO) o;
        if (coquanphoihopthamgiaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coquanphoihopthamgiaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoquanphoihopthamgiaDTO{" +
            "id=" + getId() +
            ", sudung=" + getSudung() +
            ", detai=" + getDetaiId() +
            ", coquanphoihop=" + getCoquanphoihopId() +
            "}";
    }
}
