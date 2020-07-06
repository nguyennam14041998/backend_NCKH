package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Nhansuthamgia.
 */
@Entity
@Table(name = "nhansuthamgia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nhansuthamgia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sudung")
    private Integer sudung;

    @ManyToOne
    @JsonIgnoreProperties("nhansuthamgias")
    private Nhansu nhansu;

    @ManyToOne
    @JsonIgnoreProperties("nhansuthamgias")
    private Detai detai;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Nhansuthamgia sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Nhansu getNhansu() {
        return nhansu;
    }

    public Nhansuthamgia nhansu(Nhansu nhansu) {
        this.nhansu = nhansu;
        return this;
    }

    public void setNhansu(Nhansu nhansu) {
        this.nhansu = nhansu;
    }

    public Detai getDetai() {
        return detai;
    }

    public Nhansuthamgia detai(Detai detai) {
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
        if (!(o instanceof Nhansuthamgia)) {
            return false;
        }
        return id != null && id.equals(((Nhansuthamgia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Nhansuthamgia{" +
            "id=" + getId() +
            ", sudung=" + getSudung() +
            "}";
    }
}
