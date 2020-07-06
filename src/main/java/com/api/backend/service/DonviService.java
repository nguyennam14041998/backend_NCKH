package com.api.backend.service;

import com.api.backend.service.dto.DonviDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Donvi}.
 */
public interface DonviService {

    /**
     * Save a donvi.
     *
     * @param donviDTO the entity to save.
     * @return the persisted entity.
     */
    DonviDTO save(DonviDTO donviDTO);

    /**
     * Get all the donvis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonviDTO> findAll(Pageable pageable);


    /**
     * Get the "id" donvi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonviDTO> findOne(Long id);

    /**
     * Delete the "id" donvi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
