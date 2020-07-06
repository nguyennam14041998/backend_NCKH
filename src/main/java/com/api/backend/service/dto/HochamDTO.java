package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Hocham} entity.
 */
public class HochamDTO implements Serializable {

    private Long id;

    private String mahocham;

    private String tenhocham;

    private Integer sudung;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMahocham() {
        return mahocham;
    }

    public void setMahocham(String mahocham) {
        this.mahocham = mahocham;
    }

    public String getTenhocham() {
        return tenhocham;
    }

    public void setTenhocham(String tenhocham) {
        this.tenhocham = tenhocham;
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

        HochamDTO hochamDTO = (HochamDTO) o;
        if (hochamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hochamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HochamDTO{" +
            "id=" + getId() +
            ", mahocham='" + getMahocham() + "'" +
            ", tenhocham='" + getTenhocham() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
