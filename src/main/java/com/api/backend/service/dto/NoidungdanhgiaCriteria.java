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
 * Criteria class for the {@link com.api.backend.domain.Noidungdanhgia} entity. This class is used
 * in {@link com.api.backend.web.rest.NoidungdanhgiaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /noidungdanhgias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NoidungdanhgiaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter noidung;

    private IntegerFilter sudung;

    private LongFilter danhgiaCTId;

    public NoidungdanhgiaCriteria(){
    }

    public NoidungdanhgiaCriteria(NoidungdanhgiaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.noidung = other.noidung == null ? null : other.noidung.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.danhgiaCTId = other.danhgiaCTId == null ? null : other.danhgiaCTId.copy();
    }

    @Override
    public NoidungdanhgiaCriteria copy() {
        return new NoidungdanhgiaCriteria(this);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NoidungdanhgiaCriteria that = (NoidungdanhgiaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(noidung, that.noidung) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(danhgiaCTId, that.danhgiaCTId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        noidung,
        sudung,
        danhgiaCTId
        );
    }

    @Override
    public String toString() {
        return "NoidungdanhgiaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (noidung != null ? "noidung=" + noidung + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (danhgiaCTId != null ? "danhgiaCTId=" + danhgiaCTId + ", " : "") +
            "}";
    }

}
