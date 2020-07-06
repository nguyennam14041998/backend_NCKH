package com.api.backend.service.impl;

import com.api.backend.service.HoidongdanhgiaService;
import com.api.backend.domain.Hoidongdanhgia;
import com.api.backend.repository.HoidongdanhgiaRepository;
import com.api.backend.service.dto.HoidongdanhgiaDTO;
import com.api.backend.service.mapper.HoidongdanhgiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Hoidongdanhgia}.
 */
@Service
@Transactional
public class HoidongdanhgiaServiceImpl implements HoidongdanhgiaService {

    private final Logger log = LoggerFactory.getLogger(HoidongdanhgiaServiceImpl.class);

    private final HoidongdanhgiaRepository hoidongdanhgiaRepository;

    private final HoidongdanhgiaMapper hoidongdanhgiaMapper;

    public HoidongdanhgiaServiceImpl(HoidongdanhgiaRepository hoidongdanhgiaRepository, HoidongdanhgiaMapper hoidongdanhgiaMapper) {
        this.hoidongdanhgiaRepository = hoidongdanhgiaRepository;
        this.hoidongdanhgiaMapper = hoidongdanhgiaMapper;
    }

    /**
     * Save a hoidongdanhgia.
     *
     * @param hoidongdanhgiaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HoidongdanhgiaDTO save(HoidongdanhgiaDTO hoidongdanhgiaDTO) {
        log.debug("Request to save Hoidongdanhgia : {}", hoidongdanhgiaDTO);
        Hoidongdanhgia hoidongdanhgia = hoidongdanhgiaMapper.toEntity(hoidongdanhgiaDTO);
        hoidongdanhgia = hoidongdanhgiaRepository.save(hoidongdanhgia);
        return hoidongdanhgiaMapper.toDto(hoidongdanhgia);
    }

    /**
     * Get all the hoidongdanhgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HoidongdanhgiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hoidongdanhgias");
        return hoidongdanhgiaRepository.findAll(pageable)
            .map(hoidongdanhgiaMapper::toDto);
    }


    /**
     * Get one hoidongdanhgia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HoidongdanhgiaDTO> findOne(Long id) {
        log.debug("Request to get Hoidongdanhgia : {}", id);
        return hoidongdanhgiaRepository.findById(id)
            .map(hoidongdanhgiaMapper::toDto);
    }

    /**
     * Delete the hoidongdanhgia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hoidongdanhgia : {}", id);
        hoidongdanhgiaRepository.deleteById(id);
    }
}
