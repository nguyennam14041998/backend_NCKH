package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.ThanhvienHD} entity.
 */
public class ThanhvienHDDTO implements Serializable {

    private Long id;

    private String ten;

    private String donvi;

    private Integer trachnhiem;

    private Integer sudung;


    private Long hoidongdanhgiaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public Integer getTrachnhiem() {
        return trachnhiem;
    }

    public void setTrachnhiem(Integer trachnhiem) {
        this.trachnhiem = trachnhiem;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Long getHoidongdanhgiaId() {
        return hoidongdanhgiaId;
    }

    public void setHoidongdanhgiaId(Long hoidongdanhgiaId) {
        this.hoidongdanhgiaId = hoidongdanhgiaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThanhvienHDDTO thanhvienHDDTO = (ThanhvienHDDTO) o;
        if (thanhvienHDDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), thanhvienHDDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThanhvienHDDTO{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", donvi='" + getDonvi() + "'" +
            ", trachnhiem=" + getTrachnhiem() +
            ", sudung=" + getSudung() +
            ", hoidongdanhgia=" + getHoidongdanhgiaId() +
            "}";
    }
}
