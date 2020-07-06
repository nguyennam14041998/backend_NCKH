package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DutoanKP.
 */
@Entity
@Table(name = "dutoan_kp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DutoanKP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "madutoan")
    private String madutoan;

    @Column(name = "tendutoan")
    private String tendutoan;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "dutoanKP")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DutoanKPCT> dutoanKPCTS = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("dutoanKPS")
    private Detai detai;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMadutoan() {
        return madutoan;
    }

    public DutoanKP madutoan(String madutoan) {
        this.madutoan = madutoan;
        return this;
    }

    public void setMadutoan(String madutoan) {
        this.madutoan = madutoan;
    }

    public String getTendutoan() {
        return tendutoan;
    }

    public DutoanKP tendutoan(String tendutoan) {
        this.tendutoan = tendutoan;
        return this;
    }

    public void setTendutoan(String tendutoan) {
        this.tendutoan = tendutoan;
    }

    public String getNoidung() {
        return noidung;
    }

    public DutoanKP noidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Integer getSudung() {
        return sudung;
    }

    public DutoanKP sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<DutoanKPCT> getDutoanKPCTS() {
        return dutoanKPCTS;
    }

    public DutoanKP dutoanKPCTS(Set<DutoanKPCT> dutoanKPCTS) {
        this.dutoanKPCTS = dutoanKPCTS;
        return this;
    }

    public DutoanKP addDutoanKPCT(DutoanKPCT dutoanKPCT) {
        this.dutoanKPCTS.add(dutoanKPCT);
        dutoanKPCT.setDutoanKP(this);
        return this;
    }

    public DutoanKP removeDutoanKPCT(DutoanKPCT dutoanKPCT) {
        this.dutoanKPCTS.remove(dutoanKPCT);
        dutoanKPCT.setDutoanKP(null);
        return this;
    }

    public void setDutoanKPCTS(Set<DutoanKPCT> dutoanKPCTS) {
        this.dutoanKPCTS = dutoanKPCTS;
    }

    public Detai getDetai() {
        return detai;
    }

    public DutoanKP detai(Detai detai) {
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
        if (!(o instanceof DutoanKP)) {
            return false;
        }
        return id != null && id.equals(((DutoanKP) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DutoanKP{" +
            "id=" + getId() +
            ", madutoan='" + getMadutoan() + "'" +
            ", tendutoan='" + getTendutoan() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
