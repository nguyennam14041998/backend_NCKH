package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Linhvuc} entity.
 */
public class LinhvucDTO implements Serializable {

    private Long id;

    private String malv;

    private String tenlv;

    private Integer sudung;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMalv() {
        return malv;
    }

    public void setMalv(String malv) {
        this.malv = malv;
    }

    public String getTenlv() {
        return tenlv;
    }

    public void setTenlv(String tenlv) {
        this.tenlv = tenlv;
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

        LinhvucDTO linhvucDTO = (LinhvucDTO) o;
        if (linhvucDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), linhvucDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LinhvucDTO{" +
            "id=" + getId() +
            ", malv='" + getMalv() + "'" +
            ", tenlv='" + getTenlv() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
