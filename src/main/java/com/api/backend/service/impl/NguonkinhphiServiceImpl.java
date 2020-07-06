package com.api.backend.service.impl;

import com.api.backend.service.NguonkinhphiService;
import com.api.backend.domain.Nguonkinhphi;
import com.api.backend.repository.NguonkinhphiRepository;
import com.api.backend.service.dto.NguonkinhphiDTO;
import com.api.backend.service.mapper.NguonkinhphiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Nguonkinhphi}.
 */
@Service
@Transactional
public class NguonkinhphiServiceImpl implements NguonkinhphiService {

    private final Logger log = LoggerFactory.getLogger(NguonkinhphiServiceImpl.class);

    private final NguonkinhphiRepository nguonkinhphiRepository;

    private final NguonkinhphiMapper nguonkinhphiMapper;

    public NguonkinhphiServiceImpl(NguonkinhphiRepository nguonkinhphiRepository, NguonkinhphiMapper nguonkinhphiMapper) {
        this.nguonkinhphiRepository = nguonkinhphiRepository;
        this.nguonkinhphiMapper = nguonkinhphiMapper;
    }

    /**
     * Save a nguonkinhphi.
     *
     * @param nguonkinhphiDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NguonkinhphiDTO save(NguonkinhphiDTO nguonkinhphiDTO) {
        log.debug("Request to save Nguonkinhphi : {}", nguonkinhphiDTO);
        Nguonkinhphi nguonkinhphi = nguonkinhphiMapper.toEntity(nguonkinhphiDTO);
        nguonkinhphi = nguonkinhphiRepository.save(nguonkinhphi);
        return nguonkinhphiMapper.toDto(nguonkinhphi);
    }

    /**
     * Get all the nguonkinhphis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NguonkinhphiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nguonkinhphis");
        return nguonkinhphiRepository.findAll(pageable)
            .map(nguonkinhphiMapper::toDto);
    }


    /**
     * Get one nguonkinhphi by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NguonkinhphiDTO> findOne(Long id) {
        log.debug("Request to get Nguonkinhphi : {}", id);
        return nguonkinhphiRepository.findById(id)
            .map(nguonkinhphiMapper::toDto);
    }

    /**
     * Delete the nguonkinhphi by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nguonkinhphi : {}", id);
        nguonkinhphiRepository.deleteById(id);
    }
}
