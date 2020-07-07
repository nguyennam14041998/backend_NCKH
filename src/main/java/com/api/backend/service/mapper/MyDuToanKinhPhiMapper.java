package com.api.backend.service.mapper;

import com.api.backend.domain.DutoanKP;
import com.api.backend.repository.DetaiRepository;
import com.api.backend.service.dto.DutoanKPDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyDuToanKinhPhiMapper {

    @Autowired
    private DetaiRepository detaiRepository;


    public List<DutoanKP> toEntity(List<DutoanKPDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DutoanKP> list = new ArrayList<DutoanKP>( dtoList.size() );
        for ( DutoanKPDTO dutoanKPDTO : dtoList ) {
            list.add( toEntity( dutoanKPDTO ) );
        }

        return list;
    }


    public List<DutoanKPDTO> toDto(List<DutoanKP> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DutoanKPDTO> list = new ArrayList<DutoanKPDTO>( entityList.size() );
        for ( DutoanKP dutoanKP : entityList ) {
            list.add( toDto( dutoanKP ) );
        }

        return list;
    }


    public DutoanKPDTO toDto(DutoanKP dutoanKP) {
        if ( dutoanKP == null ) {
            return null;
        }

        DutoanKPDTO dutoanKPDTO = new DutoanKPDTO();

        dutoanKPDTO.setDetaiId( (dutoanKP.getDetai() != null) ? dutoanKP.getDetai().getId() : null );
        dutoanKPDTO.setId( dutoanKP.getId() );
        dutoanKPDTO.setMadutoan( dutoanKP.getMadutoan() );
        dutoanKPDTO.setTendutoan( dutoanKP.getTendutoan() );
        dutoanKPDTO.setNoidung( dutoanKP.getNoidung() );
        dutoanKPDTO.setSudung( dutoanKP.getSudung() );

        dutoanKPDTO.setTongChiPhi(dutoanKP.getDutoanKPCTS().stream().map(dutoanKPCT -> dutoanKPCT.getTong()).collect(Collectors.summingLong(Long::longValue)));

        return dutoanKPDTO;
    }


    public DutoanKP toEntity(DutoanKPDTO dutoanKPDTO) {
        if ( dutoanKPDTO == null ) {
            return null;
        }

        DutoanKP dutoanKP = new DutoanKP();

        if(dutoanKPDTO.getDetaiId() != null) dutoanKP.setDetai(detaiRepository.getOne(dutoanKPDTO.getDetaiId()));
        dutoanKP.setId( dutoanKPDTO.getId() );
        dutoanKP.setMadutoan( dutoanKPDTO.getMadutoan() );
        dutoanKP.setTendutoan( dutoanKPDTO.getTendutoan() );
        dutoanKP.setNoidung( dutoanKPDTO.getNoidung() );
        dutoanKP.setSudung( dutoanKPDTO.getSudung() );

        return dutoanKP;
    }
}
