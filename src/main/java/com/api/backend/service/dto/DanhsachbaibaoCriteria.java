package com.api.backend.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.api.backend.domain.Danhsachbaibao} entity. This class is used
 * in {@link com.api.backend.web.rest.DanhsachbaibaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /danhsachbaibaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DanhsachbaibaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tenbaibao;

    private IntegerFilter phanloai;

    private StringFilter tenhoithao;

    private IntegerFilter namxuatban;

    private IntegerFilter thangxuatban;

    private IntegerFilter danhmuc;

    private StringFilter iffff;

    private StringFilter hindex;

    private IntegerFilter xeploai;

    private IntegerFilter rankbaibao;

    private StringFilter volbaibao;

    private IntegerFilter sobaibao;

    private StringFilter ppbaibao;

    private StringFilter link;

    private StringFilter ghichu;

    private StringFilter tacgiachinh;

    private StringFilter tacgiakhac;

    private StringFilter tendetai;

    private LongFilter detaiId;

    public DanhsachbaibaoCriteria(){
    }

    public DanhsachbaibaoCriteria(DanhsachbaibaoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tenbaibao = other.tenbaibao == null ? null : other.tenbaibao.copy();
        this.phanloai = other.phanloai == null ? null : other.phanloai.copy();
        this.tenhoithao = other.tenhoithao == null ? null : other.tenhoithao.copy();
        this.namxuatban = other.namxuatban == null ? null : other.namxuatban.copy();
        this.thangxuatban = other.thangxuatban == null ? null : other.thangxuatban.copy();
        this.danhmuc = other.danhmuc == null ? null : other.danhmuc.copy();
        this.iffff = other.iffff == null ? null : other.iffff.copy();
        this.hindex = other.hindex == null ? null : other.hindex.copy();
        this.xeploai = other.xeploai == null ? null : other.xeploai.copy();
        this.rankbaibao = other.rankbaibao == null ? null : other.rankbaibao.copy();
        this.volbaibao = other.volbaibao == null ? null : other.volbaibao.copy();
        this.sobaibao = other.sobaibao == null ? null : other.sobaibao.copy();
        this.ppbaibao = other.ppbaibao == null ? null : other.ppbaibao.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.ghichu = other.ghichu == null ? null : other.ghichu.copy();
        this.tacgiachinh = other.tacgiachinh == null ? null : other.tacgiachinh.copy();
        this.tacgiakhac = other.tacgiakhac == null ? null : other.tacgiakhac.copy();
        this.tendetai = other.tendetai == null ? null : other.tendetai.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
    }

    @Override
    public DanhsachbaibaoCriteria copy() {
        return new DanhsachbaibaoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTenbaibao() {
        return tenbaibao;
    }

    public void setTenbaibao(StringFilter tenbaibao) {
        this.tenbaibao = tenbaibao;
    }

    public IntegerFilter getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(IntegerFilter phanloai) {
        this.phanloai = phanloai;
    }

    public StringFilter getTenhoithao() {
        return tenhoithao;
    }

    public void setTenhoithao(StringFilter tenhoithao) {
        this.tenhoithao = tenhoithao;
    }

    public IntegerFilter getNamxuatban() {
        return namxuatban;
    }

    public void setNamxuatban(IntegerFilter namxuatban) {
        this.namxuatban = namxuatban;
    }

    public IntegerFilter getThangxuatban() {
        return thangxuatban;
    }

    public void setThangxuatban(IntegerFilter thangxuatban) {
        this.thangxuatban = thangxuatban;
    }

    public IntegerFilter getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(IntegerFilter danhmuc) {
        this.danhmuc = danhmuc;
    }

    public StringFilter getIffff() {
        return iffff;
    }

    public void setIffff(StringFilter iffff) {
        this.iffff = iffff;
    }

    public StringFilter getHindex() {
        return hindex;
    }

    public void setHindex(StringFilter hindex) {
        this.hindex = hindex;
    }

    public IntegerFilter getXeploai() {
        return xeploai;
    }

    public void setXeploai(IntegerFilter xeploai) {
        this.xeploai = xeploai;
    }

    public IntegerFilter getRankbaibao() {
        return rankbaibao;
    }

    public void setRankbaibao(IntegerFilter rankbaibao) {
        this.rankbaibao = rankbaibao;
    }

    public StringFilter getVolbaibao() {
        return volbaibao;
    }

    public void setVolbaibao(StringFilter volbaibao) {
        this.volbaibao = volbaibao;
    }

    public IntegerFilter getSobaibao() {
        return sobaibao;
    }

    public void setSobaibao(IntegerFilter sobaibao) {
        this.sobaibao = sobaibao;
    }

    public StringFilter getPpbaibao() {
        return ppbaibao;
    }

    public void setPpbaibao(StringFilter ppbaibao) {
        this.ppbaibao = ppbaibao;
    }

    public StringFilter getLink() {
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public StringFilter getGhichu() {
        return ghichu;
    }

    public void setGhichu(StringFilter ghichu) {
        this.ghichu = ghichu;
    }

    public StringFilter getTacgiachinh() {
        return tacgiachinh;
    }

    public void setTacgiachinh(StringFilter tacgiachinh) {
        this.tacgiachinh = tacgiachinh;
    }

    public StringFilter getTacgiakhac() {
        return tacgiakhac;
    }

    public void setTacgiakhac(StringFilter tacgiakhac) {
        this.tacgiakhac = tacgiakhac;
    }

    public StringFilter getTendetai() {
        return tendetai;
    }

    public void setTendetai(StringFilter tendetai) {
        this.tendetai = tendetai;
    }

    public LongFilter getDetaiId() {
        return detaiId;
    }

    public void setDetaiId(LongFilter detaiId) {
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
        final DanhsachbaibaoCriteria that = (DanhsachbaibaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tenbaibao, that.tenbaibao) &&
            Objects.equals(phanloai, that.phanloai) &&
            Objects.equals(tenhoithao, that.tenhoithao) &&
            Objects.equals(namxuatban, that.namxuatban) &&
            Objects.equals(thangxuatban, that.thangxuatban) &&
            Objects.equals(danhmuc, that.danhmuc) &&
            Objects.equals(iffff, that.iffff) &&
            Objects.equals(hindex, that.hindex) &&
            Objects.equals(xeploai, that.xeploai) &&
            Objects.equals(rankbaibao, that.rankbaibao) &&
            Objects.equals(volbaibao, that.volbaibao) &&
            Objects.equals(sobaibao, that.sobaibao) &&
            Objects.equals(ppbaibao, that.ppbaibao) &&
            Objects.equals(link, that.link) &&
            Objects.equals(ghichu, that.ghichu) &&
            Objects.equals(tacgiachinh, that.tacgiachinh) &&
            Objects.equals(tacgiakhac, that.tacgiakhac) &&
            Objects.equals(tendetai, that.tendetai) &&
            Objects.equals(detaiId, that.detaiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tenbaibao,
        phanloai,
        tenhoithao,
        namxuatban,
        thangxuatban,
        danhmuc,
        iffff,
        hindex,
        xeploai,
        rankbaibao,
        volbaibao,
        sobaibao,
        ppbaibao,
        link,
        ghichu,
        tacgiachinh,
        tacgiakhac,
        tendetai,
        detaiId
        );
    }

    @Override
    public String toString() {
        return "DanhsachbaibaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tenbaibao != null ? "tenbaibao=" + tenbaibao + ", " : "") +
                (phanloai != null ? "phanloai=" + phanloai + ", " : "") +
                (tenhoithao != null ? "tenhoithao=" + tenhoithao + ", " : "") +
                (namxuatban != null ? "namxuatban=" + namxuatban + ", " : "") +
                (thangxuatban != null ? "thangxuatban=" + thangxuatban + ", " : "") +
                (danhmuc != null ? "danhmuc=" + danhmuc + ", " : "") +
                (iffff != null ? "iffff=" + iffff + ", " : "") +
                (hindex != null ? "hindex=" + hindex + ", " : "") +
                (xeploai != null ? "xeploai=" + xeploai + ", " : "") +
                (rankbaibao != null ? "rankbaibao=" + rankbaibao + ", " : "") +
                (volbaibao != null ? "volbaibao=" + volbaibao + ", " : "") +
                (sobaibao != null ? "sobaibao=" + sobaibao + ", " : "") +
                (ppbaibao != null ? "ppbaibao=" + ppbaibao + ", " : "") +
                (link != null ? "link=" + link + ", " : "") +
                (ghichu != null ? "ghichu=" + ghichu + ", " : "") +
                (tacgiachinh != null ? "tacgiachinh=" + tacgiachinh + ", " : "") +
                (tacgiakhac != null ? "tacgiakhac=" + tacgiakhac + ", " : "") +
                (tendetai != null ? "tendetai=" + tendetai + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
            "}";
    }

}
