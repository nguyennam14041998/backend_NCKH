package com.api.backend.service;

import com.api.backend.service.dto.DutoanKPCTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.DutoanKPCT}.
 */
public interface DutoanKPCTService {

    /**
     * Save a dutoanKPCT.
     *
     * @param dutoanKPCTDTO the entity to save.
     * @return the persisted entity.
     */
    DutoanKPCTDTO save(DutoanKPCTDTO dutoanKPCTDTO);

    /**
     * Get all the dutoanKPCTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DutoanKPCTDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dutoanKPCT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DutoanKPCTDTO> findOne(Long id);

    /**
     * Delete the "id" dutoanKPCT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
