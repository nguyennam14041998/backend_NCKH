package com.api.backend.service.impl;

import com.api.backend.service.CapdetaiService;
import com.api.backend.domain.Capdetai;
import com.api.backend.repository.CapdetaiRepository;
import com.api.backend.service.dto.CapdetaiDTO;
import com.api.backend.service.mapper.CapdetaiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Capdetai}.
 */
@Service
@Transactional
public class CapdetaiServiceImpl implements CapdetaiService {

    private final Logger log = LoggerFactory.getLogger(CapdetaiServiceImpl.class);

    private final CapdetaiRepository capdetaiRepository;

    private final CapdetaiMapper capdetaiMapper;

    public CapdetaiServiceImpl(CapdetaiRepository capdetaiRepository, CapdetaiMapper capdetaiMapper) {
        this.capdetaiRepository = capdetaiRepository;
        this.capdetaiMapper = capdetaiMapper;
    }

    /**
     * Save a capdetai.
     *
     * @param capdetaiDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CapdetaiDTO save(CapdetaiDTO capdetaiDTO) {
        log.debug("Request to save Capdetai : {}", capdetaiDTO);
        Capdetai capdetai = capdetaiMapper.toEntity(capdetaiDTO);
        capdetai = capdetaiRepository.save(capdetai);
        return capdetaiMapper.toDto(capdetai);
    }

    /**
     * Get all the capdetais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CapdetaiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Capdetais");
        return capdetaiRepository.findAll(pageable)
            .map(capdetaiMapper::toDto);
    }


    /**
     * Get one capdetai by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CapdetaiDTO> findOne(Long id) {
        log.debug("Request to get Capdetai : {}", id);
        return capdetaiRepository.findById(id)
            .map(capdetaiMapper::toDto);
    }

    /**
     * Delete the capdetai by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Capdetai : {}", id);
        capdetaiRepository.deleteById(id);
    }
}
