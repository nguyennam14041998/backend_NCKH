package com.api.backend.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hocham.
 */
@Entity
@Table(name = "hocham")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hocham implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mahocham")
    private String mahocham;

    @Column(name = "tenhocham")
    private String tenhocham;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "hocham")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Nhansu> nhansus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMahocham() {
        return mahocham;
    }

    public Hocham mahocham(String mahocham) {
        this.mahocham = mahocham;
        return this;
    }

    public void setMahocham(String mahocham) {
        this.mahocham = mahocham;
    }

    public String getTenhocham() {
        return tenhocham;
    }

    public Hocham tenhocham(String tenhocham) {
        this.tenhocham = tenhocham;
        return this;
    }

    public void setTenhocham(String tenhocham) {
        this.tenhocham = tenhocham;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Hocham sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Nhansu> getNhansus() {
        return nhansus;
    }

    public Hocham nhansus(Set<Nhansu> nhansus) {
        this.nhansus = nhansus;
        return this;
    }

    public Hocham addNhansu(Nhansu nhansu) {
        this.nhansus.add(nhansu);
        nhansu.setHocham(this);
        return this;
    }

    public Hocham removeNhansu(Nhansu nhansu) {
        this.nhansus.remove(nhansu);
        nhansu.setHocham(null);
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
        if (!(o instanceof Hocham)) {
            return false;
        }
        return id != null && id.equals(((Hocham) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Hocham{" +
            "id=" + getId() +
            ", mahocham='" + getMahocham() + "'" +
            ", tenhocham='" + getTenhocham() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
