package com.api.backend.service.impl;

import com.api.backend.service.DonviService;
import com.api.backend.domain.Donvi;
import com.api.backend.repository.DonviRepository;
import com.api.backend.service.dto.DonviDTO;
import com.api.backend.service.mapper.DonviMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Donvi}.
 */
@Service
@Transactional
public class DonviServiceImpl implements DonviService {

    private final Logger log = LoggerFactory.getLogger(DonviServiceImpl.class);

    private final DonviRepository donviRepository;

    private final DonviMapper donviMapper;

    public DonviServiceImpl(DonviRepository donviRepository, DonviMapper donviMapper) {
        this.donviRepository = donviRepository;
        this.donviMapper = donviMapper;
    }

    /**
     * Save a donvi.
     *
     * @param donviDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DonviDTO save(DonviDTO donviDTO) {
        log.debug("Request to save Donvi : {}", donviDTO);
        Donvi donvi = donviMapper.toEntity(donviDTO);
        donvi = donviRepository.save(donvi);
        return donviMapper.toDto(donvi);
    }

    /**
     * Get all the donvis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DonviDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Donvis");
        return donviRepository.findAll(pageable)
            .map(donviMapper::toDto);
    }


    /**
     * Get one donvi by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DonviDTO> findOne(Long id) {
        log.debug("Request to get Donvi : {}", id);
        return donviRepository.findById(id)
            .map(donviMapper::toDto);
    }

    /**
     * Delete the donvi by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Donvi : {}", id);
        donviRepository.deleteById(id);
    }
}
