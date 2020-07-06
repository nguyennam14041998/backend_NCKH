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
 * Criteria class for the {@link com.api.backend.domain.Capdetai} entity. This class is used
 * in {@link com.api.backend.web.rest.CapdetaiResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /capdetais?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CapdetaiCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter macapdetai;

    private StringFilter tencapdetai;

    private IntegerFilter sudung;

    private LongFilter detaiId;

    public CapdetaiCriteria(){
    }

    public CapdetaiCriteria(CapdetaiCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.macapdetai = other.macapdetai == null ? null : other.macapdetai.copy();
        this.tencapdetai = other.tencapdetai == null ? null : other.tencapdetai.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
    }

    @Override
    public CapdetaiCriteria copy() {
        return new CapdetaiCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMacapdetai() {
        return macapdetai;
    }

    public void setMacapdetai(StringFilter macapdetai) {
        this.macapdetai = macapdetai;
    }

    public StringFilter getTencapdetai() {
        return tencapdetai;
    }

    public void setTencapdetai(StringFilter tencapdetai) {
        this.tencapdetai = tencapdetai;
    }

    public IntegerFilter getSudung() {
        return sudung;
    }

    public void setSudung(IntegerFilter sudung) {
        this.sudung = sudung;
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
        final CapdetaiCriteria that = (CapdetaiCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(macapdetai, that.macapdetai) &&
            Objects.equals(tencapdetai, that.tencapdetai) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(detaiId, that.detaiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        macapdetai,
        tencapdetai,
        sudung,
        detaiId
        );
    }

    @Override
    public String toString() {
        return "CapdetaiCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (macapdetai != null ? "macapdetai=" + macapdetai + ", " : "") +
                (tencapdetai != null ? "tencapdetai=" + tencapdetai + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
            "}";
    }

}
