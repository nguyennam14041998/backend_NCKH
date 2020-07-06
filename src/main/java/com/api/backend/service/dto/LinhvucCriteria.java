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
 * Criteria class for the {@link com.api.backend.domain.Linhvuc} entity. This class is used
 * in {@link com.api.backend.web.rest.LinhvucResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /linhvucs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LinhvucCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter malv;

    private StringFilter tenlv;

    private IntegerFilter sudung;

    private LongFilter detaiId;

    public LinhvucCriteria(){
    }

    public LinhvucCriteria(LinhvucCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.malv = other.malv == null ? null : other.malv.copy();
        this.tenlv = other.tenlv == null ? null : other.tenlv.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
    }

    @Override
    public LinhvucCriteria copy() {
        return new LinhvucCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMalv() {
        return malv;
    }

    public void setMalv(StringFilter malv) {
        this.malv = malv;
    }

    public StringFilter getTenlv() {
        return tenlv;
    }

    public void setTenlv(StringFilter tenlv) {
        this.tenlv = tenlv;
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
        final LinhvucCriteria that = (LinhvucCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(malv, that.malv) &&
            Objects.equals(tenlv, that.tenlv) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(detaiId, that.detaiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        malv,
        tenlv,
        sudung,
        detaiId
        );
    }

    @Override
    public String toString() {
        return "LinhvucCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (malv != null ? "malv=" + malv + ", " : "") +
                (tenlv != null ? "tenlv=" + tenlv + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
            "}";
    }

}
