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
 * Criteria class for the {@link com.api.backend.domain.ThanhvienHD} entity. This class is used
 * in {@link com.api.backend.web.rest.ThanhvienHDResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /thanhvien-hds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ThanhvienHDCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ten;

    private StringFilter donvi;

    private IntegerFilter trachnhiem;

    private IntegerFilter sudung;

    private LongFilter hoidongdanhgiaId;

    public ThanhvienHDCriteria(){
    }

    public ThanhvienHDCriteria(ThanhvienHDCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.ten = other.ten == null ? null : other.ten.copy();
        this.donvi = other.donvi == null ? null : other.donvi.copy();
        this.trachnhiem = other.trachnhiem == null ? null : other.trachnhiem.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.hoidongdanhgiaId = other.hoidongdanhgiaId == null ? null : other.hoidongdanhgiaId.copy();
    }

    @Override
    public ThanhvienHDCriteria copy() {
        return new ThanhvienHDCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTen() {
        return ten;
    }

    public void setTen(StringFilter ten) {
        this.ten = ten;
    }

    public StringFilter getDonvi() {
        return donvi;
    }

    public void setDonvi(StringFilter donvi) {
        this.donvi = donvi;
    }

    public IntegerFilter getTrachnhiem() {
        return trachnhiem;
    }

    public void setTrachnhiem(IntegerFilter trachnhiem) {
        this.trachnhiem = trachnhiem;
    }

    public IntegerFilter getSudung() {
        return sudung;
    }

    public void setSudung(IntegerFilter sudung) {
        this.sudung = sudung;
    }

    public LongFilter getHoidongdanhgiaId() {
        return hoidongdanhgiaId;
    }

    public void setHoidongdanhgiaId(LongFilter hoidongdanhgiaId) {
        this.hoidongdanhgiaId = hoidongdanhgiaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ThanhvienHDCriteria that = (ThanhvienHDCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ten, that.ten) &&
            Objects.equals(donvi, that.donvi) &&
            Objects.equals(trachnhiem, that.trachnhiem) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(hoidongdanhgiaId, that.hoidongdanhgiaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ten,
        donvi,
        trachnhiem,
        sudung,
        hoidongdanhgiaId
        );
    }

    @Override
    public String toString() {
        return "ThanhvienHDCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ten != null ? "ten=" + ten + ", " : "") +
                (donvi != null ? "donvi=" + donvi + ", " : "") +
                (trachnhiem != null ? "trachnhiem=" + trachnhiem + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (hoidongdanhgiaId != null ? "hoidongdanhgiaId=" + hoidongdanhgiaId + ", " : "") +
            "}";
    }

}
