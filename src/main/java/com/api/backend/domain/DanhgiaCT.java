package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DanhgiaCT.
 */
@Entity
@Table(name = "danhgia_ct")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DanhgiaCT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "diem")
    private Integer diem;

    @Column(name = "sudung")
    private Integer sudung;

    @ManyToOne
    @JsonIgnoreProperties("danhgiaCTS")
    private Danhgia danhgia;

    @ManyToOne
    @JsonIgnoreProperties("danhgiaCTS")
    private Noidungdanhgia noidungdanhgia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public DanhgiaCT noidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Integer getDiem() {
        return diem;
    }

    public DanhgiaCT diem(Integer diem) {
        this.diem = diem;
        return this;
    }

    public void setDiem(Integer diem) {
        this.diem = diem;
    }

    public Integer getSudung() {
        return sudung;
    }

    public DanhgiaCT sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Danhgia getDanhgia() {
        return danhgia;
    }

    public DanhgiaCT danhgia(Danhgia danhgia) {
        this.danhgia = danhgia;
        return this;
    }

    public void setDanhgia(Danhgia danhgia) {
        this.danhgia = danhgia;
    }

    public Noidungdanhgia getNoidungdanhgia() {
        return noidungdanhgia;
    }

    public DanhgiaCT noidungdanhgia(Noidungdanhgia noidungdanhgia) {
        this.noidungdanhgia = noidungdanhgia;
        return this;
    }

    public void setNoidungdanhgia(Noidungdanhgia noidungdanhgia) {
        this.noidungdanhgia = noidungdanhgia;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DanhgiaCT)) {
            return false;
        }
        return id != null && id.equals(((DanhgiaCT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DanhgiaCT{" +
            "id=" + getId() +
            ", noidung='" + getNoidung() + "'" +
            ", diem=" + getDiem() +
            ", sudung=" + getSudung() +
            "}";
    }
}
