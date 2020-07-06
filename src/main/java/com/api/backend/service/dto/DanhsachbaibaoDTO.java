package com.api.backend.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Danhsachbaibao} entity.
 */
public class DanhsachbaibaoDTO implements Serializable {

    private Long id;

    private String tenbaibao;

    private Integer phanloai;

    private String tenhoithao;

    private Integer namxuatban;

    private Integer thangxuatban;

    private Integer danhmuc;

    private String iffff;

    private String hindex;

    private Integer xeploai;

    private Integer rankbaibao;

    private String volbaibao;

    private Integer sobaibao;

    private String ppbaibao;

    private String link;

    private String ghichu;

    private String tacgiachinh;

    private String tacgiakhac;

    private String tendetai;


    private Long detaiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenbaibao() {
        return tenbaibao;
    }

    public void setTenbaibao(String tenbaibao) {
        this.tenbaibao = tenbaibao;
    }

    public Integer getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(Integer phanloai) {
        this.phanloai = phanloai;
    }

    public String getTenhoithao() {
        return tenhoithao;
    }

    public void setTenhoithao(String tenhoithao) {
        this.tenhoithao = tenhoithao;
    }

    public Integer getNamxuatban() {
        return namxuatban;
    }

    public void setNamxuatban(Integer namxuatban) {
        this.namxuatban = namxuatban;
    }

    public Integer getThangxuatban() {
        return thangxuatban;
    }

    public void setThangxuatban(Integer thangxuatban) {
        this.thangxuatban = thangxuatban;
    }

    public Integer getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(Integer danhmuc) {
        this.danhmuc = danhmuc;
    }

    public String getIffff() {
        return iffff;
    }

    public void setIffff(String iffff) {
        this.iffff = iffff;
    }

    public String getHindex() {
        return hindex;
    }

    public void setHindex(String hindex) {
        this.hindex = hindex;
    }

    public Integer getXeploai() {
        return xeploai;
    }

    public void setXeploai(Integer xeploai) {
        this.xeploai = xeploai;
    }

    public Integer getRankbaibao() {
        return rankbaibao;
    }

    public void setRankbaibao(Integer rankbaibao) {
        this.rankbaibao = rankbaibao;
    }

    public String getVolbaibao() {
        return volbaibao;
    }

    public void setVolbaibao(String volbaibao) {
        this.volbaibao = volbaibao;
    }

    public Integer getSobaibao() {
        return sobaibao;
    }

    public void setSobaibao(Integer sobaibao) {
        this.sobaibao = sobaibao;
    }

    public String getPpbaibao() {
        return ppbaibao;
    }

    public void setPpbaibao(String ppbaibao) {
        this.ppbaibao = ppbaibao;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTacgiachinh() {
        return tacgiachinh;
    }

    public void setTacgiachinh(String tacgiachinh) {
        this.tacgiachinh = tacgiachinh;
    }

    public String getTacgiakhac() {
        return tacgiakhac;
    }

    public void setTacgiakhac(String tacgiakhac) {
        this.tacgiakhac = tacgiakhac;
    }

    public String getTendetai() {
        return tendetai;
    }

    public void setTendetai(String tendetai) {
        this.tendetai = tendetai;
    }

    public Long getDetaiId() {
        return detaiId;
    }

    public void setDetaiId(Long detaiId) {
        this.detaiId = detaiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DanhsachbaibaoDTO danhsachbaibaoDTO = (DanhsachbaibaoDTO) o;
        if (danhsachbaibaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), danhsachbaibaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DanhsachbaibaoDTO{" +
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
            ", detai=" + getDetaiId() +
            "}";
    }
}
