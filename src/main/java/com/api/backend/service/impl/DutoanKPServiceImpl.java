package com.api.backend.service.impl;

import com.api.backend.service.DutoanKPService;
import com.api.backend.domain.DutoanKP;
import com.api.backend.repository.DutoanKPRepository;
import com.api.backend.service.dto.DutoanKPDTO;
import com.api.backend.service.mapper.DutoanKPMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DutoanKP}.
 */
@Service
@Transactional
public class DutoanKPServiceImpl implements DutoanKPService {

    private final Logger log = LoggerFactory.getLogger(DutoanKPServiceImpl.class);

    private final DutoanKPRepository dutoanKPRepository;

    private final DutoanKPMapper dutoanKPMapper;

    public DutoanKPServiceImpl(DutoanKPRepository dutoanKPRepository, DutoanKPMapper dutoanKPMapper) {
        this.dutoanKPRepository = dutoanKPRepository;
        this.dutoanKPMapper = dutoanKPMapper;
    }

    /**
     * Save a dutoanKP.
     *
     * @param dutoanKPDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DutoanKPDTO save(DutoanKPDTO dutoanKPDTO) {
        log.debug("Request to save DutoanKP : {}", dutoanKPDTO);
        DutoanKP dutoanKP = dutoanKPMapper.toEntity(dutoanKPDTO);
        dutoanKP = dutoanKPRepository.save(dutoanKP);
        return dutoanKPMapper.toDto(dutoanKP);
    }

    /**
     * Get all the dutoanKPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DutoanKPDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DutoanKPS");
        return dutoanKPRepository.findAll(pageable)
            .map(dutoanKPMapper::toDto);
    }


    /**
     * Get one dutoanKP by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DutoanKPDTO> findOne(Long id) {
        log.debug("Request to get DutoanKP : {}", id);
        return dutoanKPRepository.findById(id)
            .map(dutoanKPMapper::toDto);
    }

    /**
     * Delete the dutoanKP by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DutoanKP : {}", id);
        dutoanKPRepository.deleteById(id);
    }
}
