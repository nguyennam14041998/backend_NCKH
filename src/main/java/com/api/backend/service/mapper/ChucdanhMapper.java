package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.ChucdanhDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chucdanh} and its DTO {@link ChucdanhDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChucdanhMapper extends EntityMapper<ChucdanhDTO, Chucdanh> {


    @Mapping(target = "nhansus", ignore = true)
    @Mapping(target = "removeNhansu", ignore = true)
    Chucdanh toEntity(ChucdanhDTO chucdanhDTO);

    default Chucdanh fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chucdanh chucdanh = new Chucdanh();
        chucdanh.setId(id);
        return chucdanh;
    }
}
