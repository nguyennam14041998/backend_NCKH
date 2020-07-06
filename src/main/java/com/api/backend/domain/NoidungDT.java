package com.api.backend.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A NoidungDT.
 */
@Entity
@Table(name = "noidung_dt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NoidungDT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tennoidung")
    private String tennoidung;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "noidungDT")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DutoanKPCT> dutoanKPCTS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTennoidung() {
        return tennoidung;
    }

    public NoidungDT tennoidung(String tennoidung) {
        this.tennoidung = tennoidung;
        return this;
    }

    public void setTennoidung(String tennoidung) {
        this.tennoidung = tennoidung;
    }

    public Integer getSudung() {
        return sudung;
    }

    public NoidungDT sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<DutoanKPCT> getDutoanKPCTS() {
        return dutoanKPCTS;
    }

    public NoidungDT dutoanKPCTS(Set<DutoanKPCT> dutoanKPCTS) {
        this.dutoanKPCTS = dutoanKPCTS;
        return this;
    }

    public NoidungDT addDutoanKPCT(DutoanKPCT dutoanKPCT) {
        this.dutoanKPCTS.add(dutoanKPCT);
        dutoanKPCT.setNoidungDT(this);
        return this;
    }

    public NoidungDT removeDutoanKPCT(DutoanKPCT dutoanKPCT) {
        this.dutoanKPCTS.remove(dutoanKPCT);
        dutoanKPCT.setNoidungDT(null);
        return this;
    }

    public void setDutoanKPCTS(Set<DutoanKPCT> dutoanKPCTS) {
        this.dutoanKPCTS = dutoanKPCTS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoidungDT)) {
            return false;
        }
        return id != null && id.equals(((NoidungDT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NoidungDT{" +
            "id=" + getId() +
            ", tennoidung='" + getTennoidung() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
