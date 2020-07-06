package com.api.backend.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Noidungdanhgia.
 */
@Entity
@Table(name = "noidungdanhgia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Noidungdanhgia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "noidungdanhgia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DanhgiaCT> danhgiaCTS = new HashSet<>();

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

    public Noidungdanhgia noidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Noidungdanhgia sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<DanhgiaCT> getDanhgiaCTS() {
        return danhgiaCTS;
    }

    public Noidungdanhgia danhgiaCTS(Set<DanhgiaCT> danhgiaCTS) {
        this.danhgiaCTS = danhgiaCTS;
        return this;
    }

    public Noidungdanhgia addDanhgiaCT(DanhgiaCT danhgiaCT) {
        this.danhgiaCTS.add(danhgiaCT);
        danhgiaCT.setNoidungdanhgia(this);
        return this;
    }

    public Noidungdanhgia removeDanhgiaCT(DanhgiaCT danhgiaCT) {
        this.danhgiaCTS.remove(danhgiaCT);
        danhgiaCT.setNoidungdanhgia(null);
        return this;
    }

    public void setDanhgiaCTS(Set<DanhgiaCT> danhgiaCTS) {
        this.danhgiaCTS = danhgiaCTS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Noidungdanhgia)) {
            return false;
        }
        return id != null && id.equals(((Noidungdanhgia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Noidungdanhgia{" +
            "id=" + getId() +
            ", noidung='" + getNoidung() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
