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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.api.backend.domain.Tiendo} entity. This class is used
 * in {@link com.api.backend.web.rest.TiendoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tiendos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TiendoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter matiendo;

    private StringFilter kybaocao;

    private StringFilter noidung;

    private LocalDateFilter thoigianbatdau;

    private LocalDateFilter thoigianketthuc;

    private IntegerFilter khoiluonghoanthanh;

    private StringFilter ghichu;

    private IntegerFilter sudung;

    private LongFilter upfileId;

    private LongFilter detaiId;

    public TiendoCriteria(){
    }

    public TiendoCriteria(TiendoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.matiendo = other.matiendo == null ? null : other.matiendo.copy();
        this.kybaocao = other.kybaocao == null ? null : other.kybaocao.copy();
        this.noidung = other.noidung == null ? null : other.noidung.copy();
        this.thoigianbatdau = other.thoigianbatdau == null ? null : other.thoigianbatdau.copy();
        this.thoigianketthuc = other.thoigianketthuc == null ? null : other.thoigianketthuc.copy();
        this.khoiluonghoanthanh = other.khoiluonghoanthanh == null ? null : other.khoiluonghoanthanh.copy();
        this.ghichu = other.ghichu == null ? null : other.ghichu.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.upfileId = other.upfileId == null ? null : other.upfileId.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
    }

    @Override
    public TiendoCriteria copy() {
        return new TiendoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMatiendo() {
        return matiendo;
    }

    public void setMatiendo(StringFilter matiendo) {
        this.matiendo = matiendo;
    }

    public StringFilter getKybaocao() {
        return kybaocao;
    }

    public void setKybaocao(StringFilter kybaocao) {
        this.kybaocao = kybaocao;
    }

    public StringFilter getNoidung() {
        return noidung;
    }

    public void setNoidung(StringFilter noidung) {
        this.noidung = noidung;
    }

    public LocalDateFilter getThoigianbatdau() {
        return thoigianbatdau;
    }

    public void setThoigianbatdau(LocalDateFilter thoigianbatdau) {
        this.thoigianbatdau = thoigianbatdau;
    }

    public LocalDateFilter getThoigianketthuc() {
        return thoigianketthuc;
    }

    public void setThoigianketthuc(LocalDateFilter thoigianketthuc) {
        this.thoigianketthuc = thoigianketthuc;
    }

    public IntegerFilter getKhoiluonghoanthanh() {
        return khoiluonghoanthanh;
    }

    public void setKhoiluonghoanthanh(IntegerFilter khoiluonghoanthanh) {
        this.khoiluonghoanthanh = khoiluonghoanthanh;
    }

    public StringFilter getGhichu() {
        return ghichu;
    }

    public void setGhichu(StringFilter ghichu) {
        this.ghichu = ghichu;
    }

    public IntegerFilter getSudung() {
        return sudung;
    }

    public void setSudung(IntegerFilter sudung) {
        this.sudung = sudung;
    }

    public LongFilter getUpfileId() {
        return upfileId;
    }

    public void setUpfileId(LongFilter upfileId) {
        this.upfileId = upfileId;
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
        final TiendoCriteria that = (TiendoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(matiendo, that.matiendo) &&
            Objects.equals(kybaocao, that.kybaocao) &&
            Objects.equals(noidung, that.noidung) &&
            Objects.equals(thoigianbatdau, that.thoigianbatdau) &&
            Objects.equals(thoigianketthuc, that.thoigianketthuc) &&
            Objects.equals(khoiluonghoanthanh, that.khoiluonghoanthanh) &&
            Objects.equals(ghichu, that.ghichu) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(upfileId, that.upfileId) &&
            Objects.equals(detaiId, that.detaiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        matiendo,
        kybaocao,
        noidung,
        thoigianbatdau,
        thoigianketthuc,
        khoiluonghoanthanh,
        ghichu,
        sudung,
        upfileId,
        detaiId
        );
    }

    @Override
    public String toString() {
        return "TiendoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (matiendo != null ? "matiendo=" + matiendo + ", " : "") +
                (kybaocao != null ? "kybaocao=" + kybaocao + ", " : "") +
                (noidung != null ? "noidung=" + noidung + ", " : "") +
                (thoigianbatdau != null ? "thoigianbatdau=" + thoigianbatdau + ", " : "") +
                (thoigianketthuc != null ? "thoigianketthuc=" + thoigianketthuc + ", " : "") +
                (khoiluonghoanthanh != null ? "khoiluonghoanthanh=" + khoiluonghoanthanh + ", " : "") +
                (ghichu != null ? "ghichu=" + ghichu + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (upfileId != null ? "upfileId=" + upfileId + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
            "}";
    }

}
