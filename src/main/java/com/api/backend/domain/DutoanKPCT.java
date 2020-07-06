package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DutoanKPCT.
 */
@Entity
@Table(name = "dutoan_kpct")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DutoanKPCT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "soluong")
    private Integer soluong;

    @Column(name = "mucchi")
    private Integer mucchi;

    @Column(name = "tong")
    private Integer tong;

    @Column(name = "sudung")
    private Integer sudung;

    @ManyToOne
    @JsonIgnoreProperties("dutoanKPCTS")
    private DutoanKP dutoanKP;

    @ManyToOne
    @JsonIgnoreProperties("dutoanKPCTS")
    private NoidungDT noidungDT;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public DutoanKPCT noidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public DutoanKPCT soluong(Integer soluong) {
        this.soluong = soluong;
        return this;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public Integer getMucchi() {
        return mucchi;
    }

    public DutoanKPCT mucchi(Integer mucchi) {
        this.mucchi = mucchi;
        return this;
    }

    public void setMucchi(Integer mucchi) {
        this.mucchi = mucchi;
    }

    public Integer getTong() {
        return tong;
    }

    public DutoanKPCT tong(Integer tong) {
        this.tong = tong;
        return this;
    }

    public void setTong(Integer tong) {
        this.tong = tong;
    }

    public Integer getSudung() {
        return sudung;
    }

    public DutoanKPCT sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public DutoanKP getDutoanKP() {
        return dutoanKP;
    }

    public DutoanKPCT dutoanKP(DutoanKP dutoanKP) {
        this.dutoanKP = dutoanKP;
        return this;
    }

    public void setDutoanKP(DutoanKP dutoanKP) {
        this.dutoanKP = dutoanKP;
    }

    public NoidungDT getNoidungDT() {
        return noidungDT;
    }

    public DutoanKPCT noidungDT(NoidungDT noidungDT) {
        this.noidungDT = noidungDT;
        return this;
    }

    public void setNoidungDT(NoidungDT noidungDT) {
        this.noidungDT = noidungDT;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DutoanKPCT)) {
            return false;
        }
        return id != null && id.equals(((DutoanKPCT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DutoanKPCT{" +
            "id=" + getId() +
            ", noidung='" + getNoidung() + "'" +
            ", soluong=" + getSoluong() +
            ", mucchi=" + getMucchi() +
            ", tong=" + getTong() +
            ", sudung=" + getSudung() +
            "}";
    }
}
