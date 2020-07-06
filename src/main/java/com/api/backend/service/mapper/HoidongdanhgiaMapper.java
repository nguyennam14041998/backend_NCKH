package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.HoidongdanhgiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hoidongdanhgia} and its DTO {@link HoidongdanhgiaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HoidongdanhgiaMapper extends EntityMapper<HoidongdanhgiaDTO, Hoidongdanhgia> {


    @Mapping(target = "detais", ignore = true)
    @Mapping(target = "removeDetai", ignore = true)
    @Mapping(target = "thanhvienHDS", ignore = true)
    @Mapping(target = "removeThanhvienHD", ignore = true)
    Hoidongdanhgia toEntity(HoidongdanhgiaDTO hoidongdanhgiaDTO);

    default Hoidongdanhgia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hoidongdanhgia hoidongdanhgia = new Hoidongdanhgia();
        hoidongdanhgia.setId(id);
        return hoidongdanhgia;
    }
}
