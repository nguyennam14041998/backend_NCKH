package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.CoquanphoihopthamgiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Coquanphoihopthamgia} and its DTO {@link CoquanphoihopthamgiaDTO}.
 */
@Mapper(componentModel = "spring", uses = {DetaiMapper.class, CoquanphoihopMapper.class})
public interface CoquanphoihopthamgiaMapper extends EntityMapper<CoquanphoihopthamgiaDTO, Coquanphoihopthamgia> {

    @Mapping(source = "detai.id", target = "detaiId")
    @Mapping(source = "coquanphoihop.id", target = "coquanphoihopId")
    CoquanphoihopthamgiaDTO toDto(Coquanphoihopthamgia coquanphoihopthamgia);

    @Mapping(source = "detaiId", target = "detai")
    @Mapping(source = "coquanphoihopId", target = "coquanphoihop")
    Coquanphoihopthamgia toEntity(CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO);

    default Coquanphoihopthamgia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coquanphoihopthamgia coquanphoihopthamgia = new Coquanphoihopthamgia();
        coquanphoihopthamgia.setId(id);
        return coquanphoihopthamgia;
    }
}
