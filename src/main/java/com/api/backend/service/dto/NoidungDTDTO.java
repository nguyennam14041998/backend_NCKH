package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.NoidungDT} entity.
 */
public class NoidungDTDTO implements Serializable {

    private Long id;

    private String tennoidung;

    private Integer sudung;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTennoidung() {
        return tennoidung;
    }

    public void setTennoidung(String tennoidung) {
        this.tennoidung = tennoidung;
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

        NoidungDTDTO noidungDTDTO = (NoidungDTDTO) o;
        if (noidungDTDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noidungDTDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoidungDTDTO{" +
            "id=" + getId() +
            ", tennoidung='" + getTennoidung() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
