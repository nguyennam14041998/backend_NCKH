package com.api.backend.service.impl;

import com.api.backend.service.LinhvucService;
import com.api.backend.domain.Linhvuc;
import com.api.backend.repository.LinhvucRepository;
import com.api.backend.service.dto.LinhvucDTO;
import com.api.backend.service.mapper.LinhvucMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Linhvuc}.
 */
@Service
@Transactional
public class LinhvucServiceImpl implements LinhvucService {

    private final Logger log = LoggerFactory.getLogger(LinhvucServiceImpl.class);

    private final LinhvucRepository linhvucRepository;

    private final LinhvucMapper linhvucMapper;

    public LinhvucServiceImpl(LinhvucRepository linhvucRepository, LinhvucMapper linhvucMapper) {
        this.linhvucRepository = linhvucRepository;
        this.linhvucMapper = linhvucMapper;
    }

    /**
     * Save a linhvuc.
     *
     * @param linhvucDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LinhvucDTO save(LinhvucDTO linhvucDTO) {
        log.debug("Request to save Linhvuc : {}", linhvucDTO);
        Linhvuc linhvuc = linhvucMapper.toEntity(linhvucDTO);
        linhvuc = linhvucRepository.save(linhvuc);
        return linhvucMapper.toDto(linhvuc);
    }

    /**
     * Get all the linhvucs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LinhvucDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Linhvucs");
        return linhvucRepository.findAll(pageable)
            .map(linhvucMapper::toDto);
    }


    /**
     * Get one linhvuc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LinhvucDTO> findOne(Long id) {
        log.debug("Request to get Linhvuc : {}", id);
        return linhvucRepository.findById(id)
            .map(linhvucMapper::toDto);
    }

    /**
     * Delete the linhvuc by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Linhvuc : {}", id);
        linhvucRepository.deleteById(id);
    }
}
