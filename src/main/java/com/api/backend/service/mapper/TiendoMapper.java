package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.TiendoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tiendo} and its DTO {@link TiendoDTO}.
 */
@Mapper(componentModel = "spring", uses = {DetaiMapper.class})
public interface TiendoMapper extends EntityMapper<TiendoDTO, Tiendo> {

    @Mapping(source = "detai.id", target = "detaiId")
    TiendoDTO toDto(Tiendo tiendo);

    @Mapping(target = "upfiles", ignore = true)
    @Mapping(target = "removeUpfile", ignore = true)
    @Mapping(source = "detaiId", target = "detai")
    Tiendo toEntity(TiendoDTO tiendoDTO);

    default Tiendo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tiendo tiendo = new Tiendo();
        tiendo.setId(id);
        return tiendo;
    }
}
