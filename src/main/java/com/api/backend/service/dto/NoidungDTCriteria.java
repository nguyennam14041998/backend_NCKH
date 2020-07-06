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
 * Criteria class for the {@link com.api.backend.domain.NoidungDT} entity. This class is used
 * in {@link com.api.backend.web.rest.NoidungDTResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /noidung-dts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NoidungDTCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tennoidung;

    private IntegerFilter sudung;

    private LongFilter dutoanKPCTId;

    public NoidungDTCriteria(){
    }

    public NoidungDTCriteria(NoidungDTCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tennoidung = other.tennoidung == null ? null : other.tennoidung.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.dutoanKPCTId = other.dutoanKPCTId == null ? null : other.dutoanKPCTId.copy();
    }

    @Override
    public NoidungDTCriteria copy() {
        return new NoidungDTCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTennoidung() {
        return tennoidung;
    }

    public void setTennoidung(StringFilter tennoidung) {
        this.tennoidung = tennoidung;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NoidungDTCriteria that = (NoidungDTCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tennoidung, that.tennoidung) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(dutoanKPCTId, that.dutoanKPCTId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tennoidung,
        sudung,
        dutoanKPCTId
        );
    }

    @Override
    public String toString() {
        return "NoidungDTCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tennoidung != null ? "tennoidung=" + tennoidung + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (dutoanKPCTId != null ? "dutoanKPCTId=" + dutoanKPCTId + ", " : "") +
            "}";
    }

}
