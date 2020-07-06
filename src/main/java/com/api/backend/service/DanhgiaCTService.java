package com.api.backend.service;

import com.api.backend.service.dto.DanhgiaCTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.DanhgiaCT}.
 */
public interface DanhgiaCTService {

    /**
     * Save a danhgiaCT.
     *
     * @param danhgiaCTDTO the entity to save.
     * @return the persisted entity.
     */
    DanhgiaCTDTO save(DanhgiaCTDTO danhgiaCTDTO);

    /**
     * Get all the danhgiaCTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DanhgiaCTDTO> findAll(Pageable pageable);


    /**
     * Get the "id" danhgiaCT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DanhgiaCTDTO> findOne(Long id);

    /**
     * Delete the "id" danhgiaCT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
