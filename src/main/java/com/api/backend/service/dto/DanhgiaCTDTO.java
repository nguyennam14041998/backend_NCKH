package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.DanhgiaCT} entity.
 */
public class DanhgiaCTDTO implements Serializable {

    private Long id;

    private String noidung;

    private Integer diem;

    private Integer sudung;


    private Long danhgiaId;

    private Long noidungdanhgiaId;

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

    public Integer getDiem() {
        return diem;
    }

    public void setDiem(Integer diem) {
        this.diem = diem;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Long getDanhgiaId() {
        return danhgiaId;
    }

    public void setDanhgiaId(Long danhgiaId) {
        this.danhgiaId = danhgiaId;
    }

    public Long getNoidungdanhgiaId() {
        return noidungdanhgiaId;
    }

    public void setNoidungdanhgiaId(Long noidungdanhgiaId) {
        this.noidungdanhgiaId = noidungdanhgiaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DanhgiaCTDTO danhgiaCTDTO = (DanhgiaCTDTO) o;
        if (danhgiaCTDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), danhgiaCTDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DanhgiaCTDTO{" +
            "id=" + getId() +
            ", noidung='" + getNoidung() + "'" +
            ", diem=" + getDiem() +
            ", sudung=" + getSudung() +
            ", danhgia=" + getDanhgiaId() +
            ", noidungdanhgia=" + getNoidungdanhgiaId() +
            "}";
    }
}
