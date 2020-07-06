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
 * Criteria class for the {@link com.api.backend.domain.Danhgia} entity. This class is used
 * in {@link com.api.backend.web.rest.DanhgiaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /danhgias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DanhgiaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ma;

    private StringFilter ten;

    private IntegerFilter diem;

    private StringFilter noidung;

    private IntegerFilter sudung;

    private LongFilter danhgiaCTId;

    private LongFilter detaiId;

    public DanhgiaCriteria(){
    }

    public DanhgiaCriteria(DanhgiaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.ma = other.ma == null ? null : other.ma.copy();
        this.ten = other.ten == null ? null : other.ten.copy();
        this.diem = other.diem == null ? null : other.diem.copy();
        this.noidung = other.noidung == null ? null : other.noidung.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.danhgiaCTId = other.danhgiaCTId == null ? null : other.danhgiaCTId.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
    }

    @Override
    public DanhgiaCriteria copy() {
        return new DanhgiaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMa() {
        return ma;
    }

    public void setMa(StringFilter ma) {
        this.ma = ma;
    }

    public StringFilter getTen() {
        return ten;
    }

    public void setTen(StringFilter ten) {
        this.ten = ten;
    }

    public IntegerFilter getDiem() {
        return diem;
    }

    public void setDiem(IntegerFilter diem) {
        this.diem = diem;
    }

    public StringFilter getNoidung() {
        return noidung;
    }

    public void setNoidung(StringFilter noidung) {
        this.noidung = noidung;
    }

    public IntegerFilter getSudung() {
        return sudung;
    }

    public void setSudung(IntegerFilter sudung) {
        this.sudung = sudung;
    }

    public LongFilter getDanhgiaCTId() {
        return danhgiaCTId;
    }

    public void setDanhgiaCTId(LongFilter danhgiaCTId) {
        this.danhgiaCTId = danhgiaCTId;
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
        final DanhgiaCriteria that = (DanhgiaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ma, that.ma) &&
            Objects.equals(ten, that.ten) &&
            Objects.equals(diem, that.diem) &&
            Objects.equals(noidung, that.noidung) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(danhgiaCTId, that.danhgiaCTId) &&
            Objects.equals(detaiId, that.detaiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ma,
        ten,
        diem,
        noidung,
        sudung,
        danhgiaCTId,
        detaiId
        );
    }

    @Override
    public String toString() {
        return "DanhgiaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ma != null ? "ma=" + ma + ", " : "") +
                (ten != null ? "ten=" + ten + ", " : "") +
                (diem != null ? "diem=" + diem + ", " : "") +
                (noidung != null ? "noidung=" + noidung + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (danhgiaCTId != null ? "danhgiaCTId=" + danhgiaCTId + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
            "}";
    }

}
