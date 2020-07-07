package com.api.backend.service.impl;

import com.api.backend.service.ChunhiemService;
import com.api.backend.domain.Chunhiem;
import com.api.backend.repository.ChunhiemRepository;
import com.api.backend.service.dto.ChunhiemDTO;
import com.api.backend.service.mapper.ChunhiemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Chunhiem}.
 */
@Service
@Transactional
public class ChunhiemServiceImpl implements ChunhiemService {

    private final Logger log = LoggerFactory.getLogger(ChunhiemServiceImpl.class);

    private final ChunhiemRepository chunhiemRepository;

    private final ChunhiemMapper chunhiemMapper;

    public ChunhiemServiceImpl(ChunhiemRepository chunhiemRepository, ChunhiemMapper chunhiemMapper) {
        this.chunhiemRepository = chunhiemRepository;
        this.chunhiemMapper = chunhiemMapper;
    }

    /**
     * Save a chunhiem.
     *
     * @param chunhiemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ChunhiemDTO save(ChunhiemDTO chunhiemDTO) {
        log.debug("Request to save Chunhiem : {}", chunhiemDTO);
        Chunhiem chunhiem = chunhiemMapper.toEntity(chunhiemDTO);
        chunhiem = chunhiemRepository.save(chunhiem);
        return chunhiemMapper.toDto(chunhiem);
    }

    /**
     * Get all the chunhiems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChunhiemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Chunhiems");
        return chunhiemRepository.findAll(pageable)
            .map(chunhiemMapper::toDto);
    }


    /**
     * Get one chunhiem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Chunhiem> findOne(Long id) {
        log.debug("Request to get Chunhiem : {}", id);
        return chunhiemRepository.findById(id);
//            .map(chunhiemMapper::toDto);
    }

    /**
     * Delete the chunhiem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chunhiem : {}", id);
        chunhiemRepository.deleteById(id);
    }
}
