package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.NhansuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nhansu} and its DTO {@link NhansuDTO}.
 */
@Mapper(componentModel = "spring", uses = {DonviMapper.class, ChucdanhMapper.class, HochamMapper.class})
public interface NhansuMapper extends EntityMapper<NhansuDTO, Nhansu> {

    @Mapping(source = "donvi.id", target = "donviId")
    @Mapping(source = "chucdanh.id", target = "chucdanhId")
    @Mapping(source = "hocham.id", target = "hochamId")
    NhansuDTO toDto(Nhansu nhansu);

    @Mapping(target = "chunhiems", ignore = true)
    @Mapping(target = "removeChunhiem", ignore = true)
    @Mapping(target = "nhansuthamgias", ignore = true)
    @Mapping(target = "removeNhansuthamgia", ignore = true)
    @Mapping(source = "donviId", target = "donvi")
    @Mapping(source = "chucdanhId", target = "chucdanh")
    @Mapping(source = "hochamId", target = "hocham")
    Nhansu toEntity(NhansuDTO nhansuDTO);

    default Nhansu fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nhansu nhansu = new Nhansu();
        nhansu.setId(id);
        return nhansu;
    }
}
