package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ThanhvienHD.
 */
@Entity
@Table(name = "thanhvien_hd")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ThanhvienHD implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "donvi")
    private String donvi;

    @Column(name = "trachnhiem")
    private Integer trachnhiem;

    @Column(name = "sudung")
    private Integer sudung;

    @ManyToOne
    @JsonIgnoreProperties("thanhvienHDS")
    private Hoidongdanhgia hoidongdanhgia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public ThanhvienHD ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDonvi() {
        return donvi;
    }

    public ThanhvienHD donvi(String donvi) {
        this.donvi = donvi;
        return this;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public Integer getTrachnhiem() {
        return trachnhiem;
    }

    public ThanhvienHD trachnhiem(Integer trachnhiem) {
        this.trachnhiem = trachnhiem;
        return this;
    }

    public void setTrachnhiem(Integer trachnhiem) {
        this.trachnhiem = trachnhiem;
    }

    public Integer getSudung() {
        return sudung;
    }

    public ThanhvienHD sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Hoidongdanhgia getHoidongdanhgia() {
        return hoidongdanhgia;
    }

    public ThanhvienHD hoidongdanhgia(Hoidongdanhgia hoidongdanhgia) {
        this.hoidongdanhgia = hoidongdanhgia;
        return this;
    }

    public void setHoidongdanhgia(Hoidongdanhgia hoidongdanhgia) {
        this.hoidongdanhgia = hoidongdanhgia;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThanhvienHD)) {
            return false;
        }
        return id != null && id.equals(((ThanhvienHD) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ThanhvienHD{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", donvi='" + getDonvi() + "'" +
            ", trachnhiem=" + getTrachnhiem() +
            ", sudung=" + getSudung() +
            "}";
    }
}
