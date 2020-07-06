package com.api.backend.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Linhvuc.
 */
@Entity
@Table(name = "linhvuc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Linhvuc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "malv")
    private String malv;

    @Column(name = "tenlv")
    private String tenlv;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "linhvuc")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Detai> detais = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMalv() {
        return malv;
    }

    public Linhvuc malv(String malv) {
        this.malv = malv;
        return this;
    }

    public void setMalv(String malv) {
        this.malv = malv;
    }

    public String getTenlv() {
        return tenlv;
    }

    public Linhvuc tenlv(String tenlv) {
        this.tenlv = tenlv;
        return this;
    }

    public void setTenlv(String tenlv) {
        this.tenlv = tenlv;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Linhvuc sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Detai> getDetais() {
        return detais;
    }

    public Linhvuc detais(Set<Detai> detais) {
        this.detais = detais;
        return this;
    }

    public Linhvuc addDetai(Detai detai) {
        this.detais.add(detai);
        detai.setLinhvuc(this);
        return this;
    }

    public Linhvuc removeDetai(Detai detai) {
        this.detais.remove(detai);
        detai.setLinhvuc(null);
        return this;
    }

    public void setDetais(Set<Detai> detais) {
        this.detais = detais;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Linhvuc)) {
            return false;
        }
        return id != null && id.equals(((Linhvuc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Linhvuc{" +
            "id=" + getId() +
            ", malv='" + getMalv() + "'" +
            ", tenlv='" + getTenlv() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
