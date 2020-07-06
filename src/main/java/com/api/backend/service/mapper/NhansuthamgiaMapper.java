package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.NhansuthamgiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nhansuthamgia} and its DTO {@link NhansuthamgiaDTO}.
 */
@Mapper(componentModel = "spring", uses = {NhansuMapper.class, DetaiMapper.class})
public interface NhansuthamgiaMapper extends EntityMapper<NhansuthamgiaDTO, Nhansuthamgia> {

    @Mapping(source = "nhansu.id", target = "nhansuId")
    @Mapping(source = "detai.id", target = "detaiId")
    NhansuthamgiaDTO toDto(Nhansuthamgia nhansuthamgia);

    @Mapping(source = "nhansuId", target = "nhansu")
    @Mapping(source = "detaiId", target = "detai")
    Nhansuthamgia toEntity(NhansuthamgiaDTO nhansuthamgiaDTO);

    default Nhansuthamgia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nhansuthamgia nhansuthamgia = new Nhansuthamgia();
        nhansuthamgia.setId(id);
        return nhansuthamgia;
    }
}
