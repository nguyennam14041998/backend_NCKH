package com.api.backend.service.impl;

import com.api.backend.service.NhansuthamgiaService;
import com.api.backend.domain.Nhansuthamgia;
import com.api.backend.repository.NhansuthamgiaRepository;
import com.api.backend.service.dto.NhansuthamgiaDTO;
import com.api.backend.service.mapper.NhansuthamgiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Nhansuthamgia}.
 */
@Service
@Transactional
public class NhansuthamgiaServiceImpl implements NhansuthamgiaService {

    private final Logger log = LoggerFactory.getLogger(NhansuthamgiaServiceImpl.class);

    private final NhansuthamgiaRepository nhansuthamgiaRepository;

    private final NhansuthamgiaMapper nhansuthamgiaMapper;

    public NhansuthamgiaServiceImpl(NhansuthamgiaRepository nhansuthamgiaRepository, NhansuthamgiaMapper nhansuthamgiaMapper) {
        this.nhansuthamgiaRepository = nhansuthamgiaRepository;
        this.nhansuthamgiaMapper = nhansuthamgiaMapper;
    }

    /**
     * Save a nhansuthamgia.
     *
     * @param nhansuthamgiaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NhansuthamgiaDTO save(NhansuthamgiaDTO nhansuthamgiaDTO) {
        log.debug("Request to save Nhansuthamgia : {}", nhansuthamgiaDTO);
        Nhansuthamgia nhansuthamgia = nhansuthamgiaMapper.toEntity(nhansuthamgiaDTO);
        nhansuthamgia = nhansuthamgiaRepository.save(nhansuthamgia);
        return nhansuthamgiaMapper.toDto(nhansuthamgia);
    }

    /**
     * Get all the nhansuthamgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NhansuthamgiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nhansuthamgias");
        return nhansuthamgiaRepository.findAll(pageable)
            .map(nhansuthamgiaMapper::toDto);
    }


    /**
     * Get one nhansuthamgia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NhansuthamgiaDTO> findOne(Long id) {
        log.debug("Request to get Nhansuthamgia : {}", id);
        return nhansuthamgiaRepository.findById(id)
            .map(nhansuthamgiaMapper::toDto);
    }

    /**
     * Delete the nhansuthamgia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nhansuthamgia : {}", id);
        nhansuthamgiaRepository.deleteById(id);
    }
}
