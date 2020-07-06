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
 * A Tiendo.
 */
@Entity
@Table(name = "tiendo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tiendo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matiendo")
    private String matiendo;

    @Column(name = "kybaocao")
    private String kybaocao;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "thoigianbatdau")
    private LocalDate thoigianbatdau;

    @Column(name = "thoigianketthuc")
    private LocalDate thoigianketthuc;

    @Column(name = "khoiluonghoanthanh")
    private Integer khoiluonghoanthanh;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "sudung")
    private Integer sudung;

    @OneToMany(mappedBy = "tiendo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Upfile> upfiles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tiendos")
    private Detai detai;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatiendo() {
        return matiendo;
    }

    public Tiendo matiendo(String matiendo) {
        this.matiendo = matiendo;
        return this;
    }

    public void setMatiendo(String matiendo) {
        this.matiendo = matiendo;
    }

    public String getKybaocao() {
        return kybaocao;
    }

    public Tiendo kybaocao(String kybaocao) {
        this.kybaocao = kybaocao;
        return this;
    }

    public void setKybaocao(String kybaocao) {
        this.kybaocao = kybaocao;
    }

    public String getNoidung() {
        return noidung;
    }

    public Tiendo noidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public LocalDate getThoigianbatdau() {
        return thoigianbatdau;
    }

    public Tiendo thoigianbatdau(LocalDate thoigianbatdau) {
        this.thoigianbatdau = thoigianbatdau;
        return this;
    }

    public void setThoigianbatdau(LocalDate thoigianbatdau) {
        this.thoigianbatdau = thoigianbatdau;
    }

    public LocalDate getThoigianketthuc() {
        return thoigianketthuc;
    }

    public Tiendo thoigianketthuc(LocalDate thoigianketthuc) {
        this.thoigianketthuc = thoigianketthuc;
        return this;
    }

    public void setThoigianketthuc(LocalDate thoigianketthuc) {
        this.thoigianketthuc = thoigianketthuc;
    }

    public Integer getKhoiluonghoanthanh() {
        return khoiluonghoanthanh;
    }

    public Tiendo khoiluonghoanthanh(Integer khoiluonghoanthanh) {
        this.khoiluonghoanthanh = khoiluonghoanthanh;
        return this;
    }

    public void setKhoiluonghoanthanh(Integer khoiluonghoanthanh) {
        this.khoiluonghoanthanh = khoiluonghoanthanh;
    }

    public String getGhichu() {
        return ghichu;
    }

    public Tiendo ghichu(String ghichu) {
        this.ghichu = ghichu;
        return this;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Integer getSudung() {
        return sudung;
    }

    public Tiendo sudung(Integer sudung) {
        this.sudung = sudung;
        return this;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Set<Upfile> getUpfiles() {
        return upfiles;
    }

    public Tiendo upfiles(Set<Upfile> upfiles) {
        this.upfiles = upfiles;
        return this;
    }

    public Tiendo addUpfile(Upfile upfile) {
        this.upfiles.add(upfile);
        upfile.setTiendo(this);
        return this;
    }

    public Tiendo removeUpfile(Upfile upfile) {
        this.upfiles.remove(upfile);
        upfile.setTiendo(null);
        return this;
    }

    public void setUpfiles(Set<Upfile> upfiles) {
        this.upfiles = upfiles;
    }

    public Detai getDetai() {
        return detai;
    }

    public Tiendo detai(Detai detai) {
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
        if (!(o instanceof Tiendo)) {
            return false;
        }
        return id != null && id.equals(((Tiendo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tiendo{" +
            "id=" + getId() +
            ", matiendo='" + getMatiendo() + "'" +
            ", kybaocao='" + getKybaocao() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", thoigianbatdau='" + getThoigianbatdau() + "'" +
            ", thoigianketthuc='" + getThoigianketthuc() + "'" +
            ", khoiluonghoanthanh=" + getKhoiluonghoanthanh() +
            ", ghichu='" + getGhichu() + "'" +
            ", sudung=" + getSudung() +
            "}";
    }
}
