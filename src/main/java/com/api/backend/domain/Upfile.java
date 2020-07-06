package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Upfile.
 */
@Entity
@Table(name = "upfile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Upfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mota")
    private String mota;

    @Lob
    @Column(name = "noidung")
    private byte[] noidung;

    @Column(name = "noidung_content_type")
    private String noidungContentType;

    @Column(name = "thoigian")
    private LocalDate thoigian;

    @ManyToOne
    @JsonIgnoreProperties("upfiles")
    private Detai detai;

    @ManyToOne
    @JsonIgnoreProperties("upfiles")
    private Tiendo tiendo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMota() {
        return mota;
    }

    public Upfile mota(String mota) {
        this.mota = mota;
        return this;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public byte[] getNoidung() {
        return noidung;
    }

    public Upfile noidung(byte[] noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(byte[] noidung) {
        this.noidung = noidung;
    }

    public String getNoidungContentType() {
        return noidungContentType;
    }

    public Upfile noidungContentType(String noidungContentType) {
        this.noidungContentType = noidungContentType;
        return this;
    }

    public void setNoidungContentType(String noidungContentType) {
        this.noidungContentType = noidungContentType;
    }

    public LocalDate getThoigian() {
        return thoigian;
    }

    public Upfile thoigian(LocalDate thoigian) {
        this.thoigian = thoigian;
        return this;
    }

    public void setThoigian(LocalDate thoigian) {
        this.thoigian = thoigian;
    }

    public Detai getDetai() {
        return detai;
    }

    public Upfile detai(Detai detai) {
        this.detai = detai;
        return this;
    }

    public void setDetai(Detai detai) {
        this.detai = detai;
    }

    public Tiendo getTiendo() {
        return tiendo;
    }

    public Upfile tiendo(Tiendo tiendo) {
        this.tiendo = tiendo;
        return this;
    }

    public void setTiendo(Tiendo tiendo) {
        this.tiendo = tiendo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Upfile)) {
            return false;
        }
        return id != null && id.equals(((Upfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Upfile{" +
            "id=" + getId() +
            ", mota='" + getMota() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", noidungContentType='" + getNoidungContentType() + "'" +
            ", thoigian='" + getThoigian() + "'" +
            "}";
    }
}
