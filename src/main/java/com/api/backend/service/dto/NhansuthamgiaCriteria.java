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
 * Criteria class for the {@link com.api.backend.domain.Nhansuthamgia} entity. This class is used
 * in {@link com.api.backend.web.rest.NhansuthamgiaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nhansuthamgias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NhansuthamgiaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sudung;

    private LongFilter nhansuId;

    private LongFilter detaiId;

    public NhansuthamgiaCriteria(){
    }

    public NhansuthamgiaCriteria(NhansuthamgiaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.nhansuId = other.nhansuId == null ? null : other.nhansuId.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
    }

    @Override
    public NhansuthamgiaCriteria copy() {
        return new NhansuthamgiaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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
        final NhansuthamgiaCriteria that = (NhansuthamgiaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(nhansuId, that.nhansuId) &&
            Objects.equals(detaiId, that.detaiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sudung,
        nhansuId,
        detaiId
        );
    }

    @Override
    public String toString() {
        return "NhansuthamgiaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (nhansuId != null ? "nhansuId=" + nhansuId + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
            "}";
    }

}
