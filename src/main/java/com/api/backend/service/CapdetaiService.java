package com.api.backend.service;

import com.api.backend.service.dto.CapdetaiDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Capdetai}.
 */
public interface CapdetaiService {

    /**
     * Save a capdetai.
     *
     * @param capdetaiDTO the entity to save.
     * @return the persisted entity.
     */
    CapdetaiDTO save(CapdetaiDTO capdetaiDTO);

    /**
     * Get all the capdetais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CapdetaiDTO> findAll(Pageable pageable);


    /**
     * Get the "id" capdetai.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CapdetaiDTO> findOne(Long id);

    /**
     * Delete the "id" capdetai.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
