package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Coquanphoihopthamgia.
 */
@Entity
@Table(name = "coquanphoihopthamgia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coquanphoihopthamgia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sudung")
    private Integer sudung;

    @ManyToOne
    @JsonIgnoreProperties("coquanphoihopthamgias")
    private Detai detai;

    @ManyToOne
    @JsonIgnoreProperties("coquanphoihopthamgias")
    private Coquanphoihop coquanphoihop;

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

    public Coquanphoihopthamgia sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Detai getDetai() {
        return detai;
    }

    public Coquanphoihopthamgia detai(Detai detai) {
        this.detai = detai;
        return this;
    }

    public void setDetai(Detai detai) {
        this.detai = detai;
    }

    public Coquanphoihop getCoquanphoihop() {
        return coquanphoihop;
    }

    public Coquanphoihopthamgia coquanphoihop(Coquanphoihop coquanphoihop) {
        this.coquanphoihop = coquanphoihop;
        return this;
    }

    public void setCoquanphoihop(Coquanphoihop coquanphoihop) {
        this.coquanphoihop = coquanphoihop;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coquanphoihopthamgia)) {
            return false;
        }
        return id != null && id.equals(((Coquanphoihopthamgia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Coquanphoihopthamgia{" +
            "id=" + getId() +
            ", sudung=" + getSudung() +
            "}";
    }
}
