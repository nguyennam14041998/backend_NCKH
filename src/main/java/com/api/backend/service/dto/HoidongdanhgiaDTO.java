package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Hoidongdanhgia} entity.
 */
public class HoidongdanhgiaDTO implements Serializable {

    private Long id;

    private String mahoidong;

    private String tenhoidong;

    private Integer sudung;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMahoidong() {
        return mahoidong;
    }

    public void setMahoidong(String mahoidong) {
        this.mahoidong = mahoidong;
    }

    public String getTenhoidong() {
        return tenhoidong;
    }

    public void setTenhoidong(String tenhoidong) {
        this.tenhoidong = tenhoidong;
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

        HoidongdanhgiaDTO hoidongdanhgiaDTO = (HoidongdanhgiaDTO) o;
        if (hoidongdanhgiaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hoidongdanhgiaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HoidongdanhgiaDTO{" +
            "id=" + getId() +
            ", mahoidong='" + getMahoidong() + "'" +
            ", tenhoidong='" + getTenhoidong() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
