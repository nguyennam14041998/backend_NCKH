package com.api.backend.service.impl;

import com.api.backend.service.UpfileService;
import com.api.backend.domain.Upfile;
import com.api.backend.repository.UpfileRepository;
import com.api.backend.service.dto.UpfileDTO;
import com.api.backend.service.mapper.UpfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Upfile}.
 */
@Service
@Transactional
public class UpfileServiceImpl implements UpfileService {

    private final Logger log = LoggerFactory.getLogger(UpfileServiceImpl.class);

    private final UpfileRepository upfileRepository;

    private final UpfileMapper upfileMapper;

    public UpfileServiceImpl(UpfileRepository upfileRepository, UpfileMapper upfileMapper) {
        this.upfileRepository = upfileRepository;
        this.upfileMapper = upfileMapper;
    }

    /**
     * Save a upfile.
     *
     * @param upfileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UpfileDTO save(UpfileDTO upfileDTO) {
        log.debug("Request to save Upfile : {}", upfileDTO);
        Upfile upfile = upfileMapper.toEntity(upfileDTO);
        upfile = upfileRepository.save(upfile);
        return upfileMapper.toDto(upfile);
    }

    /**
     * Get all the upfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UpfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Upfiles");
        return upfileRepository.findAll(pageable)
            .map(upfileMapper::toDto);
    }


    /**
     * Get one upfile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UpfileDTO> findOne(Long id) {
        log.debug("Request to get Upfile : {}", id);
        return upfileRepository.findById(id)
            .map(upfileMapper::toDto);
    }

    /**
     * Delete the upfile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Upfile : {}", id);
        upfileRepository.deleteById(id);
    }
}
