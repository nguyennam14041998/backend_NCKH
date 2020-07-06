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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.api.backend.domain.Upfile} entity. This class is used
 * in {@link com.api.backend.web.rest.UpfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /upfiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UpfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter mota;

    private LocalDateFilter thoigian;

    private LongFilter detaiId;

    private LongFilter tiendoId;

    public UpfileCriteria(){
    }

    public UpfileCriteria(UpfileCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.mota = other.mota == null ? null : other.mota.copy();
        this.thoigian = other.thoigian == null ? null : other.thoigian.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
        this.tiendoId = other.tiendoId == null ? null : other.tiendoId.copy();
    }

    @Override
    public UpfileCriteria copy() {
        return new UpfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMota() {
        return mota;
    }

    public void setMota(StringFilter mota) {
        this.mota = mota;
    }

    public LocalDateFilter getThoigian() {
        return thoigian;
    }

    public void setThoigian(LocalDateFilter thoigian) {
        this.thoigian = thoigian;
    }

    public LongFilter getDetaiId() {
        return detaiId;
    }

    public void setDetaiId(LongFilter detaiId) {
        this.detaiId = detaiId;
    }

    public LongFilter getTiendoId() {
        return tiendoId;
    }

    public void setTiendoId(LongFilter tiendoId) {
        this.tiendoId = tiendoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UpfileCriteria that = (UpfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(mota, that.mota) &&
            Objects.equals(thoigian, that.thoigian) &&
            Objects.equals(detaiId, that.detaiId) &&
            Objects.equals(tiendoId, that.tiendoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        mota,
        thoigian,
        detaiId,
        tiendoId
        );
    }

    @Override
    public String toString() {
        return "UpfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mota != null ? "mota=" + mota + ", " : "") +
                (thoigian != null ? "thoigian=" + thoigian + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
                (tiendoId != null ? "tiendoId=" + tiendoId + ", " : "") +
            "}";
    }

}
