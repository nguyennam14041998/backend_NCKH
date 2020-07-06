package com.api.backend.service.mapper;

import com.api.backend.domain.*;
import com.api.backend.service.dto.DutoanKPDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DutoanKP} and its DTO {@link DutoanKPDTO}.
 */
@Mapper(componentModel = "spring", uses = {DetaiMapper.class})
public interface DutoanKPMapper extends EntityMapper<DutoanKPDTO, DutoanKP> {

    @Mapping(source = "detai.id", target = "detaiId")
    DutoanKPDTO toDto(DutoanKP dutoanKP);

    @Mapping(target = "dutoanKPCTS", ignore = true)
    @Mapping(target = "removeDutoanKPCT", ignore = true)
    @Mapping(source = "detaiId", target = "detai")
    DutoanKP toEntity(DutoanKPDTO dutoanKPDTO);

    default DutoanKP fromId(Long id) {
        if (id == null) {
            return null;
        }
        DutoanKP dutoanKP = new DutoanKP();
        dutoanKP.setId(id);
        return dutoanKP;
    }
}
