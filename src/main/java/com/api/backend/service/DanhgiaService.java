package com.api.backend.service;

import com.api.backend.service.dto.DanhgiaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Danhgia}.
 */
public interface DanhgiaService {

    /**
     * Save a danhgia.
     *
     * @param danhgiaDTO the entity to save.
     * @return the persisted entity.
     */
    DanhgiaDTO save(DanhgiaDTO danhgiaDTO);

    /**
     * Get all the danhgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DanhgiaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" danhgia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DanhgiaDTO> findOne(Long id);

    /**
     * Delete the "id" danhgia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
