package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.LinhvucDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Linhvuc} and its DTO {@link LinhvucDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LinhvucMapper extends EntityMapper<LinhvucDTO, Linhvuc> {


    @Mapping(target = "detais", ignore = true)
    @Mapping(target = "removeDetai", ignore = true)
    Linhvuc toEntity(LinhvucDTO linhvucDTO);

    default Linhvuc fromId(Long id) {
        if (id == null) {
            return null;
        }
        Linhvuc linhvuc = new Linhvuc();
        linhvuc.setId(id);
        return linhvuc;
    }
}
