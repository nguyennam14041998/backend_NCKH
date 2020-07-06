package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Danhgia.
 */
@Entity
@Table(name = "danhgia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Danhgia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "diem")
    private Integer diem;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "danhgia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DanhgiaCT> danhgiaCTS = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("danhgias")
    private Detai detai;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public Danhgia ma(String ma) {
        this.ma = ma;
        return this;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public Danhgia ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getDiem() {
        return diem;
    }

    public Danhgia diem(Integer diem) {
        this.diem = diem;
        return this;
    }

    public void setDiem(Integer diem) {
        this.diem = diem;
    }

    public String getNoidung() {
        return noidung;
    }

    public Danhgia noidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Danhgia sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<DanhgiaCT> getDanhgiaCTS() {
        return danhgiaCTS;
    }

    public Danhgia danhgiaCTS(Set<DanhgiaCT> danhgiaCTS) {
        this.danhgiaCTS = danhgiaCTS;
        return this;
    }

    public Danhgia addDanhgiaCT(DanhgiaCT danhgiaCT) {
        this.danhgiaCTS.add(danhgiaCT);
        danhgiaCT.setDanhgia(this);
        return this;
    }

    public Danhgia removeDanhgiaCT(DanhgiaCT danhgiaCT) {
        this.danhgiaCTS.remove(danhgiaCT);
        danhgiaCT.setDanhgia(null);
        return this;
    }

    public void setDanhgiaCTS(Set<DanhgiaCT> danhgiaCTS) {
        this.danhgiaCTS = danhgiaCTS;
    }

    public Detai getDetai() {
        return detai;
    }

    public Danhgia detai(Detai detai) {
        this.detai = detai;
        return this;
    }

    public void setDetai(Detai detai) {
        this.detai = detai;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Danhgia)) {
            return false;
        }
        return id != null && id.equals(((Danhgia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Danhgia{" +
            "id=" + getId() +
            ", ma='" + getMa() + "'" +
            ", ten='" + getTen() + "'" +
            ", diem=" + getDiem() +
            ", noidung='" + getNoidung() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
