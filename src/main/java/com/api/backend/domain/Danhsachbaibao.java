package com.api.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Danhsachbaibao.
 */
@Entity
@Table(name = "danhsachbaibao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Danhsachbaibao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenbaibao")
    private String tenbaibao;

    @Column(name = "phanloai")
    private Integer phanloai;

    @Column(name = "tenhoithao")
    private String tenhoithao;

    @Column(name = "namxuatban")
    private Integer namxuatban;

    @Column(name = "thangxuatban")
    private Integer thangxuatban;

    @Column(name = "danhmuc")
    private Integer danhmuc;

    @Column(name = "iffff")
    private String iffff;

    @Column(name = "hindex")
    private String hindex;

    @Column(name = "xeploai")
    private Integer xeploai;

    @Column(name = "rankbaibao")
    private Integer rankbaibao;

    @Column(name = "volbaibao")
    private String volbaibao;

    @Column(name = "sobaibao")
    private Integer sobaibao;

    @Column(name = "ppbaibao")
    private String ppbaibao;

    @Column(name = "link")
    private String link;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "tacgiachinh")
    private String tacgiachinh;

    @Column(name = "tacgiakhac")
    private String tacgiakhac;

    @Column(name = "tendetai")
    private String tendetai;

    @ManyToOne
    @JsonIgnoreProperties("danhsachbaibaos")
    private Detai detai;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenbaibao() {
        return tenbaibao;
    }

    public Danhsachbaibao tenbaibao(String tenbaibao) {
        this.tenbaibao = tenbaibao;
        return this;
    }

    public void setTenbaibao(String tenbaibao) {
        this.tenbaibao = tenbaibao;
    }

    public Integer getPhanloai() {
        return phanloai;
    }

    public Danhsachbaibao phanloai(Integer phanloai) {
        this.phanloai = phanloai;
        return this;
    }

    public void setPhanloai(Integer phanloai) {
        this.phanloai = phanloai;
    }

    public String getTenhoithao() {
        return tenhoithao;
    }

    public Danhsachbaibao tenhoithao(String tenhoithao) {
        this.tenhoithao = tenhoithao;
        return this;
    }

    public void setTenhoithao(String tenhoithao) {
        this.tenhoithao = tenhoithao;
    }

    public Integer getNamxuatban() {
        return namxuatban;
    }

    public Danhsachbaibao namxuatban(Integer namxuatban) {
        this.namxuatban = namxuatban;
        return this;
    }

    public void setNamxuatban(Integer namxuatban) {
        this.namxuatban = namxuatban;
    }

    public Integer getThangxuatban() {
        return thangxuatban;
    }

    public Danhsachbaibao thangxuatban(Integer thangxuatban) {
        this.thangxuatban = thangxuatban;
        return this;
    }

    public void setThangxuatban(Integer thangxuatban) {
        this.thangxuatban = thangxuatban;
    }

    public Integer getDanhmuc() {
        return danhmuc;
    }

    public Danhsachbaibao danhmuc(Integer danhmuc) {
        this.danhmuc = danhmuc;
        return this;
    }

    public void setDanhmuc(Integer danhmuc) {
        this.danhmuc = danhmuc;
    }

    public String getIffff() {
        return iffff;
    }

    public Danhsachbaibao iffff(String iffff) {
        this.iffff = iffff;
        return this;
    }

    public void setIffff(String iffff) {
        this.iffff = iffff;
    }

    public String getHindex() {
        return hindex;
    }

    public Danhsachbaibao hindex(String hindex) {
        this.hindex = hindex;
        return this;
    }

    public void setHindex(String hindex) {
        this.hindex = hindex;
    }

    public Integer getXeploai() {
        return xeploai;
    }

    public Danhsachbaibao xeploai(Integer xeploai) {
        this.xeploai = xeploai;
        return this;
    }

    public void setXeploai(Integer xeploai) {
        this.xeploai = xeploai;
    }

    public Integer getRankbaibao() {
        return rankbaibao;
    }

    public Danhsachbaibao rankbaibao(Integer rankbaibao) {
        this.rankbaibao = rankbaibao;
        return this;
    }

    public void setRankbaibao(Integer rankbaibao) {
        this.rankbaibao = rankbaibao;
    }

    public String getVolbaibao() {
        return volbaibao;
    }

    public Danhsachbaibao volbaibao(String volbaibao) {
        this.volbaibao = volbaibao;
        return this;
    }

    public void setVolbaibao(String volbaibao) {
        this.volbaibao = volbaibao;
    }

    public Integer getSobaibao() {
        return sobaibao;
    }

    public Danhsachbaibao sobaibao(Integer sobaibao) {
        this.sobaibao = sobaibao;
        return this;
    }

    public void setSobaibao(Integer sobaibao) {
        this.sobaibao = sobaibao;
    }

    public String getPpbaibao() {
        return ppbaibao;
    }

    public Danhsachbaibao ppbaibao(String ppbaibao) {
        this.ppbaibao = ppbaibao;
        return this;
    }

    public void setPpbaibao(String ppbaibao) {
        this.ppbaibao = ppbaibao;
    }

    public String getLink() {
        return link;
    }

    public Danhsachbaibao link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGhichu() {
        return ghichu;
    }

    public Danhsachbaibao ghichu(String ghichu) {
        this.ghichu = ghichu;
        return this;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTacgiachinh() {
        return tacgiachinh;
    }

    public Danhsachbaibao tacgiachinh(String tacgiachinh) {
        this.tacgiachinh = tacgiachinh;
        return this;
    }

    public void setTacgiachinh(String tacgiachinh) {
        this.tacgiachinh = tacgiachinh;
    }

    public String getTacgiakhac() {
        return tacgiakhac;
    }

    public Danhsachbaibao tacgiakhac(String tacgiakhac) {
        this.tacgiakhac = tacgiakhac;
        return this;
    }

    public void setTacgiakhac(String tacgiakhac) {
        this.tacgiakhac = tacgiakhac;
    }

    public String getTendetai() {
        return tendetai;
    }

    public Danhsachbaibao tendetai(String tendetai) {
        this.tendetai = tendetai;
        return this;
    }

    public void setTendetai(String tendetai) {
        this.tendetai = tendetai;
    }

    public Detai getDetai() {
        return detai;
    }

    public Danhsachbaibao detai(Detai detai) {
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
        if (!(o instanceof Danhsachbaibao)) {
            return false;
        }
        return id != null && id.equals(((Danhsachbaibao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Danhsachbaibao{" +
            "id=" + getId() +
            ", tenbaibao='" + getTenbaibao() + "'" +
            ", phanloai=" + getPhanloai() +
            ", tenhoithao='" + getTenhoithao() + "'" +
            ", namxuatban=" + getNamxuatban() +
            ", thangxuatban=" + getThangxuatban() +
            ", danhmuc=" + getDanhmuc() +
            ", iffff='" + getIffff() + "'" +
            ", hindex='" + getHindex() + "'" +
            ", xeploai=" + getXeploai() +
            ", rankbaibao=" + getRankbaibao() +
            ", volbaibao='" + getVolbaibao() + "'" +
            ", sobaibao=" + getSobaibao() +
            ", ppbaibao='" + getPpbaibao() + "'" +
            ", link='" + getLink() + "'" +
            ", ghichu='" + getGhichu() + "'" +
            ", tacgiachinh='" + getTacgiachinh() + "'" +
            ", tacgiakhac='" + getTacgiakhac() + "'" +
            ", tendetai='" + getTendetai() + "'" +
            "}";
    }
}
