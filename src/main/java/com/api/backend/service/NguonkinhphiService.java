package com.api.backend.service;

import com.api.backend.service.dto.NguonkinhphiDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Nguonkinhphi}.
 */
public interface NguonkinhphiService {

    /**
     * Save a nguonkinhphi.
     *
     * @param nguonkinhphiDTO the entity to save.
     * @return the persisted entity.
     */
    NguonkinhphiDTO save(NguonkinhphiDTO nguonkinhphiDTO);

    /**
     * Get all the nguonkinhphis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NguonkinhphiDTO> findAll(Pageable pageable);


    /**
     * Get the "id" nguonkinhphi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NguonkinhphiDTO> findOne(Long id);

    /**
     * Delete the "id" nguonkinhphi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
