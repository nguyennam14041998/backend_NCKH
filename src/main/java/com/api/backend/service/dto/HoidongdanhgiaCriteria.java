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
 * Criteria class for the {@link com.api.backend.domain.Hoidongdanhgia} entity. This class is used
 * in {@link com.api.backend.web.rest.HoidongdanhgiaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hoidongdanhgias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HoidongdanhgiaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter mahoidong;

    private StringFilter tenhoidong;

    private IntegerFilter sudung;

    private LongFilter detaiId;

    private LongFilter thanhvienHDId;

    public HoidongdanhgiaCriteria(){
    }

    public HoidongdanhgiaCriteria(HoidongdanhgiaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.mahoidong = other.mahoidong == null ? null : other.mahoidong.copy();
        this.tenhoidong = other.tenhoidong == null ? null : other.tenhoidong.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.detaiId = other.detaiId == null ? null : other.detaiId.copy();
        this.thanhvienHDId = other.thanhvienHDId == null ? null : other.thanhvienHDId.copy();
    }

    @Override
    public HoidongdanhgiaCriteria copy() {
        return new HoidongdanhgiaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMahoidong() {
        return mahoidong;
    }

    public void setMahoidong(StringFilter mahoidong) {
        this.mahoidong = mahoidong;
    }

    public StringFilter getTenhoidong() {
        return tenhoidong;
    }

    public void setTenhoidong(StringFilter tenhoidong) {
        this.tenhoidong = tenhoidong;
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

    public LongFilter getThanhvienHDId() {
        return thanhvienHDId;
    }

    public void setThanhvienHDId(LongFilter thanhvienHDId) {
        this.thanhvienHDId = thanhvienHDId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HoidongdanhgiaCriteria that = (HoidongdanhgiaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(mahoidong, that.mahoidong) &&
            Objects.equals(tenhoidong, that.tenhoidong) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(detaiId, that.detaiId) &&
            Objects.equals(thanhvienHDId, that.thanhvienHDId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        mahoidong,
        tenhoidong,
        sudung,
        detaiId,
        thanhvienHDId
        );
    }

    @Override
    public String toString() {
        return "HoidongdanhgiaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mahoidong != null ? "mahoidong=" + mahoidong + ", " : "") +
                (tenhoidong != null ? "tenhoidong=" + tenhoidong + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (detaiId != null ? "detaiId=" + detaiId + ", " : "") +
                (thanhvienHDId != null ? "thanhvienHDId=" + thanhvienHDId + ", " : "") +
            "}";
    }

}
