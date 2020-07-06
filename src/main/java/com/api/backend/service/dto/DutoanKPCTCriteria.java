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
 * Criteria class for the {@link com.api.backend.domain.DutoanKPCT} entity. This class is used
 * in {@link com.api.backend.web.rest.DutoanKPCTResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dutoan-kpcts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DutoanKPCTCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter noidung;

    private IntegerFilter soluong;

    private IntegerFilter mucchi;

    private IntegerFilter tong;

    private IntegerFilter sudung;

    private LongFilter dutoanKPId;

    private LongFilter noidungDTId;

    public DutoanKPCTCriteria(){
    }

    public DutoanKPCTCriteria(DutoanKPCTCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.noidung = other.noidung == null ? null : other.noidung.copy();
        this.soluong = other.soluong == null ? null : other.soluong.copy();
        this.mucchi = other.mucchi == null ? null : other.mucchi.copy();
        this.tong = other.tong == null ? null : other.tong.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.dutoanKPId = other.dutoanKPId == null ? null : other.dutoanKPId.copy();
        this.noidungDTId = other.noidungDTId == null ? null : other.noidungDTId.copy();
    }

    @Override
    public DutoanKPCTCriteria copy() {
        return new DutoanKPCTCriteria(this);
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

    public IntegerFilter getSoluong() {
        return soluong;
    }

    public void setSoluong(IntegerFilter soluong) {
        this.soluong = soluong;
    }

    public IntegerFilter getMucchi() {
        return mucchi;
    }

    public void setMucchi(IntegerFilter mucchi) {
        this.mucchi = mucchi;
    }

    public IntegerFilter getTong() {
        return tong;
    }

    public void setTong(IntegerFilter tong) {
        this.tong = tong;
    }

    public IntegerFilter getSudung() {
        return sudung;
    }

    public void setSudung(IntegerFilter sudung) {
        this.sudung = sudung;
    }

    public LongFilter getDutoanKPId() {
        return dutoanKPId;
    }

    public void setDutoanKPId(LongFilter dutoanKPId) {
        this.dutoanKPId = dutoanKPId;
    }

    public LongFilter getNoidungDTId() {
        return noidungDTId;
    }

    public void setNoidungDTId(LongFilter noidungDTId) {
        this.noidungDTId = noidungDTId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DutoanKPCTCriteria that = (DutoanKPCTCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(noidung, that.noidung) &&
            Objects.equals(soluong, that.soluong) &&
            Objects.equals(mucchi, that.mucchi) &&
            Objects.equals(tong, that.tong) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(dutoanKPId, that.dutoanKPId) &&
            Objects.equals(noidungDTId, that.noidungDTId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        noidung,
        soluong,
        mucchi,
        tong,
        sudung,
        dutoanKPId,
        noidungDTId
        );
    }

    @Override
    public String toString() {
        return "DutoanKPCTCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (noidung != null ? "noidung=" + noidung + ", " : "") +
                (soluong != null ? "soluong=" + soluong + ", " : "") +
                (mucchi != null ? "mucchi=" + mucchi + ", " : "") +
                (tong != null ? "tong=" + tong + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (dutoanKPId != null ? "dutoanKPId=" + dutoanKPId + ", " : "") +
                (noidungDTId != null ? "noidungDTId=" + noidungDTId + ", " : "") +
            "}";
    }

}
