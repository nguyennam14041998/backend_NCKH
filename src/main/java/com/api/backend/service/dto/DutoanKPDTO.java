package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.DutoanKP} entity.
 */
public class DutoanKPDTO implements Serializable {

    private Long id;

    private String madutoan;

    private String tendutoan;

    private String noidung;

    private Integer sudung;


    private Long detaiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMadutoan() {
        return madutoan;
    }

    public void setMadutoan(String madutoan) {
        this.madutoan = madutoan;
    }

    public String getTendutoan() {
        return tendutoan;
    }

    public void setTendutoan(String tendutoan) {
        this.tendutoan = tendutoan;
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

        DutoanKPDTO dutoanKPDTO = (DutoanKPDTO) o;
        if (dutoanKPDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dutoanKPDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DutoanKPDTO{" +
            "id=" + getId() +
            ", madutoan='" + getMadutoan() + "'" +
            ", tendutoan='" + getTendutoan() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", sudung=" + getSudung() +
            ", detai=" + getDetaiId() +
            "}";
    }
}
