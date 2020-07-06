package com.api.backend.service.impl;

import com.api.backend.service.HochamService;
import com.api.backend.domain.Hocham;
import com.api.backend.repository.HochamRepository;
import com.api.backend.service.dto.HochamDTO;
import com.api.backend.service.mapper.HochamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Hocham}.
 */
@Service
@Transactional
public class HochamServiceImpl implements HochamService {

    private final Logger log = LoggerFactory.getLogger(HochamServiceImpl.class);

    private final HochamRepository hochamRepository;

    private final HochamMapper hochamMapper;

    public HochamServiceImpl(HochamRepository hochamRepository, HochamMapper hochamMapper) {
        this.hochamRepository = hochamRepository;
        this.hochamMapper = hochamMapper;
    }

    /**
     * Save a hocham.
     *
     * @param hochamDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HochamDTO save(HochamDTO hochamDTO) {
        log.debug("Request to save Hocham : {}", hochamDTO);
        Hocham hocham = hochamMapper.toEntity(hochamDTO);
        hocham = hochamRepository.save(hocham);
        return hochamMapper.toDto(hocham);
    }

    /**
     * Get all the hochams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HochamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hochams");
        return hochamRepository.findAll(pageable)
            .map(hochamMapper::toDto);
    }


    /**
     * Get one hocham by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HochamDTO> findOne(Long id) {
        log.debug("Request to get Hocham : {}", id);
        return hochamRepository.findById(id)
            .map(hochamMapper::toDto);
    }

    /**
     * Delete the hocham by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hocham : {}", id);
        hochamRepository.deleteById(id);
    }
}
