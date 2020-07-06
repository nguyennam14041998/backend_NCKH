package com.api.backend.service;

import com.api.backend.service.dto.NhansuDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Nhansu}.
 */
public interface NhansuService {

    /**
     * Save a nhansu.
     *
     * @param nhansuDTO the entity to save.
     * @return the persisted entity.
     */
    NhansuDTO save(NhansuDTO nhansuDTO);

    /**
     * Get all the nhansus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NhansuDTO> findAll(Pageable pageable);


    /**
     * Get the "id" nhansu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NhansuDTO> findOne(Long id);

    /**
     * Delete the "id" nhansu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
