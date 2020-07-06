package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Nhansu.
 */
@Entity
@Table(name = "nhansu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nhansu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "manhansu")
    private String manhansu;

    @Column(name = "tennhansu")
    private String tennhansu;

    @Column(name = "sdt")
    private Integer sdt;

    @Column(name = "email")
    private String email;

    @Column(name = "diachi")
    private String diachi;

    @Column(name = "namsinh")
    private String namsinh;

    @Column(name = "ngaysinh")
    private LocalDate ngaysinh;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "nhansu")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chunhiem> chunhiems = new HashSet<>();

    @OneToMany(mappedBy = "nhansu")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Nhansuthamgia> nhansuthamgias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("nhansus")
    private Donvi donvi;

    @ManyToOne
    @JsonIgnoreProperties("nhansus")
    private Chucdanh chucdanh;

    @ManyToOne
    @JsonIgnoreProperties("nhansus")
    private Hocham hocham;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManhansu() {
        return manhansu;
    }

    public Nhansu manhansu(String manhansu) {
        this.manhansu = manhansu;
        return this;
    }

    public void setManhansu(String manhansu) {
        this.manhansu = manhansu;
    }

    public String getTennhansu() {
        return tennhansu;
    }

    public Nhansu tennhansu(String tennhansu) {
        this.tennhansu = tennhansu;
        return this;
    }

    public void setTennhansu(String tennhansu) {
        this.tennhansu = tennhansu;
    }

    public Integer getSdt() {
        return sdt;
    }

    public Nhansu sdt(Integer sdt) {
        this.sdt = sdt;
        return this;
    }

    public void setSdt(Integer sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public Nhansu email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public Nhansu diachi(String diachi) {
        this.diachi = diachi;
        return this;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public Nhansu namsinh(String namsinh) {
        this.namsinh = namsinh;
        return this;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }

    public LocalDate getNgaysinh() {
        return ngaysinh;
    }

    public Nhansu ngaysinh(LocalDate ngaysinh) {
        this.ngaysinh = ngaysinh;
        return this;
    }

    public void setNgaysinh(LocalDate ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Nhansu sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Chunhiem> getChunhiems() {
        return chunhiems;
    }

    public Nhansu chunhiems(Set<Chunhiem> chunhiems) {
        this.chunhiems = chunhiems;
        return this;
    }

    public Nhansu addChunhiem(Chunhiem chunhiem) {
        this.chunhiems.add(chunhiem);
        chunhiem.setNhansu(this);
        return this;
    }

    public Nhansu removeChunhiem(Chunhiem chunhiem) {
        this.chunhiems.remove(chunhiem);
        chunhiem.setNhansu(null);
        return this;
    }

    public void setChunhiems(Set<Chunhiem> chunhiems) {
        this.chunhiems = chunhiems;
    }

    public Set<Nhansuthamgia> getNhansuthamgias() {
        return nhansuthamgias;
    }

    public Nhansu nhansuthamgias(Set<Nhansuthamgia> nhansuthamgias) {
        this.nhansuthamgias = nhansuthamgias;
        return this;
    }

    public Nhansu addNhansuthamgia(Nhansuthamgia nhansuthamgia) {
        this.nhansuthamgias.add(nhansuthamgia);
        nhansuthamgia.setNhansu(this);
        return this;
    }

    public Nhansu removeNhansuthamgia(Nhansuthamgia nhansuthamgia) {
        this.nhansuthamgias.remove(nhansuthamgia);
        nhansuthamgia.setNhansu(null);
        return this;
    }

    public void setNhansuthamgias(Set<Nhansuthamgia> nhansuthamgias) {
        this.nhansuthamgias = nhansuthamgias;
    }

    public Donvi getDonvi() {
        return donvi;
    }

    public Nhansu donvi(Donvi donvi) {
        this.donvi = donvi;
        return this;
    }

    public void setDonvi(Donvi donvi) {
        this.donvi = donvi;
    }

    public Chucdanh getChucdanh() {
        return chucdanh;
    }

    public Nhansu chucdanh(Chucdanh chucdanh) {
        this.chucdanh = chucdanh;
        return this;
    }

    public void setChucdanh(Chucdanh chucdanh) {
        this.chucdanh = chucdanh;
    }

    public Hocham getHocham() {
        return hocham;
    }

    public Nhansu hocham(Hocham hocham) {
        this.hocham = hocham;
        return this;
    }

    public void setHocham(Hocham hocham) {
        this.hocham = hocham;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nhansu)) {
            return false;
        }
        return id != null && id.equals(((Nhansu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Nhansu{" +
            "id=" + getId() +
            ", manhansu='" + getManhansu() + "'" +
            ", tennhansu='" + getTennhansu() + "'" +
            ", sdt=" + getSdt() +
            ", email='" + getEmail() + "'" +
            ", diachi='" + getDiachi() + "'" +
            ", namsinh='" + getNamsinh() + "'" +
            ", ngaysinh='" + getNgaysinh() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
