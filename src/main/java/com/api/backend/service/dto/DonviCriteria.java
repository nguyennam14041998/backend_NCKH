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
 * Criteria class for the {@link com.api.backend.domain.Donvi} entity. This class is used
 * in {@link com.api.backend.web.rest.DonviResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /donvis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DonviCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter madv;

    private StringFilter tendv;

    private IntegerFilter dienthoai;

    private IntegerFilter fax;

    private StringFilter email;

    private IntegerFilter sudung;

    private LongFilter nhansuId;

    public DonviCriteria(){
    }

    public DonviCriteria(DonviCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.madv = other.madv == null ? null : other.madv.copy();
        this.tendv = other.tendv == null ? null : other.tendv.copy();
        this.dienthoai = other.dienthoai == null ? null : other.dienthoai.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.nhansuId = other.nhansuId == null ? null : other.nhansuId.copy();
    }

    @Override
    public DonviCriteria copy() {
        return new DonviCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMadv() {
        return madv;
    }

    public void setMadv(StringFilter madv) {
        this.madv = madv;
    }

    public StringFilter getTendv() {
        return tendv;
    }

    public void setTendv(StringFilter tendv) {
        this.tendv = tendv;
    }

    public IntegerFilter getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(IntegerFilter dienthoai) {
        this.dienthoai = dienthoai;
    }

    public IntegerFilter getFax() {
        return fax;
    }

    public void setFax(IntegerFilter fax) {
        this.fax = fax;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public IntegerFilter getSudung() {
        return sudung;
    }

    public void setSudung(IntegerFilter sudung) {
        this.sudung = sudung;
    }

    public LongFilter getNhansuId() {
        return nhansuId;
    }

    public void setNhansuId(LongFilter nhansuId) {
        this.nhansuId = nhansuId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DonviCriteria that = (DonviCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(madv, that.madv) &&
            Objects.equals(tendv, that.tendv) &&
            Objects.equals(dienthoai, that.dienthoai) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(email, that.email) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(nhansuId, that.nhansuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        madv,
        tendv,
        dienthoai,
        fax,
        email,
        sudung,
        nhansuId
        );
    }

    @Override
    public String toString() {
        return "DonviCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (madv != null ? "madv=" + madv + ", " : "") +
                (tendv != null ? "tendv=" + tendv + ", " : "") +
                (dienthoai != null ? "dienthoai=" + dienthoai + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (nhansuId != null ? "nhansuId=" + nhansuId + ", " : "") +
            "}";
    }

}
