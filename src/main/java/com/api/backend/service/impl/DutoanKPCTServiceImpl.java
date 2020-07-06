package com.api.backend.service.impl;

import com.api.backend.service.DutoanKPCTService;
import com.api.backend.domain.DutoanKPCT;
import com.api.backend.repository.DutoanKPCTRepository;
import com.api.backend.service.dto.DutoanKPCTDTO;
import com.api.backend.service.mapper.DutoanKPCTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DutoanKPCT}.
 */
@Service
@Transactional
public class DutoanKPCTServiceImpl implements DutoanKPCTService {

    private final Logger log = LoggerFactory.getLogger(DutoanKPCTServiceImpl.class);

    private final DutoanKPCTRepository dutoanKPCTRepository;

    private final DutoanKPCTMapper dutoanKPCTMapper;

    public DutoanKPCTServiceImpl(DutoanKPCTRepository dutoanKPCTRepository, DutoanKPCTMapper dutoanKPCTMapper) {
        this.dutoanKPCTRepository = dutoanKPCTRepository;
        this.dutoanKPCTMapper = dutoanKPCTMapper;
    }

    /**
     * Save a dutoanKPCT.
     *
     * @param dutoanKPCTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DutoanKPCTDTO save(DutoanKPCTDTO dutoanKPCTDTO) {
        log.debug("Request to save DutoanKPCT : {}", dutoanKPCTDTO);
        DutoanKPCT dutoanKPCT = dutoanKPCTMapper.toEntity(dutoanKPCTDTO);
        dutoanKPCT = dutoanKPCTRepository.save(dutoanKPCT);
        return dutoanKPCTMapper.toDto(dutoanKPCT);
    }

    /**
     * Get all the dutoanKPCTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DutoanKPCTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DutoanKPCTS");
        return dutoanKPCTRepository.findAll(pageable)
            .map(dutoanKPCTMapper::toDto);
    }


    /**
     * Get one dutoanKPCT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DutoanKPCTDTO> findOne(Long id) {
        log.debug("Request to get DutoanKPCT : {}", id);
        return dutoanKPCTRepository.findById(id)
            .map(dutoanKPCTMapper::toDto);
    }

    /**
     * Delete the dutoanKPCT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DutoanKPCT : {}", id);
        dutoanKPCTRepository.deleteById(id);
    }
}
