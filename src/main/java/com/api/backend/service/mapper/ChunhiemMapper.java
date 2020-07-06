package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.ChunhiemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chunhiem} and its DTO {@link ChunhiemDTO}.
 */
@Mapper(componentModel = "spring", uses = {NhansuMapper.class})
public interface ChunhiemMapper extends EntityMapper<ChunhiemDTO, Chunhiem> {

    @Mapping(source = "nhansu.id", target = "nhansuId")
    ChunhiemDTO toDto(Chunhiem chunhiem);

    @Mapping(target = "detais", ignore = true)
    @Mapping(target = "removeDetai", ignore = true)
    @Mapping(source = "nhansuId", target = "nhansu")
    Chunhiem toEntity(ChunhiemDTO chunhiemDTO);

    default Chunhiem fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chunhiem chunhiem = new Chunhiem();
        chunhiem.setId(id);
        return chunhiem;
    }
}
