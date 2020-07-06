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
 * Criteria class for the {@link com.api.backend.domain.Nguonkinhphi} entity. This class is used
 * in {@link com.api.backend.web.rest.NguonkinhphiResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nguonkinhphis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NguonkinhphiCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter manguonkinhphi;

    private StringFilter tennguonkinhphi;

    private StringFilter noidung;

    private IntegerFilter sotiencap;

    private IntegerFilter sudung;

    private LongFilter detaiId;

    public NguonkinhphiCriteria(){
    }

    public NguonkinhphiCriteria(NguonkinhphiCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.manguonkinhphi = other.manguonkinhphi == null ? null : other.manguonkinhphi.copy();
        this.tennguonkinhphi = other.tennguonkinhphi == null ? null : other.tennguonkinhphi.copy();
        this.noidung = other.noidung == null ? null : other.noidung.copy();
        this.sotiencap = other.sotiencap == null ? null : other.sotiencap.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
    }

    @Override
    public NguonkinhphiCriteria copy() {
        return new NguonkinhphiCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getManguonkinhphi() {
        return manguonkinhphi;
    }

    public void setManguonkinhphi(StringFilter manguonkinhphi) {
        this.manguonkinhphi = manguonkinhphi;
    }

    public StringFilter getTennguonkinhphi() {
        return tennguonkinhphi;
    }

    public void setTennguonkinhphi(StringFilter tennguonkinhphi) {
        this.tennguonkinhphi = tennguonkinhphi;
    }

    public StringFilter getNoidung() {
        return noidung;
    }

    public void setNoidung(StringFilter noidung) {
        this.noidung = noidung;
    }

    public IntegerFilter getSotiencap() {
        return sotiencap;
    }

    public void setSotiencap(IntegerFilter sotiencap) {
        this.sotiencap = sotiencap;
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
        final NguonkinhphiCriteria that = (NguonkinhphiCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(manguonkinhphi, that.manguonkinhphi) &&
            Objects.equals(tennguonkinhphi, that.tennguonkinhphi) &&
            Objects.equals(noidung, that.noidung) &&
            Objects.equals(sotiencap, that.sotiencap) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(detaiId, that.detaiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        manguonkinhphi,
        tennguonkinhphi,
        noidung,
        sotiencap,
        sudung,
        detaiId
        );
    }

    @Override
    public String toString() {
        return "NguonkinhphiCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (manguonkinhphi != null ? "manguonkinhphi=" + manguonkinhphi + ", " : "") +
                (tennguonkinhphi != null ? "tennguonkinhphi=" + tennguonkinhphi + ", " : "") +
                (noidung != null ? "noidung=" + noidung + ", " : "") +
                (sotiencap != null ? "sotiencap=" + sotiencap + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
            "}";
    }

}
