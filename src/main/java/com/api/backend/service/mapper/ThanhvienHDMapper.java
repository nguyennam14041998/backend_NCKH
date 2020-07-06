package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.ThanhvienHDDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThanhvienHD} and its DTO {@link ThanhvienHDDTO}.
 */
@Mapper(componentModel = "spring", uses = {HoidongdanhgiaMapper.class})
public interface ThanhvienHDMapper extends EntityMapper<ThanhvienHDDTO, ThanhvienHD> {

    @Mapping(source = "hoidongdanhgia.id", target = "hoidongdanhgiaId")
    ThanhvienHDDTO toDto(ThanhvienHD thanhvienHD);

    @Mapping(source = "hoidongdanhgiaId", target = "hoidongdanhgia")
    ThanhvienHD toEntity(ThanhvienHDDTO thanhvienHDDTO);

    default ThanhvienHD fromId(Long id) {
        if (id == null) {
            return null;
        }
        ThanhvienHD thanhvienHD = new ThanhvienHD();
        thanhvienHD.setId(id);
        return thanhvienHD;
    }
}
