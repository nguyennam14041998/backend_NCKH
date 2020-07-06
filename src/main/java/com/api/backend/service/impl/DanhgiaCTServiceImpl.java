package com.api.backend.service.impl;

import com.api.backend.service.DanhgiaCTService;
import com.api.backend.domain.DanhgiaCT;
import com.api.backend.repository.DanhgiaCTRepository;
import com.api.backend.service.dto.DanhgiaCTDTO;
import com.api.backend.service.mapper.DanhgiaCTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DanhgiaCT}.
 */
@Service
@Transactional
public class DanhgiaCTServiceImpl implements DanhgiaCTService {

    private final Logger log = LoggerFactory.getLogger(DanhgiaCTServiceImpl.class);

    private final DanhgiaCTRepository danhgiaCTRepository;

    private final DanhgiaCTMapper danhgiaCTMapper;

    public DanhgiaCTServiceImpl(DanhgiaCTRepository danhgiaCTRepository, DanhgiaCTMapper danhgiaCTMapper) {
        this.danhgiaCTRepository = danhgiaCTRepository;
        this.danhgiaCTMapper = danhgiaCTMapper;
    }

    /**
     * Save a danhgiaCT.
     *
     * @param danhgiaCTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DanhgiaCTDTO save(DanhgiaCTDTO danhgiaCTDTO) {
        log.debug("Request to save DanhgiaCT : {}", danhgiaCTDTO);
        DanhgiaCT danhgiaCT = danhgiaCTMapper.toEntity(danhgiaCTDTO);
        danhgiaCT = danhgiaCTRepository.save(danhgiaCT);
        return danhgiaCTMapper.toDto(danhgiaCT);
    }

    /**
     * Get all the danhgiaCTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DanhgiaCTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DanhgiaCTS");
        return danhgiaCTRepository.findAll(pageable)
            .map(danhgiaCTMapper::toDto);
    }


    /**
     * Get one danhgiaCT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DanhgiaCTDTO> findOne(Long id) {
        log.debug("Request to get DanhgiaCT : {}", id);
        return danhgiaCTRepository.findById(id)
            .map(danhgiaCTMapper::toDto);
    }

    /**
     * Delete the danhgiaCT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DanhgiaCT : {}", id);
        danhgiaCTRepository.deleteById(id);
    }
}
