package com.api.backend.service.impl;

import com.api.backend.service.ThanhvienHDService;
import com.api.backend.domain.ThanhvienHD;
import com.api.backend.repository.ThanhvienHDRepository;
import com.api.backend.service.dto.ThanhvienHDDTO;
import com.api.backend.service.mapper.ThanhvienHDMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ThanhvienHD}.
 */
@Service
@Transactional
public class ThanhvienHDServiceImpl implements ThanhvienHDService {

    private final Logger log = LoggerFactory.getLogger(ThanhvienHDServiceImpl.class);

    private final ThanhvienHDRepository thanhvienHDRepository;

    private final ThanhvienHDMapper thanhvienHDMapper;

    public ThanhvienHDServiceImpl(ThanhvienHDRepository thanhvienHDRepository, ThanhvienHDMapper thanhvienHDMapper) {
        this.thanhvienHDRepository = thanhvienHDRepository;
        this.thanhvienHDMapper = thanhvienHDMapper;
    }

    /**
     * Save a thanhvienHD.
     *
     * @param thanhvienHDDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ThanhvienHDDTO save(ThanhvienHDDTO thanhvienHDDTO) {
        log.debug("Request to save ThanhvienHD : {}", thanhvienHDDTO);
        ThanhvienHD thanhvienHD = thanhvienHDMapper.toEntity(thanhvienHDDTO);
        thanhvienHD = thanhvienHDRepository.save(thanhvienHD);
        return thanhvienHDMapper.toDto(thanhvienHD);
    }

    /**
     * Get all the thanhvienHDS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ThanhvienHDDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThanhvienHDS");
        return thanhvienHDRepository.findAll(pageable)
            .map(thanhvienHDMapper::toDto);
    }


    /**
     * Get one thanhvienHD by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ThanhvienHDDTO> findOne(Long id) {
        log.debug("Request to get ThanhvienHD : {}", id);
        return thanhvienHDRepository.findById(id)
            .map(thanhvienHDMapper::toDto);
    }

    /**
     * Delete the thanhvienHD by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThanhvienHD : {}", id);
        thanhvienHDRepository.deleteById(id);
    }
}
