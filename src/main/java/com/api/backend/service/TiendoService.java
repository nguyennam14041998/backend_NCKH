package com.api.backend.service;

import com.api.backend.service.dto.TiendoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Tiendo}.
 */
public interface TiendoService {

    /**
     * Save a tiendo.
     *
     * @param tiendoDTO the entity to save.
     * @return the persisted entity.
     */
    TiendoDTO save(TiendoDTO tiendoDTO);

    /**
     * Get all the tiendos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TiendoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tiendo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TiendoDTO> findOne(Long id);

    /**
     * Delete the "id" tiendo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
