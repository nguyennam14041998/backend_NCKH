package com.api.backend.service;

import com.api.backend.service.dto.HochamDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Hocham}.
 */
public interface HochamService {

    /**
     * Save a hocham.
     *
     * @param hochamDTO the entity to save.
     * @return the persisted entity.
     */
    HochamDTO save(HochamDTO hochamDTO);

    /**
     * Get all the hochams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HochamDTO> findAll(Pageable pageable);


    /**
     * Get the "id" hocham.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HochamDTO> findOne(Long id);

    /**
     * Delete the "id" hocham.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
