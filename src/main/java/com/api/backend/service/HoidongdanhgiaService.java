package com.api.backend.service;

import com.api.backend.service.dto.HoidongdanhgiaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Hoidongdanhgia}.
 */
public interface HoidongdanhgiaService {

    /**
     * Save a hoidongdanhgia.
     *
     * @param hoidongdanhgiaDTO the entity to save.
     * @return the persisted entity.
     */
    HoidongdanhgiaDTO save(HoidongdanhgiaDTO hoidongdanhgiaDTO);

    /**
     * Get all the hoidongdanhgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HoidongdanhgiaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" hoidongdanhgia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HoidongdanhgiaDTO> findOne(Long id);

    /**
     * Delete the "id" hoidongdanhgia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
