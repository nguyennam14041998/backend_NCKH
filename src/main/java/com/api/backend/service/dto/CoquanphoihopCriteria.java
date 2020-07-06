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
 * Criteria class for the {@link com.api.backend.domain.Coquanphoihop} entity. This class is used
 * in {@link com.api.backend.web.rest.CoquanphoihopResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /coquanphoihops?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CoquanphoihopCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter macoquan;

    private StringFilter tencoquan;

    private StringFilter noidung;

    private StringFilter tendaidien;

    private IntegerFilter sudung;

    private LongFilter coquanphoihopthamgiaId;

    public CoquanphoihopCriteria(){
    }

    public CoquanphoihopCriteria(CoquanphoihopCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.macoquan = other.macoquan == null ? null : other.macoquan.copy();
        this.tencoquan = other.tencoquan == null ? null : other.tencoquan.copy();
        this.noidung = other.noidung == null ? null : other.noidung.copy();
        this.tendaidien = other.tendaidien == null ? null : other.tendaidien.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.coquanphoihopthamgiaId = other.coquanphoihopthamgiaId == null ? null : other.coquanphoihopthamgiaId.copy();
    }

    @Override
    public CoquanphoihopCriteria copy() {
        return new CoquanphoihopCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMacoquan() {
        return macoquan;
    }

    public void setMacoquan(StringFilter macoquan) {
        this.macoquan = macoquan;
    }

    public StringFilter getTencoquan() {
        return tencoquan;
    }

    public void setTencoquan(StringFilter tencoquan) {
        this.tencoquan = tencoquan;
    }

    public StringFilter getNoidung() {
        return noidung;
    }

    public void setNoidung(StringFilter noidung) {
        this.noidung = noidung;
    }

    public StringFilter getTendaidien() {
        return tendaidien;
    }

    public void setTendaidien(StringFilter tendaidien) {
        this.tendaidien = tendaidien;
    }

    public IntegerFilter getSudung() {
        return sudung;
    }

    public void setSudung(IntegerFilter sudung) {
        this.sudung = sudung;
    }

    public LongFilter getCoquanphoihopthamgiaId() {
        return coquanphoihopthamgiaId;
    }

    public void setCoquanphoihopthamgiaId(LongFilter coquanphoihopthamgiaId) {
        this.coquanphoihopthamgiaId = coquanphoihopthamgiaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CoquanphoihopCriteria that = (CoquanphoihopCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(macoquan, that.macoquan) &&
            Objects.equals(tencoquan, that.tencoquan) &&
            Objects.equals(noidung, that.noidung) &&
            Objects.equals(tendaidien, that.tendaidien) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(coquanphoihopthamgiaId, that.coquanphoihopthamgiaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        macoquan,
        tencoquan,
        noidung,
        tendaidien,
        sudung,
        coquanphoihopthamgiaId
        );
    }

    @Override
    public String toString() {
        return "CoquanphoihopCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (macoquan != null ? "macoquan=" + macoquan + ", " : "") +
                (tencoquan != null ? "tencoquan=" + tencoquan + ", " : "") +
                (noidung != null ? "noidung=" + noidung + ", " : "") +
                (tendaidien != null ? "tendaidien=" + tendaidien + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (coquanphoihopthamgiaId != null ? "coquanphoihopthamgiaId=" + coquanphoihopthamgiaId + ", " : "") +
            "}";
    }

}
