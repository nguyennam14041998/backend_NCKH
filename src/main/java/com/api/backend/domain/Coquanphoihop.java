package com.api.backend.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Coquanphoihop.
 */
@Entity
@Table(name = "coquanphoihop")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coquanphoihop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "macoquan")
    private String macoquan;

    @Column(name = "tencoquan")
    private String tencoquan;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "tendaidien")
    private String tendaidien;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "coquanphoihop")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Coquanphoihopthamgia> coquanphoihopthamgias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMacoquan() {
        return macoquan;
    }

    public Coquanphoihop macoquan(String macoquan) {
        this.macoquan = macoquan;
        return this;
    }

    public void setMacoquan(String macoquan) {
        this.macoquan = macoquan;
    }

    public String getTencoquan() {
        return tencoquan;
    }

    public Coquanphoihop tencoquan(String tencoquan) {
        this.tencoquan = tencoquan;
        return this;
    }

    public void setTencoquan(String tencoquan) {
        this.tencoquan = tencoquan;
    }

    public String getNoidung() {
        return noidung;
    }

    public Coquanphoihop noidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTendaidien() {
        return tendaidien;
    }

    public Coquanphoihop tendaidien(String tendaidien) {
        this.tendaidien = tendaidien;
        return this;
    }

    public void setTendaidien(String tendaidien) {
        this.tendaidien = tendaidien;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Coquanphoihop sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Coquanphoihopthamgia> getCoquanphoihopthamgias() {
        return coquanphoihopthamgias;
    }

    public Coquanphoihop coquanphoihopthamgias(Set<Coquanphoihopthamgia> coquanphoihopthamgias) {
        this.coquanphoihopthamgias = coquanphoihopthamgias;
        return this;
    }

    public Coquanphoihop addCoquanphoihopthamgia(Coquanphoihopthamgia coquanphoihopthamgia) {
        this.coquanphoihopthamgias.add(coquanphoihopthamgia);
        coquanphoihopthamgia.setCoquanphoihop(this);
        return this;
    }

    public Coquanphoihop removeCoquanphoihopthamgia(Coquanphoihopthamgia coquanphoihopthamgia) {
        this.coquanphoihopthamgias.remove(coquanphoihopthamgia);
        coquanphoihopthamgia.setCoquanphoihop(null);
        return this;
    }

    public void setCoquanphoihopthamgias(Set<Coquanphoihopthamgia> coquanphoihopthamgias) {
        this.coquanphoihopthamgias = coquanphoihopthamgias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coquanphoihop)) {
            return false;
        }
        return id != null && id.equals(((Coquanphoihop) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Coquanphoihop{" +
            "id=" + getId() +
            ", macoquan='" + getMacoquan() + "'" +
            ", tencoquan='" + getTencoquan() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", tendaidien='" + getTendaidien() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
