package com.api.backend.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Donvi.
 */
@Entity
@Table(name = "donvi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Donvi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "madv")
    private String madv;

    @Column(name = "tendv")
    private String tendv;

    @Column(name = "dienthoai")
    private Integer dienthoai;

    @Column(name = "fax")
    private Integer fax;

    @Column(name = "email")
    private String email;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "donvi")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Nhansu> nhansus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMadv() {
        return madv;
    }

    public Donvi madv(String madv) {
        this.madv = madv;
        return this;
    }

    public void setMadv(String madv) {
        this.madv = madv;
    }

    public String getTendv() {
        return tendv;
    }

    public Donvi tendv(String tendv) {
        this.tendv = tendv;
        return this;
    }

    public void setTendv(String tendv) {
        this.tendv = tendv;
    }

    public Integer getDienthoai() {
        return dienthoai;
    }

    public Donvi dienthoai(Integer dienthoai) {
        this.dienthoai = dienthoai;
        return this;
    }

    public void setDienthoai(Integer dienthoai) {
        this.dienthoai = dienthoai;
    }

    public Integer getFax() {
        return fax;
    }

    public Donvi fax(Integer fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(Integer fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public Donvi email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Donvi sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Nhansu> getNhansus() {
        return nhansus;
    }

    public Donvi nhansus(Set<Nhansu> nhansus) {
        this.nhansus = nhansus;
        return this;
    }

    public Donvi addNhansu(Nhansu nhansu) {
        this.nhansus.add(nhansu);
        nhansu.setDonvi(this);
        return this;
    }

    public Donvi removeNhansu(Nhansu nhansu) {
        this.nhansus.remove(nhansu);
        nhansu.setDonvi(null);
        return this;
    }

    public void setNhansus(Set<Nhansu> nhansus) {
        this.nhansus = nhansus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Donvi)) {
            return false;
        }
        return id != null && id.equals(((Donvi) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Donvi{" +
            "id=" + getId() +
            ", madv='" + getMadv() + "'" +
            ", tendv='" + getTendv() + "'" +
            ", dienthoai=" + getDienthoai() +
            ", fax=" + getFax() +
            ", email='" + getEmail() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
