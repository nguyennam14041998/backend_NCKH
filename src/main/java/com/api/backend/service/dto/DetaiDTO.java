package com.api.backend.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.api.backend.domain.Detai} entity.
 */
public class DetaiDTO implements Serializable {

    private Long id;

    private String ma;

    private String ten;

    private LocalDate thoigiantao;

    private LocalDate thoigianbatdau;

    private LocalDate thoigianketthuc;

    private String muctieu;

    private String noidung;

    private String tinhcapthiet;

    private String nguoihuongdan;

    private String ketqua;

    private Integer xeploai;

    private Integer phanloai;

    private Integer trangthai;

    private String tenchunhiem;

    private Integer sudung;


    private Long linhvucId;

    private Long capdetaiId;

    private Long hoidongdanhgiaId;

    private Long chunhiemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public LocalDate getThoigiantao() {
        return thoigiantao;
    }

    public void setThoigiantao(LocalDate thoigiantao) {
        this.thoigiantao = thoigiantao;
    }

    public LocalDate getThoigianbatdau() {
        return thoigianbatdau;
    }

    public void setThoigianbatdau(LocalDate thoigianbatdau) {
        this.thoigianbatdau = thoigianbatdau;
    }

    public LocalDate getThoigianketthuc() {
        return thoigianketthuc;
    }

    public void setThoigianketthuc(LocalDate thoigianketthuc) {
        this.thoigianketthuc = thoigianketthuc;
    }

    public String getMuctieu() {
        return muctieu;
    }

    public void setMuctieu(String muctieu) {
        this.muctieu = muctieu;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTinhcapthiet() {
        return tinhcapthiet;
    }

    public void setTinhcapthiet(String tinhcapthiet) {
        this.tinhcapthiet = tinhcapthiet;
    }

    public String getNguoihuongdan() {
        return nguoihuongdan;
    }

    public void setNguoihuongdan(String nguoihuongdan) {
        this.nguoihuongdan = nguoihuongdan;
    }

    public String getKetqua() {
        return ketqua;
    }

    public void setKetqua(String ketqua) {
        this.ketqua = ketqua;
    }

    public Integer getXeploai() {
        return xeploai;
    }

    public void setXeploai(Integer xeploai) {
        this.xeploai = xeploai;
    }

    public Integer getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(Integer phanloai) {
        this.phanloai = phanloai;
    }

    public Integer getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(Integer trangthai) {
        this.trangthai = trangthai;
    }

    public String getTenchunhiem() {
        return tenchunhiem;
    }

    public void setTenchunhiem(String tenchunhiem) {
        this.tenchunhiem = tenchunhiem;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Long getLinhvucId() {
        return linhvucId;
    }

    public void setLinhvucId(Long linhvucId) {
        this.linhvucId = linhvucId;
    }

    public Long getCapdetaiId() {
        return capdetaiId;
    }

    public void setCapdetaiId(Long capdetaiId) {
        this.capdetaiId = capdetaiId;
    }

    public Long getHoidongdanhgiaId() {
        return hoidongdanhgiaId;
    }

    public void setHoidongdanhgiaId(Long hoidongdanhgiaId) {
        this.hoidongdanhgiaId = hoidongdanhgiaId;
    }

    public Long getChunhiemId() {
        return chunhiemId;
    }

    public void setChunhiemId(Long chunhiemId) {
        this.chunhiemId = chunhiemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetaiDTO detaiDTO = (DetaiDTO) o;
        if (detaiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detaiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetaiDTO{" +
            "id=" + getId() +
            ", ma='" + getMa() + "'" +
            ", ten='" + getTen() + "'" +
            ", thoigiantao='" + getThoigiantao() + "'" +
            ", thoigianbatdau='" + getThoigianbatdau() + "'" +
            ", thoigianketthuc='" + getThoigianketthuc() + "'" +
            ", muctieu='" + getMuctieu() + "'" +
            ", noidung='" + getNoidung() + "'" +
            ", tinhcapthiet='" + getTinhcapthiet() + "'" +
            ", nguoihuongdan='" + getNguoihuongdan() + "'" +
            ", ketqua='" + getKetqua() + "'" +
            ", xeploai=" + getXeploai() +
            ", phanloai=" + getPhanloai() +
            ", trangthai=" + getTrangthai() +
            ", tenchunhiem='" + getTenchunhiem() + "'" +
            ", sudung=" + getSudung() +
            ", linhvuc=" + getLinhvucId() +
            ", capdetai=" + getCapdetaiId() +
            ", hoidongdanhgia=" + getHoidongdanhgiaId() +
            ", chunhiem=" + getChunhiemId() +
            "}";
    }
}
