package com.api.backend.service;

import com.api.backend.domain.Detai;
import com.api.backend.service.dto.DetaiDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Detai}.
 */
public interface DetaiService {

    /**
     * Save a detai.
     *
     * @param detaiDTO the entity to save.
     * @return the persisted entity.
     */
    DetaiDTO save(DetaiDTO detaiDTO);

    /**
     * Get all the detais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetaiDTO> findAll(Pageable pageable);


    /**
     * Get the "id" detai.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Detai> findOne(Long id);

    /**
     * Delete the "id" detai.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
