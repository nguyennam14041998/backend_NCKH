package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.DanhgiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Danhgia} and its DTO {@link DanhgiaDTO}.
 */
@Mapper(componentModel = "spring", uses = {DetaiMapper.class})
public interface DanhgiaMapper extends EntityMapper<DanhgiaDTO, Danhgia> {

    @Mapping(source = "detai.id", target = "detaiId")
    DanhgiaDTO toDto(Danhgia danhgia);

    @Mapping(target = "danhgiaCTS", ignore = true)
    @Mapping(target = "removeDanhgiaCT", ignore = true)
    @Mapping(source = "detaiId", target = "detai")
    Danhgia toEntity(DanhgiaDTO danhgiaDTO);

    default Danhgia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Danhgia danhgia = new Danhgia();
        danhgia.setId(id);
        return danhgia;
    }
}
