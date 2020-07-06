package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Nguonkinhphi} entity.
 */
public class NguonkinhphiDTO implements Serializable {

    private Long id;

    private String manguonkinhphi;

    private String tennguonkinhphi;

    private String noidung;

    private Integer sotiencap;

    private Integer sudung;


    private Long detaiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManguonkinhphi() {
        return manguonkinhphi;
    }

    public void setManguonkinhphi(String manguonkinhphi) {
        this.manguonkinhphi = manguonkinhphi;
    }

    public String getTennguonkinhphi() {
        return tennguonkinhphi;
    }

    public void setTennguonkinhphi(String tennguonkinhphi) {
        this.tennguonkinhphi = tennguonkinhphi;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Integer getSotiencap() {
        return sotiencap;
    }

    public void setSotiencap(Integer sotiencap) {
        this.sotiencap = sotiencap;
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

        NguonkinhphiDTO nguonkinhphiDTO = (NguonkinhphiDTO) o;
        if (nguonkinhphiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nguonkinhphiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NguonkinhphiDTO{" +
            "id=" + getId() +
            ", manguonkinhphi='" + getManguonkinhphi() + "'" +
            ", tennguonkinhphi='" + getTennguonkinhphi() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", sotiencap=" + getSotiencap() +
            ", sudung=" + getSudung() +
            ", detai=" + getDetaiId() +
            "}";
    }
}
