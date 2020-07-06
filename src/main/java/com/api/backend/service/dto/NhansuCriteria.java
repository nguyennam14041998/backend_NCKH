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
 * Criteria class for the {@link com.api.backend.domain.Nhansu} entity. This class is used
 * in {@link com.api.backend.web.rest.NhansuResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nhansus?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NhansuCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter manhansu;

    private StringFilter tennhansu;

    private IntegerFilter sdt;

    private StringFilter email;

    private StringFilter diachi;

    private StringFilter namsinh;

    private LocalDateFilter ngaysinh;

    private IntegerFilter sudung;

    private LongFilter chunhiemId;

    private LongFilter nhansuthamgiaId;

    private LongFilter donviId;

    private LongFilter chucdanhId;

    private LongFilter hochamId;

    public NhansuCriteria(){
    }

    public NhansuCriteria(NhansuCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.manhansu = other.manhansu == null ? null : other.manhansu.copy();
        this.tennhansu = other.tennhansu == null ? null : other.tennhansu.copy();
        this.sdt = other.sdt == null ? null : other.sdt.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.diachi = other.diachi == null ? null : other.diachi.copy();
        this.namsinh = other.namsinh == null ? null : other.namsinh.copy();
        this.ngaysinh = other.ngaysinh == null ? null : other.ngaysinh.copy();
        this.sudung = other.sudung == null ? null : other.sudung.copy();
        this.chunhiemId = other.chunhiemId == null ? null : other.chunhiemId.copy();
        this.nhansuthamgiaId = other.nhansuthamgiaId == null ? null : other.nhansuthamgiaId.copy();
        this.donviId = other.donviId == null ? null : other.donviId.copy();
        this.chucdanhId = other.chucdanhId == null ? null : other.chucdanhId.copy();
        this.hochamId = other.hochamId == null ? null : other.hochamId.copy();
    }

    @Override
    public NhansuCriteria copy() {
        return new NhansuCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getManhansu() {
        return manhansu;
    }

    public void setManhansu(StringFilter manhansu) {
        this.manhansu = manhansu;
    }

    public StringFilter getTennhansu() {
        return tennhansu;
    }

    public void setTennhansu(StringFilter tennhansu) {
        this.tennhansu = tennhansu;
    }

    public IntegerFilter getSdt() {
        return sdt;
    }

    public void setSdt(IntegerFilter sdt) {
        this.sdt = sdt;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getDiachi() {
        return diachi;
    }

    public void setDiachi(StringFilter diachi) {
        this.diachi = diachi;
    }

    public StringFilter getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(StringFilter namsinh) {
        this.namsinh = namsinh;
    }

    public LocalDateFilter getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(LocalDateFilter ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public IntegerFilter getSudung() {
        return sudung;
    }

    public void setSudung(IntegerFilter sudung) {
        this.sudung = sudung;
    }

    public LongFilter getChunhiemId() {
        return chunhiemId;
    }

    public void setChunhiemId(LongFilter chunhiemId) {
        this.chunhiemId = chunhiemId;
    }

    public LongFilter getNhansuthamgiaId() {
        return nhansuthamgiaId;
    }

    public void setNhansuthamgiaId(LongFilter nhansuthamgiaId) {
        this.nhansuthamgiaId = nhansuthamgiaId;
    }

    public LongFilter getDonviId() {
        return donviId;
    }

    public void setDonviId(LongFilter donviId) {
        this.donviId = donviId;
    }

    public LongFilter getChucdanhId() {
        return chucdanhId;
    }

    public void setChucdanhId(LongFilter chucdanhId) {
        this.chucdanhId = chucdanhId;
    }

    public LongFilter getHochamId() {
        return hochamId;
    }

    public void setHochamId(LongFilter hochamId) {
        this.hochamId = hochamId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NhansuCriteria that = (NhansuCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(manhansu, that.manhansu) &&
            Objects.equals(tennhansu, that.tennhansu) &&
            Objects.equals(sdt, that.sdt) &&
            Objects.equals(email, that.email) &&
            Objects.equals(diachi, that.diachi) &&
            Objects.equals(namsinh, that.namsinh) &&
            Objects.equals(ngaysinh, that.ngaysinh) &&
            Objects.equals(sudung, that.sudung) &&
            Objects.equals(chunhiemId, that.chunhiemId) &&
            Objects.equals(nhansuthamgiaId, that.nhansuthamgiaId) &&
            Objects.equals(donviId, that.donviId) &&
            Objects.equals(chucdanhId, that.chucdanhId) &&
            Objects.equals(hochamId, that.hochamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        manhansu,
        tennhansu,
        sdt,
        email,
        diachi,
        namsinh,
        ngaysinh,
        sudung,
        chunhiemId,
        nhansuthamgiaId,
        donviId,
        chucdanhId,
        hochamId
        );
    }

    @Override
    public String toString() {
        return "NhansuCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (manhansu != null ? "manhansu=" + manhansu + ", " : "") +
                (tennhansu != null ? "tennhansu=" + tennhansu + ", " : "") +
                (sdt != null ? "sdt=" + sdt + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (diachi != null ? "diachi=" + diachi + ", " : "") +
                (namsinh != null ? "namsinh=" + namsinh + ", " : "") +
                (ngaysinh != null ? "ngaysinh=" + ngaysinh + ", " : "") +
                (sudung != null ? "sudung=" + sudung + ", " : "") +
                (chunhiemId != null ? "chunhiemId=" + chunhiemId + ", " : "") +
                (nhansuthamgiaId != null ? "nhansuthamgiaId=" + nhansuthamgiaId + ", " : "") +
                (donviId != null ? "donviId=" + donviId + ", " : "") +
                (chucdanhId != null ? "chucdanhId=" + chucdanhId + ", " : "") +
                (hochamId != null ? "hochamId=" + hochamId + ", " : "") +
            "}";
    }

}
