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
 * Criteria class for the {@link com.api.backend.domain.DanhgiaCT} entity. This class is used
 * in {@link com.api.backend.web.rest.DanhgiaCTResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /danhgia-cts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DanhgiaCTCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter noidung;

    private IntegerFilter diem;

    private IntegerFilter sudung;

    private LongFilter danhgiaId;

    private LongFilter noidungdanhgiaId;

    public DanhgiaCTCriteria(){
    }

    public DanhgiaCTCriteria(DanhgiaCTCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.noidung = other.noidung == null ? null : other.noidung.copy();
        this.diem = other.diem == null ? null : other.diem.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.danhgiaId = other.danhgiaId == null ? null : other.danhgiaId.copy();
        this.noidungdanhgiaId = other.noidungdanhgiaId == null ? null : other.noidungdanhgiaId.copy();
    }

    @Override
    public DanhgiaCTCriteria copy() {
        return new DanhgiaCTCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNoidung() {
        return noidung;
    }

    public void setNoidung(StringFilter noidung) {
        this.noidung = noidung;
    }

    public IntegerFilter getDiem() {
        return diem;
    }

    public void setDiem(IntegerFilter diem) {
        this.diem = diem;
    }

    public IntegerFilter getSudung() {
        return sudung;
    }

    public void setSudung(IntegerFilter sudung) {
        this.sudung = sudung;
    }

    public LongFilter getDanhgiaId() {
        return danhgiaId;
    }

    public void setDanhgiaId(LongFilter danhgiaId) {
        this.danhgiaId = danhgiaId;
    }

    public LongFilter getNoidungdanhgiaId() {
        return noidungdanhgiaId;
    }

    public void setNoidungdanhgiaId(LongFilter noidungdanhgiaId) {
        this.noidungdanhgiaId = noidungdanhgiaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DanhgiaCTCriteria that = (DanhgiaCTCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(noidung, that.noidung) &&
            Objects.equals(diem, that.diem) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(danhgiaId, that.danhgiaId) &&
            Objects.equals(noidungdanhgiaId, that.noidungdanhgiaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        noidung,
        diem,
        sudung,
        danhgiaId,
        noidungdanhgiaId
        );
    }

    @Override
    public String toString() {
        return "DanhgiaCTCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (noidung != null ? "noidung=" + noidung + ", " : "") +
                (diem != null ? "diem=" + diem + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (danhgiaId != null ? "danhgiaId=" + danhgiaId + ", " : "") +
                (noidungdanhgiaId != null ? "noidungdanhgiaId=" + noidungdanhgiaId + ", " : "") +
            "}";
    }

}
