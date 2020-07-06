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
 * Criteria class for the {@link com.api.backend.domain.Coquanphoihopthamgia} entity. This class is used
 * in {@link com.api.backend.web.rest.CoquanphoihopthamgiaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /coquanphoihopthamgias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CoquanphoihopthamgiaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sudung;

    private LongFilter detaiId;

    private LongFilter coquanphoihopId;

    public CoquanphoihopthamgiaCriteria(){
    }

    public CoquanphoihopthamgiaCriteria(CoquanphoihopthamgiaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
        this.coquanphoihopId = other.coquanphoihopId == null ? null : other.coquanphoihopId.copy();
    }

    @Override
    public CoquanphoihopthamgiaCriteria copy() {
        return new CoquanphoihopthamgiaCriteria(this);
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

    public LongFilter getDetaiId() {
        return detaiId;
    }

    public void setDetaiId(LongFilter detaiId) {
        this.detaiId = detaiId;
    }

    public LongFilter getCoquanphoihopId() {
        return coquanphoihopId;
    }

    public void setCoquanphoihopId(LongFilter coquanphoihopId) {
        this.coquanphoihopId = coquanphoihopId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CoquanphoihopthamgiaCriteria that = (CoquanphoihopthamgiaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(detaiId, that.detaiId) &&
            Objects.equals(coquanphoihopId, that.coquanphoihopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sudung,
        detaiId,
        coquanphoihopId
        );
    }

    @Override
    public String toString() {
        return "CoquanphoihopthamgiaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
                (coquanphoihopId != null ? "coquanphoihopId=" + coquanphoihopId + ", " : "") +
            "}";
    }

}
