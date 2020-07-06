package com.api.backend.service.impl;

import com.api.backend.service.TiendoService;
import com.api.backend.domain.Tiendo;
import com.api.backend.repository.TiendoRepository;
import com.api.backend.service.dto.TiendoDTO;
import com.api.backend.service.mapper.TiendoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tiendo}.
 */
@Service
@Transactional
public class TiendoServiceImpl implements TiendoService {

    private final Logger log = LoggerFactory.getLogger(TiendoServiceImpl.class);

    private final TiendoRepository tiendoRepository;

    private final TiendoMapper tiendoMapper;

    public TiendoServiceImpl(TiendoRepository tiendoRepository, TiendoMapper tiendoMapper) {
        this.tiendoRepository = tiendoRepository;
        this.tiendoMapper = tiendoMapper;
    }

    /**
     * Save a tiendo.
     *
     * @param tiendoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TiendoDTO save(TiendoDTO tiendoDTO) {
        log.debug("Request to save Tiendo : {}", tiendoDTO);
        Tiendo tiendo = tiendoMapper.toEntity(tiendoDTO);
        tiendo = tiendoRepository.save(tiendo);
        return tiendoMapper.toDto(tiendo);
    }

    /**
     * Get all the tiendos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TiendoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tiendos");
        return tiendoRepository.findAll(pageable)
            .map(tiendoMapper::toDto);
    }


    /**
     * Get one tiendo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TiendoDTO> findOne(Long id) {
        log.debug("Request to get Tiendo : {}", id);
        return tiendoRepository.findById(id)
            .map(tiendoMapper::toDto);
    }

    /**
     * Delete the tiendo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tiendo : {}", id);
        tiendoRepository.deleteById(id);
    }
}
