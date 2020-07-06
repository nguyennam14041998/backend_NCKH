package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Chunhiem} entity.
 */
public class ChunhiemDTO implements Serializable {

    private Long id;

    private String ten;

    private Integer sudung;


    private Long nhansuId;

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

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Long getNhansuId() {
        return nhansuId;
    }

    public void setNhansuId(Long nhansuId) {
        this.nhansuId = nhansuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChunhiemDTO chunhiemDTO = (ChunhiemDTO) o;
        if (chunhiemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chunhiemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChunhiemDTO{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", sudung=" + getSudung() +
            ", nhansu=" + getNhansuId() +
            "}";
    }
}
