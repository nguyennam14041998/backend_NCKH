package com.api.backend.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Tiendo} entity.
 */
public class TiendoDTO implements Serializable {

    private Long id;

    private String matiendo;

    private String kybaocao;

    private String noidung;

    private LocalDate thoigianbatdau;

    private LocalDate thoigianketthuc;

    private Integer khoiluonghoanthanh;

    private String ghichu;

    private Integer sudung;


    private Long detaiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatiendo() {
        return matiendo;
    }

    public void setMatiendo(String matiendo) {
        this.matiendo = matiendo;
    }

    public String getKybaocao() {
        return kybaocao;
    }

    public void setKybaocao(String kybaocao) {
        this.kybaocao = kybaocao;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public LocalDate getThoigianbatdau() {
        return thoigianbatdau;
    }

    public void setThoigianbatdau(LocalDate thoigianbatdau) {
        this.thoigianbatdau = thoigianbatdau;
    }

    public LocalDate getThoigianketthuc() {
        return thoigianketthuc;
    }

    public void setThoigianketthuc(LocalDate thoigianketthuc) {
        this.thoigianketthuc = thoigianketthuc;
    }

    public Integer getKhoiluonghoanthanh() {
        return khoiluonghoanthanh;
    }

    public void setKhoiluonghoanthanh(Integer khoiluonghoanthanh) {
        this.khoiluonghoanthanh = khoiluonghoanthanh;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
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

        TiendoDTO tiendoDTO = (TiendoDTO) o;
        if (tiendoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tiendoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TiendoDTO{" +
            "id=" + getId() +
            ", matiendo='" + getMatiendo() + "'" +
            ", kybaocao='" + getKybaocao() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", thoigianbatdau='" + getThoigianbatdau() + "'" +
            ", thoigianketthuc='" + getThoigianketthuc() + "'" +
            ", khoiluonghoanthanh=" + getKhoiluonghoanthanh() +
            ", ghichu='" + getGhichu() + "'" +
            ", sudung=" + getSudung() +
            ", detai=" + getDetaiId() +
            "}";
    }
}
