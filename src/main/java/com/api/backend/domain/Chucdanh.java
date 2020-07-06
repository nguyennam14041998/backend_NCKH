package com.api.backend.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Chucdanh.
 */
@Entity
@Table(name = "chucdanh")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chucdanh implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "machucdanh")
    private String machucdanh;

    @Column(name = "tenchucdanh")
    private String tenchucdanh;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "chucdanh")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Nhansu> nhansus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMachucdanh() {
        return machucdanh;
    }

    public Chucdanh machucdanh(String machucdanh) {
        this.machucdanh = machucdanh;
        return this;
    }

    public void setMachucdanh(String machucdanh) {
        this.machucdanh = machucdanh;
    }

    public String getTenchucdanh() {
        return tenchucdanh;
    }

    public Chucdanh tenchucdanh(String tenchucdanh) {
        this.tenchucdanh = tenchucdanh;
        return this;
    }

    public void setTenchucdanh(String tenchucdanh) {
        this.tenchucdanh = tenchucdanh;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Chucdanh sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Nhansu> getNhansus() {
        return nhansus;
    }

    public Chucdanh nhansus(Set<Nhansu> nhansus) {
        this.nhansus = nhansus;
        return this;
    }

    public Chucdanh addNhansu(Nhansu nhansu) {
        this.nhansus.add(nhansu);
        nhansu.setChucdanh(this);
        return this;
    }

    public Chucdanh removeNhansu(Nhansu nhansu) {
        this.nhansus.remove(nhansu);
        nhansu.setChucdanh(null);
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
        if (!(o instanceof Chucdanh)) {
            return false;
        }
        return id != null && id.equals(((Chucdanh) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chucdanh{" +
            "id=" + getId() +
            ", machucdanh='" + getMachucdanh() + "'" +
            ", tenchucdanh='" + getTenchucdanh() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
