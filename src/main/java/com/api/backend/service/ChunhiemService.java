package com.api.backend.service;

import com.api.backend.domain.Chunhiem;
import com.api.backend.service.dto.ChunhiemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Chunhiem}.
 */
public interface ChunhiemService {

    /**
     * Save a chunhiem.
     *
     * @param chunhiemDTO the entity to save.
     * @return the persisted entity.
     */
    ChunhiemDTO save(ChunhiemDTO chunhiemDTO);

    /**
     * Get all the chunhiems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChunhiemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" chunhiem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Chunhiem> findOne(Long id);

    /**
     * Delete the "id" chunhiem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
