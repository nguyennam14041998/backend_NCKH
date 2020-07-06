package com.api.backend.service.impl;

import com.api.backend.service.CoquanphoihopthamgiaService;
import com.api.backend.domain.Coquanphoihopthamgia;
import com.api.backend.repository.CoquanphoihopthamgiaRepository;
import com.api.backend.service.dto.CoquanphoihopthamgiaDTO;
import com.api.backend.service.mapper.CoquanphoihopthamgiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Coquanphoihopthamgia}.
 */
@Service
@Transactional
public class CoquanphoihopthamgiaServiceImpl implements CoquanphoihopthamgiaService {

    private final Logger log = LoggerFactory.getLogger(CoquanphoihopthamgiaServiceImpl.class);

    private final CoquanphoihopthamgiaRepository coquanphoihopthamgiaRepository;

    private final CoquanphoihopthamgiaMapper coquanphoihopthamgiaMapper;

    public CoquanphoihopthamgiaServiceImpl(CoquanphoihopthamgiaRepository coquanphoihopthamgiaRepository, CoquanphoihopthamgiaMapper coquanphoihopthamgiaMapper) {
        this.coquanphoihopthamgiaRepository = coquanphoihopthamgiaRepository;
        this.coquanphoihopthamgiaMapper = coquanphoihopthamgiaMapper;
    }

    /**
     * Save a coquanphoihopthamgia.
     *
     * @param coquanphoihopthamgiaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CoquanphoihopthamgiaDTO save(CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO) {
        log.debug("Request to save Coquanphoihopthamgia : {}", coquanphoihopthamgiaDTO);
        Coquanphoihopthamgia coquanphoihopthamgia = coquanphoihopthamgiaMapper.toEntity(coquanphoihopthamgiaDTO);
        coquanphoihopthamgia = coquanphoihopthamgiaRepository.save(coquanphoihopthamgia);
        return coquanphoihopthamgiaMapper.toDto(coquanphoihopthamgia);
    }

    /**
     * Get all the coquanphoihopthamgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CoquanphoihopthamgiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coquanphoihopthamgias");
        return coquanphoihopthamgiaRepository.findAll(pageable)
            .map(coquanphoihopthamgiaMapper::toDto);
    }


    /**
     * Get one coquanphoihopthamgia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CoquanphoihopthamgiaDTO> findOne(Long id) {
        log.debug("Request to get Coquanphoihopthamgia : {}", id);
        return coquanphoihopthamgiaRepository.findById(id)
            .map(coquanphoihopthamgiaMapper::toDto);
    }

    /**
     * Delete the coquanphoihopthamgia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coquanphoihopthamgia : {}", id);
        coquanphoihopthamgiaRepository.deleteById(id);
    }
}
