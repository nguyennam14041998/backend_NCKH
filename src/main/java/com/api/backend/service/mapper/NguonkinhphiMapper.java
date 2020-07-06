package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.NguonkinhphiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nguonkinhphi} and its DTO {@link NguonkinhphiDTO}.
 */
@Mapper(componentModel = "spring", uses = {DetaiMapper.class})
public interface NguonkinhphiMapper extends EntityMapper<NguonkinhphiDTO, Nguonkinhphi> {

    @Mapping(source = "detai.id", target = "detaiId")
    NguonkinhphiDTO toDto(Nguonkinhphi nguonkinhphi);

    @Mapping(source = "detaiId", target = "detai")
    Nguonkinhphi toEntity(NguonkinhphiDTO nguonkinhphiDTO);

    default Nguonkinhphi fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nguonkinhphi nguonkinhphi = new Nguonkinhphi();
        nguonkinhphi.setId(id);
        return nguonkinhphi;
    }
}
