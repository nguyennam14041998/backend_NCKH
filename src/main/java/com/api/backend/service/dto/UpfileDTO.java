package com.api.backend.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.api.backend.domain.Upfile} entity.
 */
public class UpfileDTO implements Serializable {

    private Long id;

    private String mota;

    @Lob
    private byte[] noidung;

    private String noidungContentType;
    private LocalDate thoigian;


    private Long detaiId;

    private Long tiendoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public byte[] getNoidung() {
        return noidung;
    }

    public void setNoidung(byte[] noidung) {
        this.noidung = noidung;
    }

    public String getNoidungContentType() {
        return noidungContentType;
    }

    public void setNoidungContentType(String noidungContentType) {
        this.noidungContentType = noidungContentType;
    }

    public LocalDate getThoigian() {
        return thoigian;
    }

    public void setThoigian(LocalDate thoigian) {
        this.thoigian = thoigian;
    }

    public Long getDetaiId() {
        return detaiId;
    }

    public void setDetaiId(Long detaiId) {
        this.detaiId = detaiId;
    }

    public Long getTiendoId() {
        return tiendoId;
    }

    public void setTiendoId(Long tiendoId) {
        this.tiendoId = tiendoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UpfileDTO upfileDTO = (UpfileDTO) o;
        if (upfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), upfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UpfileDTO{" +
            "id=" + getId() +
            ", mota='" + getMota() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", thoigian='" + getThoigian() + "'" +
            ", detai=" + getDetaiId() +
            ", tiendo=" + getTiendoId() +
            "}";
    }
}
