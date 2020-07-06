package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.DanhsachbaibaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Danhsachbaibao} and its DTO {@link DanhsachbaibaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {DetaiMapper.class})
public interface DanhsachbaibaoMapper extends EntityMapper<DanhsachbaibaoDTO, Danhsachbaibao> {

    @Mapping(source = "detai.id", target = "detaiId")
    DanhsachbaibaoDTO toDto(Danhsachbaibao danhsachbaibao);

    @Mapping(source = "detaiId", target = "detai")
    Danhsachbaibao toEntity(DanhsachbaibaoDTO danhsachbaibaoDTO);

    default Danhsachbaibao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Danhsachbaibao danhsachbaibao = new Danhsachbaibao();
        danhsachbaibao.setId(id);
        return danhsachbaibao;
    }
}
