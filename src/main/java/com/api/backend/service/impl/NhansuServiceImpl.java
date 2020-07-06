package com.api.backend.service.impl;

import com.api.backend.service.NhansuService;
import com.api.backend.domain.Nhansu;
import com.api.backend.repository.NhansuRepository;
import com.api.backend.service.dto.NhansuDTO;
import com.api.backend.service.mapper.NhansuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Nhansu}.
 */
@Service
@Transactional
public class NhansuServiceImpl implements NhansuService {

    private final Logger log = LoggerFactory.getLogger(NhansuServiceImpl.class);

    private final NhansuRepository nhansuRepository;

    private final NhansuMapper nhansuMapper;

    public NhansuServiceImpl(NhansuRepository nhansuRepository, NhansuMapper nhansuMapper) {
        this.nhansuRepository = nhansuRepository;
        this.nhansuMapper = nhansuMapper;
    }

    /**
     * Save a nhansu.
     *
     * @param nhansuDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NhansuDTO save(NhansuDTO nhansuDTO) {
        log.debug("Request to save Nhansu : {}", nhansuDTO);
        Nhansu nhansu = nhansuMapper.toEntity(nhansuDTO);
        nhansu = nhansuRepository.save(nhansu);
        return nhansuMapper.toDto(nhansu);
    }

    /**
     * Get all the nhansus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NhansuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nhansus");
        return nhansuRepository.findAll(pageable)
            .map(nhansuMapper::toDto);
    }


    /**
     * Get one nhansu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NhansuDTO> findOne(Long id) {
        log.debug("Request to get Nhansu : {}", id);
        return nhansuRepository.findById(id)
            .map(nhansuMapper::toDto);
    }

    /**
     * Delete the nhansu by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nhansu : {}", id);
        nhansuRepository.deleteById(id);
    }
}
