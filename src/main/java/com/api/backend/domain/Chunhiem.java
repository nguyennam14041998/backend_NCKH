package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Chunhiem.
 */
@Entity
@Table(name = "chunhiem")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = "detais")
public class Chunhiem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "chunhiem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Detai> detais = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = {"chunhiems","nhansuthamgias","donvi","chucdanh","hocham"})
    private Nhansu nhansu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public Chunhiem ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Chunhiem sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Detai> getDetais() {
        return detais;
    }

    public Chunhiem detais(Set<Detai> detais) {
        this.detais = detais;
        return this;
    }

    public Chunhiem addDetai(Detai detai) {
        this.detais.add(detai);
        detai.setChunhiem(this);
        return this;
    }

    public Chunhiem removeDetai(Detai detai) {
        this.detais.remove(detai);
        detai.setChunhiem(null);
        return this;
    }

    public void setDetais(Set<Detai> detais) {
        this.detais = detais;
    }

    public Nhansu getNhansu() {
        return nhansu;
    }

    public Chunhiem nhansu(Nhansu nhansu) {
        this.nhansu = nhansu;
        return this;
    }

    public void setNhansu(Nhansu nhansu) {
        this.nhansu = nhansu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chunhiem)) {
            return false;
        }
        return id != null && id.equals(((Chunhiem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chunhiem{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
