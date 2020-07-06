package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.CapdetaiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Capdetai} and its DTO {@link CapdetaiDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CapdetaiMapper extends EntityMapper<CapdetaiDTO, Capdetai> {


    @Mapping(target = "detais", ignore = true)
    @Mapping(target = "removeDetai", ignore = true)
    Capdetai toEntity(CapdetaiDTO capdetaiDTO);

    default Capdetai fromId(Long id) {
        if (id == null) {
            return null;
        }
        Capdetai capdetai = new Capdetai();
        capdetai.setId(id);
        return capdetai;
    }
}
