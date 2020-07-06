package com.api.backend.service.impl;

import com.api.backend.service.CoquanphoihopService;
import com.api.backend.domain.Coquanphoihop;
import com.api.backend.repository.CoquanphoihopRepository;
import com.api.backend.service.dto.CoquanphoihopDTO;
import com.api.backend.service.mapper.CoquanphoihopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Coquanphoihop}.
 */
@Service
@Transactional
public class CoquanphoihopServiceImpl implements CoquanphoihopService {

    private final Logger log = LoggerFactory.getLogger(CoquanphoihopServiceImpl.class);

    private final CoquanphoihopRepository coquanphoihopRepository;

    private final CoquanphoihopMapper coquanphoihopMapper;

    public CoquanphoihopServiceImpl(CoquanphoihopRepository coquanphoihopRepository, CoquanphoihopMapper coquanphoihopMapper) {
        this.coquanphoihopRepository = coquanphoihopRepository;
        this.coquanphoihopMapper = coquanphoihopMapper;
    }

    /**
     * Save a coquanphoihop.
     *
     * @param coquanphoihopDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CoquanphoihopDTO save(CoquanphoihopDTO coquanphoihopDTO) {
        log.debug("Request to save Coquanphoihop : {}", coquanphoihopDTO);
        Coquanphoihop coquanphoihop = coquanphoihopMapper.toEntity(coquanphoihopDTO);
        coquanphoihop = coquanphoihopRepository.save(coquanphoihop);
        return coquanphoihopMapper.toDto(coquanphoihop);
    }

    /**
     * Get all the coquanphoihops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CoquanphoihopDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coquanphoihops");
        return coquanphoihopRepository.findAll(pageable)
            .map(coquanphoihopMapper::toDto);
    }


    /**
     * Get one coquanphoihop by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CoquanphoihopDTO> findOne(Long id) {
        log.debug("Request to get Coquanphoihop : {}", id);
        return coquanphoihopRepository.findById(id)
            .map(coquanphoihopMapper::toDto);
    }

    /**
     * Delete the coquanphoihop by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coquanphoihop : {}", id);
        coquanphoihopRepository.deleteById(id);
    }
}
