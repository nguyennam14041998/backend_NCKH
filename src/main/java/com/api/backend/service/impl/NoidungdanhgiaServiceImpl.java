package com.api.backend.service.impl;

import com.api.backend.service.NoidungdanhgiaService;
import com.api.backend.domain.Noidungdanhgia;
import com.api.backend.repository.NoidungdanhgiaRepository;
import com.api.backend.service.dto.NoidungdanhgiaDTO;
import com.api.backend.service.mapper.NoidungdanhgiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Noidungdanhgia}.
 */
@Service
@Transactional
public class NoidungdanhgiaServiceImpl implements NoidungdanhgiaService {

    private final Logger log = LoggerFactory.getLogger(NoidungdanhgiaServiceImpl.class);

    private final NoidungdanhgiaRepository noidungdanhgiaRepository;

    private final NoidungdanhgiaMapper noidungdanhgiaMapper;

    public NoidungdanhgiaServiceImpl(NoidungdanhgiaRepository noidungdanhgiaRepository, NoidungdanhgiaMapper noidungdanhgiaMapper) {
        this.noidungdanhgiaRepository = noidungdanhgiaRepository;
        this.noidungdanhgiaMapper = noidungdanhgiaMapper;
    }

    /**
     * Save a noidungdanhgia.
     *
     * @param noidungdanhgiaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NoidungdanhgiaDTO save(NoidungdanhgiaDTO noidungdanhgiaDTO) {
        log.debug("Request to save Noidungdanhgia : {}", noidungdanhgiaDTO);
        Noidungdanhgia noidungdanhgia = noidungdanhgiaMapper.toEntity(noidungdanhgiaDTO);
        noidungdanhgia = noidungdanhgiaRepository.save(noidungdanhgia);
        return noidungdanhgiaMapper.toDto(noidungdanhgia);
    }

    /**
     * Get all the noidungdanhgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NoidungdanhgiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Noidungdanhgias");
        return noidungdanhgiaRepository.findAll(pageable)
            .map(noidungdanhgiaMapper::toDto);
    }


    /**
     * Get one noidungdanhgia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NoidungdanhgiaDTO> findOne(Long id) {
        log.debug("Request to get Noidungdanhgia : {}", id);
        return noidungdanhgiaRepository.findById(id)
            .map(noidungdanhgiaMapper::toDto);
    }

    /**
     * Delete the noidungdanhgia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Noidungdanhgia : {}", id);
        noidungdanhgiaRepository.deleteById(id);
    }
}
