package com.api.backend.service;

import com.api.backend.service.dto.LinhvucDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Linhvuc}.
 */
public interface LinhvucService {

    /**
     * Save a linhvuc.
     *
     * @param linhvucDTO the entity to save.
     * @return the persisted entity.
     */
    LinhvucDTO save(LinhvucDTO linhvucDTO);

    /**
     * Get all the linhvucs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LinhvucDTO> findAll(Pageable pageable);


    /**
     * Get the "id" linhvuc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LinhvucDTO> findOne(Long id);

    /**
     * Delete the "id" linhvuc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
