package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.CoquanphoihopDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Coquanphoihop} and its DTO {@link CoquanphoihopDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoquanphoihopMapper extends EntityMapper<CoquanphoihopDTO, Coquanphoihop> {


    @Mapping(target = "coquanphoihopthamgias", ignore = true)
    @Mapping(target = "removeCoquanphoihopthamgia", ignore = true)
    Coquanphoihop toEntity(CoquanphoihopDTO coquanphoihopDTO);

    default Coquanphoihop fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coquanphoihop coquanphoihop = new Coquanphoihop();
        coquanphoihop.setId(id);
        return coquanphoihop;
    }
}
