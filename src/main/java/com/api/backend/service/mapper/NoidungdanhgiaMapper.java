package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.NoidungdanhgiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Noidungdanhgia} and its DTO {@link NoidungdanhgiaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NoidungdanhgiaMapper extends EntityMapper<NoidungdanhgiaDTO, Noidungdanhgia> {


    @Mapping(target = "danhgiaCTS", ignore = true)
    @Mapping(target = "removeDanhgiaCT", ignore = true)
    Noidungdanhgia toEntity(NoidungdanhgiaDTO noidungdanhgiaDTO);

    default Noidungdanhgia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Noidungdanhgia noidungdanhgia = new Noidungdanhgia();
        noidungdanhgia.setId(id);
        return noidungdanhgia;
    }
}
