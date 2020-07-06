package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.UpfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Upfile} and its DTO {@link UpfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {DetaiMapper.class, TiendoMapper.class})
public interface UpfileMapper extends EntityMapper<UpfileDTO, Upfile> {

    @Mapping(source = "detai.id", target = "detaiId")
    @Mapping(source = "tiendo.id", target = "tiendoId")
    UpfileDTO toDto(Upfile upfile);

    @Mapping(source = "detaiId", target = "detai")
    @Mapping(source = "tiendoId", target = "tiendo")
    Upfile toEntity(UpfileDTO upfileDTO);

    default Upfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Upfile upfile = new Upfile();
        upfile.setId(id);
        return upfile;
    }
}
