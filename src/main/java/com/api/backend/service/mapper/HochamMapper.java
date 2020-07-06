package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.HochamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hocham} and its DTO {@link HochamDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HochamMapper extends EntityMapper<HochamDTO, Hocham> {


    @Mapping(target = "nhansus", ignore = true)
    @Mapping(target = "removeNhansu", ignore = true)
    Hocham toEntity(HochamDTO hochamDTO);

    default Hocham fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hocham hocham = new Hocham();
        hocham.setId(id);
        return hocham;
    }
}
