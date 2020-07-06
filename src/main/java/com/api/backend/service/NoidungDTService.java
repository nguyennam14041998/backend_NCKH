package com.api.backend.service;

import com.api.backend.service.dto.NoidungDTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.NoidungDT}.
 */
public interface NoidungDTService {

    /**
     * Save a noidungDT.
     *
     * @param noidungDTDTO the entity to save.
     * @return the persisted entity.
     */
    NoidungDTDTO save(NoidungDTDTO noidungDTDTO);

    /**
     * Get all the noidungDTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NoidungDTDTO> findAll(Pageable pageable);


    /**
     * Get the "id" noidungDT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoidungDTDTO> findOne(Long id);

    /**
     * Delete the "id" noidungDT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
