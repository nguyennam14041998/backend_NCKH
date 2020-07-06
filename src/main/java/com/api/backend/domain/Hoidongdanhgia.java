package com.api.backend.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hoidongdanhgia.
 */
@Entity
@Table(name = "hoidongdanhgia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hoidongdanhgia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mahoidong")
    private String mahoidong;

    @Column(name = "tenhoidong")
    private String tenhoidong;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "hoidongdanhgia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Detai> detais = new HashSet<>();

    @OneToMany(mappedBy = "hoidongdanhgia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ThanhvienHD> thanhvienHDS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMahoidong() {
        return mahoidong;
    }

    public Hoidongdanhgia mahoidong(String mahoidong) {
        this.mahoidong = mahoidong;
        return this;
    }

    public void setMahoidong(String mahoidong) {
        this.mahoidong = mahoidong;
    }

    public String getTenhoidong() {
        return tenhoidong;
    }

    public Hoidongdanhgia tenhoidong(String tenhoidong) {
        this.tenhoidong = tenhoidong;
        return this;
    }

    public void setTenhoidong(String tenhoidong) {
        this.tenhoidong = tenhoidong;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Hoidongdanhgia sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Detai> getDetais() {
        return detais;
    }

    public Hoidongdanhgia detais(Set<Detai> detais) {
        this.detais = detais;
        return this;
    }

    public Hoidongdanhgia addDetai(Detai detai) {
        this.detais.add(detai);
        detai.setHoidongdanhgia(this);
        return this;
    }

    public Hoidongdanhgia removeDetai(Detai detai) {
        this.detais.remove(detai);
        detai.setHoidongdanhgia(null);
        return this;
    }

    public void setDetais(Set<Detai> detais) {
        this.detais = detais;
    }

    public Set<ThanhvienHD> getThanhvienHDS() {
        return thanhvienHDS;
    }

    public Hoidongdanhgia thanhvienHDS(Set<ThanhvienHD> thanhvienHDS) {
        this.thanhvienHDS = thanhvienHDS;
        return this;
    }

    public Hoidongdanhgia addThanhvienHD(ThanhvienHD thanhvienHD) {
        this.thanhvienHDS.add(thanhvienHD);
        thanhvienHD.setHoidongdanhgia(this);
        return this;
    }

    public Hoidongdanhgia removeThanhvienHD(ThanhvienHD thanhvienHD) {
        this.thanhvienHDS.remove(thanhvienHD);
        thanhvienHD.setHoidongdanhgia(null);
        return this;
    }

    public void setThanhvienHDS(Set<ThanhvienHD> thanhvienHDS) {
        this.thanhvienHDS = thanhvienHDS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hoidongdanhgia)) {
            return false;
        }
        return id != null && id.equals(((Hoidongdanhgia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Hoidongdanhgia{" +
            "id=" + getId() +
            ", mahoidong='" + getMahoidong() + "'" +
            ", tenhoidong='" + getTenhoidong() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
