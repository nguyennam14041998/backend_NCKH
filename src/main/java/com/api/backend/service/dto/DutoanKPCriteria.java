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
 * Criteria class for the {@link com.api.backend.domain.DutoanKP} entity. This class is used
 * in {@link com.api.backend.web.rest.DutoanKPResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dutoan-kps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DutoanKPCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter madutoan;

    private StringFilter tendutoan;

    private StringFilter noidung;

    private IntegerFilter sudung;

    private LongFilter dutoanKPCTId;

    private LongFilter detaiId;

    public DutoanKPCriteria(){
    }

    public DutoanKPCriteria(DutoanKPCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.madutoan = other.madutoan == null ? null : other.madutoan.copy();
        this.tendutoan = other.tendutoan == null ? null : other.tendutoan.copy();
        this.noidung = other.noidung == null ? null : other.noidung.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.dutoanKPCTId = other.dutoanKPCTId == null ? null : other.dutoanKPCTId.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
    }

    @Override
    public DutoanKPCriteria copy() {
        return new DutoanKPCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMadutoan() {
        return madutoan;
    }

    public void setMadutoan(StringFilter madutoan) {
        this.madutoan = madutoan;
    }

    public StringFilter getTendutoan() {
        return tendutoan;
    }

    public void setTendutoan(StringFilter tendutoan) {
        this.tendutoan = tendutoan;
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

    public LongFilter getDutoanKPCTId() {
        return dutoanKPCTId;
    }

    public void setDutoanKPCTId(LongFilter dutoanKPCTId) {
        this.dutoanKPCTId = dutoanKPCTId;
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
        final DutoanKPCriteria that = (DutoanKPCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(madutoan, that.madutoan) &&
            Objects.equals(tendutoan, that.tendutoan) &&
            Objects.equals(noidung, that.noidung) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(dutoanKPCTId, that.dutoanKPCTId) &&
            Objects.equals(detaiId, that.detaiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        madutoan,
        tendutoan,
        noidung,
        sudung,
        dutoanKPCTId,
        detaiId
        );
    }

    @Override
    public String toString() {
        return "DutoanKPCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (madutoan != null ? "madutoan=" + madutoan + ", " : "") +
                (tendutoan != null ? "tendutoan=" + tendutoan + ", " : "") +
                (noidung != null ? "noidung=" + noidung + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (dutoanKPCTId != null ? "dutoanKPCTId=" + dutoanKPCTId + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
            "}";
    }

}
