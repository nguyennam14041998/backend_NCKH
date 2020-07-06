package com.api.backend.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Capdetai.
 */
@Entity
@Table(name = "capdetai")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Capdetai implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "macapdetai")
    private String macapdetai;

    @Column(name = "tencapdetai")
    private String tencapdetai;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "capdetai")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Detai> detais = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMacapdetai() {
        return macapdetai;
    }

    public Capdetai macapdetai(String macapdetai) {
        this.macapdetai = macapdetai;
        return this;
    }

    public void setMacapdetai(String macapdetai) {
        this.macapdetai = macapdetai;
    }

    public String getTencapdetai() {
        return tencapdetai;
    }

    public Capdetai tencapdetai(String tencapdetai) {
        this.tencapdetai = tencapdetai;
        return this;
    }

    public void setTencapdetai(String tencapdetai) {
        this.tencapdetai = tencapdetai;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Capdetai sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Detai> getDetais() {
        return detais;
    }

    public Capdetai detais(Set<Detai> detais) {
        this.detais = detais;
        return this;
    }

    public Capdetai addDetai(Detai detai) {
        this.detais.add(detai);
        detai.setCapdetai(this);
        return this;
    }

    public Capdetai removeDetai(Detai detai) {
        this.detais.remove(detai);
        detai.setCapdetai(null);
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
        if (!(o instanceof Capdetai)) {
            return false;
        }
        return id != null && id.equals(((Capdetai) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Capdetai{" +
            "id=" + getId() +
            ", macapdetai='" + getMacapdetai() + "'" +
            ", tencapdetai='" + getTencapdetai() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
