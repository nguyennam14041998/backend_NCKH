package com.api.backend.service.impl;

import com.api.backend.service.NoidungDTService;
import com.api.backend.domain.NoidungDT;
import com.api.backend.repository.NoidungDTRepository;
import com.api.backend.service.dto.NoidungDTDTO;
import com.api.backend.service.mapper.NoidungDTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NoidungDT}.
 */
@Service
@Transactional
public class NoidungDTServiceImpl implements NoidungDTService {

    private final Logger log = LoggerFactory.getLogger(NoidungDTServiceImpl.class);

    private final NoidungDTRepository noidungDTRepository;

    private final NoidungDTMapper noidungDTMapper;

    public NoidungDTServiceImpl(NoidungDTRepository noidungDTRepository, NoidungDTMapper noidungDTMapper) {
        this.noidungDTRepository = noidungDTRepository;
        this.noidungDTMapper = noidungDTMapper;
    }

    /**
     * Save a noidungDT.
     *
     * @param noidungDTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NoidungDTDTO save(NoidungDTDTO noidungDTDTO) {
        log.debug("Request to save NoidungDT : {}", noidungDTDTO);
        NoidungDT noidungDT = noidungDTMapper.toEntity(noidungDTDTO);
        noidungDT = noidungDTRepository.save(noidungDT);
        return noidungDTMapper.toDto(noidungDT);
    }

    /**
     * Get all the noidungDTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NoidungDTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NoidungDTS");
        return noidungDTRepository.findAll(pageable)
            .map(noidungDTMapper::toDto);
    }


    /**
     * Get one noidungDT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NoidungDTDTO> findOne(Long id) {
        log.debug("Request to get NoidungDT : {}", id);
        return noidungDTRepository.findById(id)
            .map(noidungDTMapper::toDto);
    }

    /**
     * Delete the noidungDT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NoidungDT : {}", id);
        noidungDTRepository.deleteById(id);
    }
}
