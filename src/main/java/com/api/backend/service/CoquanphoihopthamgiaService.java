package com.api.backend.service;

import com.api.backend.service.dto.CoquanphoihopthamgiaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Coquanphoihopthamgia}.
 */
public interface CoquanphoihopthamgiaService {

    /**
     * Save a coquanphoihopthamgia.
     *
     * @param coquanphoihopthamgiaDTO the entity to save.
     * @return the persisted entity.
     */
    CoquanphoihopthamgiaDTO save(CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO);

    /**
     * Get all the coquanphoihopthamgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoquanphoihopthamgiaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" coquanphoihopthamgia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoquanphoihopthamgiaDTO> findOne(Long id);

    /**
     * Delete the "id" coquanphoihopthamgia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
