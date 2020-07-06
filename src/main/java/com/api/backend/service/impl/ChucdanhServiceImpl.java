package com.api.backend.service.impl;

import com.api.backend.service.ChucdanhService;
import com.api.backend.domain.Chucdanh;
import com.api.backend.repository.ChucdanhRepository;
import com.api.backend.service.dto.ChucdanhDTO;
import com.api.backend.service.mapper.ChucdanhMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Chucdanh}.
 */
@Service
@Transactional
public class ChucdanhServiceImpl implements ChucdanhService {

    private final Logger log = LoggerFactory.getLogger(ChucdanhServiceImpl.class);

    private final ChucdanhRepository chucdanhRepository;

    private final ChucdanhMapper chucdanhMapper;

    public ChucdanhServiceImpl(ChucdanhRepository chucdanhRepository, ChucdanhMapper chucdanhMapper) {
        this.chucdanhRepository = chucdanhRepository;
        this.chucdanhMapper = chucdanhMapper;
    }

    /**
     * Save a chucdanh.
     *
     * @param chucdanhDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ChucdanhDTO save(ChucdanhDTO chucdanhDTO) {
        log.debug("Request to save Chucdanh : {}", chucdanhDTO);
        Chucdanh chucdanh = chucdanhMapper.toEntity(chucdanhDTO);
        chucdanh = chucdanhRepository.save(chucdanh);
        return chucdanhMapper.toDto(chucdanh);
    }

    /**
     * Get all the chucdanhs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChucdanhDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Chucdanhs");
        return chucdanhRepository.findAll(pageable)
            .map(chucdanhMapper::toDto);
    }


    /**
     * Get one chucdanh by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChucdanhDTO> findOne(Long id) {
        log.debug("Request to get Chucdanh : {}", id);
        return chucdanhRepository.findById(id)
            .map(chucdanhMapper::toDto);
    }

    /**
     * Delete the chucdanh by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chucdanh : {}", id);
        chucdanhRepository.deleteById(id);
    }
}
